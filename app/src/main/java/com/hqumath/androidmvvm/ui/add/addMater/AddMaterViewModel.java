package com.hqumath.androidmvvm.ui.add.addMater;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.hqumath.androidmvvm.ui.add.addTotalMater.AddTotalMaterViewModel;
import com.hqumath.androidmvvm.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.reactivex.Observable;
import retrofit2.Retrofit;

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
public class AddMaterViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isRecycleVisible = new MutableLiveData<>();
    public MutableLiveData<String> mater = new MutableLiveData<>();
    public MutableLiveData<String> date = new MutableLiveData<>();
    public LiveData<PagedList<MaterInfoEntity>> list;
    private int pageSize = 10;//每页大小
    private int initialLoadPage = 3;//预加载页数

    public AddMaterViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        isRecycleVisible.setValue(true);
    }

    public void showAdd() {
        isRecycleVisible.setValue(false);
    }

    @SuppressLint("DefaultLocale")
    public int addMater(int renterId, int materId, int rentRoom, int rentWater) {
        MaterInfoEntity entity = new MaterInfoEntity();
        entity.setRenter_id(renterId);
        entity.setMater(Integer.parseInt(mater.getValue()));
        entity.setDate(date.getValue());
        List<MaterInfoEntity> currentList = model.getAllMatersById(renterId);
        System.out.println("currentList=" + Arrays.toString(currentList.toArray()));
        //第一个修改
        double totalElect;
        double totalElectMoney;
        double totalSpend;
        if(currentList.size() > 0){
            if (materId == currentList.get(0).getMater_id()) {
                totalElect = 0;
                totalElectMoney = 0;
                totalSpend = 0;
            } else {
                if (materId != -1) {
                    //编辑
                    totalElect = (Integer.parseInt(mater.getValue()) - currentList.get(currentList.size() - 2).getMater());
                } else {
                    //新增
                    totalElect = (Integer.parseInt(mater.getValue()) - currentList.get(currentList.size() - 1).getMater());
                }

                totalElectMoney = totalElect * 1.3;
                totalSpend = totalElectMoney + rentRoom + rentWater;

            }
        }else {
            totalElect = 0;
            totalElectMoney = 0;
            totalSpend = 0;
        }
        entity.setUse_mater(Double.parseDouble(String.format("%.2f", totalElect)));
        entity.setTotal_rent(Double.parseDouble(String.format("%.2f", totalElectMoney)));
        entity.setTotal_spend(Double.parseDouble(String.format("%.2f", totalSpend)));
        model.runInTransaction(() -> {
            model.insertMater(entity);
        });
        if (materId != -1) {
            //编辑之后，遍历更新totalElect
            List<MaterInfoEntity> currentList2 = model.getAllMatersById(renterId);
            for (int i = 1; i <= currentList2.size() - 1; i++) {
                MaterInfoEntity materInfoEntity = currentList2.get(i);
                totalElect = (currentList2.get(i).getMater() - currentList2.get(i - 1).getMater());
                totalElectMoney = totalElect * 1.3;
                totalSpend = totalElectMoney + rentRoom + rentWater;
                materInfoEntity.setUse_mater(Double.parseDouble(String.format("%.2f", totalElect)));
                materInfoEntity.setTotal_rent(Double.parseDouble(String.format("%.2f", totalElectMoney)));
                materInfoEntity.setTotal_spend(Double.parseDouble(String.format("%.2f", totalSpend)));
                model.runInTransaction(() -> {
                    model.insertMater(materInfoEntity);
                });
            }
            return -1;
        }
        isRecycleVisible.setValue(true);
        return 0;
    }

    @SuppressLint("DefaultLocale")
    public void init(int renterId, int rentRoom, int rentWater) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        date.setValue(ft.format(dNow));
/*        //update result
        List<MaterInfoEntity> currentList = model.getAllMatersById(renterId);
        Logger.getLogger("List<MaterInfoEntity>").log(Level.INFO, Arrays.toString(currentList.toArray()));
        for (int i = 1; i < currentList.size()+1; i++) {
            if (i <= currentList.size() - 1 && currentList.get(i).getTotal_rent() < 1) {
                MaterInfoEntity materInfoEntity = currentList.get(i);
                double totalElect = (currentList.get(i).getMater() - currentList.get(i - 1).getMater());
                double totalElectMoney = totalElect*1.3;
                i++;
                double totalSpend = totalElectMoney + rentRoom + rentWater;
                materInfoEntity.setUse_mater(Double.parseDouble(String.format("%.2f", totalElect)));
                materInfoEntity.setTotal_rent(Double.parseDouble(String.format("%.2f", totalElectMoney)));
                materInfoEntity.setTotal_spend(Double.parseDouble(String.format("%.2f", totalSpend)));
                model.updateMater(materInfoEntity);
//                list.notify();
            }
        }
//        calculator(renterId, rentRoom, rentWater);
*/
        list = new LivePagedListBuilder<>(
                model.loadMatersByRenterId(renterId),
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize * initialLoadPage)
                        .build())
                .setFetchExecutor(appExecutors.diskIO())
//                .setBoundaryCallback(boundaryCallback)
                .build();
    }

}
