package com.hqumath.androidmvvm.ui.add.addRenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;

import java.util.List;

/**
 * FileName: AddViewModel
 * Date: 2021/6/15 14:46
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class AddViewModel extends BaseViewModel<MyRepository> {
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> rentRoom = new MutableLiveData<>();
    public MutableLiveData<String> rentWater = new MutableLiveData<>();
    public MutableLiveData<String> mark = new MutableLiveData<>();

    public AddViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void saveRenter() {
        model.runInTransaction(() -> {
            /*query by name is exit or not*/
            RenterInfoEntity entity = new RenterInfoEntity();
            entity.setName(name.getValue());
            entity.setRent_room(Integer.parseInt(rentRoom.getValue()));
            entity.setRent_water(Integer.parseInt(rentWater.getValue()));
            entity.setMark(mark.getValue());
            List<RenterInfoEntity> renterInfoEntityList = model.findRenterByName(name.getValue());
            if (renterInfoEntityList != null && renterInfoEntityList.size() > 0) {
                //update
                entity.setId(renterInfoEntityList.get(0).getId());
                model.updateRenter(entity);
            }else {
                //add
                model.insertRenter(entity);
            }

        });
    }
}
