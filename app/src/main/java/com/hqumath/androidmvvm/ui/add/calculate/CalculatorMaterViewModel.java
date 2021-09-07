package com.hqumath.androidmvvm.ui.add.calculate;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowCalculatorBeans;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * 文件名称: ProfileViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:52
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CalculatorMaterViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isRecycleVisible = new MutableLiveData<>();
    public MutableLiveData<String> value = new MutableLiveData<>();
    public MutableLiveData<String> date = new MutableLiveData<>();
    public LiveData<PagedList<ShowCalculatorBeans>> list;
    private int pageSize = 10;//每页大小
    private int initialLoadPage = 3;//预加载页数

    public CalculatorMaterViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        isRecycleVisible.setValue(true);
    }

    public void showAdd() {
        isRecycleVisible.setValue(false);
    }



    @SuppressLint("DefaultLocale")
    public void init(String date,double totalValue,double shareValue) {
        List<ShowCalculatorBeans> list1 = model.getShowCalculatorBeanListByDate(date);
        Logger.getLogger("TEST").log(Level.INFO, "list="+Arrays.toString(list1.toArray()));
        double useValue = 0;
        if(totalValue>0 && shareValue<=0){
            for(ShowCalculatorBeans showCalculatorBeans : list1){
                useValue+=showCalculatorBeans.getUse_mater();
            }
            TotalMaterEntity totalMaterEntity = model.findTotalMaterByDate(date).get(0);
            shareValue = totalValue-useValue;
            totalMaterEntity.setShare_value(Double.parseDouble(String.format("%.2f", shareValue)));
            model.updateTotalMater(totalMaterEntity);
        }else {
            useValue = totalValue - shareValue;
        }
        value.setValue(String.format(Locale.getDefault(),"总共使用：%.1f,电表相加：%.1f,公摊：%.1f",totalValue,useValue,shareValue));
        list = new LivePagedListBuilder<>(
                model.getShowAllCalculatorBeanListByDate(date),
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize * initialLoadPage)
                        .build())
                .setFetchExecutor(appExecutors.diskIO())
//                .setBoundaryCallback(boundaryCallback)
                .build();
//        Logger.getLogger("TEST").log(Level.INFO, list.getValue().size() + "SIze");
    }

}
