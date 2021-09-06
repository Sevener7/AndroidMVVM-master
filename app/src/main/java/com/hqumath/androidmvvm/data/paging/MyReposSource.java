package com.hqumath.androidmvvm.data.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.hqumath.androidmvvm.app.AppExecutors;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyReposSource
 * 作    者: Created by gyd
 * 创建时间: 2019/7/31 14:47
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyReposSource extends PageKeyedDataSource<Long, ReposEntity> {

    public MutableLiveData<NetworkState> networkState = new MutableLiveData<>();//网络状态
    public MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();//初始化加载状态
    private Runnable retry = null;
    private int pageSize;//分页大小

    public MyReposSource(int pageSize) {
        this.pageSize = pageSize;
    }

    public void retryAllFailed() {
        if (retry != null) {
            Runnable prevRetry = retry;
            retry = null;
            AppExecutors.getInstance().networkIO().execute(prevRetry);
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long,
            ReposEntity> callback) {
        networkState.postValue(NetworkState.LOADING);
        //initialLoad.postValue(NetworkState.LOADING);

        // triggered by a refresh, we better execute sync
        // 初始化请求不能异步
        try {
            Response<List<ReposEntity>> response =
                    RetrofitClient.getInstance().getRetrofit().create(MyApiService.class).getMyRepos(params.requestedLoadSize, 1).execute();
            if (response.isSuccessful() && response.body() != null) {
                retry = null;
                networkState.postValue(NetworkState.LOADED);
                initialLoad.postValue(NetworkState.LOADED);
                Long nextPageKey = params.requestedLoadSize / pageSize + 1L;//下一页位置
                callback.onResult(response.body(), null, nextPageKey);
            } else {
                retry = () -> loadInitial(params, callback);
                NetworkState error = new NetworkState(NetworkState.Status.FAILED,
                        "error: " + response.code() + " " + response.message());
                networkState.postValue(error);
                initialLoad.postValue(error);
            }
        } catch (IOException e) {
            retry = () -> loadInitial(params, callback);
            NetworkState error = new NetworkState(NetworkState.Status.FAILED, e.getMessage());
            networkState.postValue(error);
            initialLoad.postValue(error);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ReposEntity> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ReposEntity> callback) {
        networkState.postValue(NetworkState.LOADING);
        RetrofitClient.getInstance().getRetrofit().create(MyApiService.class).getMyRepos(params.requestedLoadSize,
                params.key)
                .enqueue(new Callback<List<ReposEntity>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ReposEntity>> call,
                                           @NonNull Response<List<ReposEntity>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            retry = null;
                            networkState.postValue(NetworkState.LOADED);
                            List<ReposEntity> list = response.body();
                            callback.onResult(list, (list.size() > 0) ? params.key + 1 : null);
                        } else {
                            retry = () -> loadAfter(params, callback);
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                                    "error: " + response.code() + " " + response.message()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ReposEntity>> call, @NonNull Throwable t) {
                        retry = () -> loadAfter(params, callback);
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
                    }
                });
    }
}
