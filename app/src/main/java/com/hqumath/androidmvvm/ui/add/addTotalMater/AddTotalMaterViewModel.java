package com.hqumath.androidmvvm.ui.add.addTotalMater;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;

import java.util.List;

/**
 * FileName: AddViewModel
 * Date: 2021/6/15 14:46
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class AddTotalMaterViewModel extends BaseViewModel<MyRepository> {
    public MutableLiveData<String> date = new MutableLiveData<>();
    public MutableLiveData<String> totalMater = new MutableLiveData<>();

    public AddTotalMaterViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void saveTotalMater() {
        model.runInTransaction(() -> {
            /*query by name is exit or not*/
            TotalMaterEntity entity = new TotalMaterEntity();
            entity.setDate(date.getValue());
            entity.setMater(Integer.parseInt(totalMater.getValue()));
            model.insertTotalMater(entity);
        });
    }
}
