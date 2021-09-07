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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class UpdateMaterViewModel extends BaseViewModel<MyRepository> {
    private int renterId;
    public MutableLiveData<String> renterRoom = new MutableLiveData<>();
    public MutableLiveData<String> renterWater = new MutableLiveData<>();
    public MutableLiveData<String> renterName = new MutableLiveData<>();
    public MutableLiveData<String> renterMark = new MutableLiveData<>();


    public UpdateMaterViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void save() {
        RenterInfoEntity renterInfoEntity = new RenterInfoEntity();
        renterInfoEntity.setId(renterId);
        renterInfoEntity.setMark(renterMark.getValue());
        renterInfoEntity.setName(renterName.getValue());
        renterInfoEntity.setRent_room(Integer.parseInt(renterRoom.getValue()));
        renterInfoEntity.setRent_water(Integer.parseInt(renterWater.getValue()));
        model.runInTransaction(() -> {
            model.updateRenter(renterInfoEntity);
        });
    }

    @SuppressLint("DefaultLocale")
    public void init(int renterId) {
        this.renterId = renterId;
        List<RenterInfoEntity> renterInfoEntityList = model.findRenterById(renterId);
        if(renterInfoEntityList.size()>0){
            renterRoom.setValue(String.valueOf(renterInfoEntityList.get(0).getRent_room()));
            renterMark.setValue(String.valueOf(renterInfoEntityList.get(0).getMark()));
            renterWater.setValue(String.valueOf(renterInfoEntityList.get(0).getRent_water()));
            renterName.setValue(String.valueOf(renterInfoEntityList.get(0).getName()));
        }
    }

}
