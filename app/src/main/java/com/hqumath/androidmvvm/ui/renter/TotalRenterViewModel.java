package com.hqumath.androidmvvm.ui.renter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.data.paging.StarredReposFactory;
import com.hqumath.androidmvvm.data.paging.StarredReposSource;
import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * 文件名称: StarredViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class TotalRenterViewModel extends BaseViewModel<MyRepository> {


    public LiveData<PagedList<TotalMaterEntity>> list;
    public LiveData<NetworkState> networkState;//网络状态
    public LiveData<NetworkState> refreshState;//初始化加载状态
    public MutableLiveData<String> mater = new MutableLiveData<>();
    public MutableLiveData<String> date = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRecycleVisible = new MutableLiveData<>();
    private int pageSize = 10;//每页大小
    private int initialLoadPage = 3;//预加载页数

    public TotalRenterViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        isRecycleVisible.setValue(true);
    }

    public void init() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        date.setValue(ft.format(dNow));
        //update result
        List<TotalMaterEntity> currentList = model.getAllTotalMaters();
        Logger.getLogger("List<MaterInfoEntity>").log(Level.INFO, Arrays.toString(currentList.toArray()));
        for (int i = 1; i < currentList.size()+1; i++) {
            if (i <= currentList.size() - 1 && currentList.get(i).getValue() < 1) {
                TotalMaterEntity materInfoEntity = currentList.get(i);
                double totalElect = (currentList.get(i).getMater() - currentList.get(i - 1).getMater());
                i++;
                materInfoEntity.setValue(Double.parseDouble(String.format("%.2f", totalElect)));
                model.updateTotalMater(materInfoEntity);
            }
        }
        list = new LivePagedListBuilder<>(
                model.loadAllTotalMaters(),
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize * initialLoadPage)
                        .setEnablePlaceholders(false)//不明确item数目
                        .build())
                .setFetchExecutor(appExecutors.networkIO())
                .build();
    }

    public void refresh() {

    }

    public void retry() {
    }
    public void deleteByDate(String date){
        model.deleteTotalRenterByDate(date);
    }

}
