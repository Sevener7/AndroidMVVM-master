package com.hqumath.androidmvvm.ui.add.addTotalMater;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowCalculatorBeans;
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

    @SuppressLint("DefaultLocale")
    public void saveTotalMater() {
        model.runInTransaction(() -> {
            /*query by name is exit or not*/
            TotalMaterEntity entity = new TotalMaterEntity();
            entity.setDate(date.getValue());
            entity.setMater(Integer.parseInt(totalMater.getValue()));
            List<TotalMaterEntity> currentList = model.getAllTotalMaters();
            double useValue = 0;
            if(currentList.size()>0){
                double totalElect = (Integer.parseInt(totalMater.getValue()) - currentList.get(currentList.size() - 1).getMater());
                entity.setValue(Double.parseDouble(String.format("%.2f", totalElect)));
                List<ShowCalculatorBeans> list1 = model.getShowCalculatorBeanListByDate(date.getValue());
                for(ShowCalculatorBeans showCalculatorBeans : list1){
                    useValue+=showCalculatorBeans.getUse_mater();
                }
                entity.setShare_value(Double.parseDouble(String.format("%.2f", totalElect-useValue)));
            }
            model.insertTotalMater(entity);
        });
    }
}
