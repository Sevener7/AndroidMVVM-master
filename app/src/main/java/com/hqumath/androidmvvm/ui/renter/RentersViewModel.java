package com.hqumath.androidmvvm.ui.renter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.data.paging.RenterInfoBoundaryCallback;
import com.hqumath.androidmvvm.data.paging.UserInfoBoundaryCallback;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * ****************************************************************
 * 文件名称: FollowingViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class RentersViewModel extends BaseViewModel<MyRepository> {

    private RenterInfoBoundaryCallback boundaryCallback;

    public LiveData<PagedList<RenterInfoEntity>> list;
    public LiveData<NetworkState> networkState;//网络状态
    public MutableLiveData<NetworkState> refreshState = new MutableLiveData<>();//初始化加载状态
    private int pageSize = 10;//每页大小
    private int initialLoadPage = 3;//预加载页数

    public RentersViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void init() {
//        boundaryCallback = new RenterInfoBoundaryCallback(this::insertResultIntoDb, pageSize, initialLoadPage,
//                getLifecycleProvider());
//
        list = new LivePagedListBuilder<>(
                model.loadAllRenters(),
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize * initialLoadPage)
                        .build())
                .setFetchExecutor(appExecutors.diskIO())
//                .setBoundaryCallback(boundaryCallback)
                .build();
//
//        networkState = boundaryCallback.networkState;
    }

    public void refresh() {
        refreshState.postValue(NetworkState.LOADED);
//        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener<List<RenterInfoEntity>>() {
//            @Override
//            public void onSubscribe() {
//                refreshState.postValue(NetworkState.LOADING);
//            }
//
//            @Override
//            public void onNext(List<RenterInfoEntity> o) {
//                model.runInTransaction(() -> {
//                    model.deleteAllUsers();
//                    insertResultIntoDb(o);
//                });
//                refreshState.postValue(NetworkState.LOADED);
//            }
//
//            @Override
//            public void onError(HandlerException.ResponseThrowable e) {
//                refreshState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        }, getLifecycleProvider()) {
//            @Override
//            public Observable getObservable(Retrofit retrofit) {
////                return retrofit.create(MyApiService.class).getFollowers("JakeWharton", pageSize * initialLoadPage, 1);
//                return null;
//            }
//        });
    }

    public void retry() {
        boundaryCallback.helper.retryAllFailed();
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     * 插入数据库，并标注位置
     */
    private void insertResultIntoDb(List<RenterInfoEntity> list) {
        model.runInTransaction(() -> {
            int start = model.getNextIndexInRenters();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIndexInResponse(start + i);
            }
            model.insertAllRenter(list);
        });
    }
    public void deleteByRenterId(int id){
        model.runInTransaction(() -> {
            model.deleteRenterById(id);
        });
    }
}