package com.hqumath.androidmvvm.ui.add.addMater;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MaterPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityAddMaterBinding;
import com.hqumath.androidmvvm.entity.MaterInfoEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * 文件名称: ProfileActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/26 10:04
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AddMaterActivity extends BaseViewModelActivity<ActivityAddMaterBinding, AddMaterViewModel> {
    private static final String TAG = "AddMaterActivity";
    private int renterId;
    private String renterName;
    private Integer rentRoom;
    private Integer rentWater;
    private int materId = -1;

    private MaterPagedListAdapter adapter;

    @Override
    public AddMaterViewModel getViewModel() {
        return ViewModelProviders.of(this).get(AddMaterViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_mater;
    }

    @Override
    public void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        binding.btnAddMater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = viewModel.addMater(renterId, materId, rentRoom, rentWater);
                materId = -1;
                if (result == -1) {
                    finish();
                }
//                Logger.getLogger(TAG).log(Level.INFO,"Visibility="+binding.list.getVisibility());
            }
        });
    }

    @Override
    public void initData() {
        renterId = Integer.parseInt(getIntent().getStringExtra("renter_id"));
        //binding.toolbar.setTitle(userName);
        String data[] = getIntent().getStringExtra("renter_data").split("_");
        renterName = data[0];
        rentRoom = Integer.parseInt(data[1]);
        rentWater = Integer.parseInt(data[2]);
        setTitle(data[0]);
        binding.setViewModel(viewModel);
        viewModel.init(renterId, rentRoom, rentWater);
        adapter = new MaterPagedListAdapter(new MaterPagedListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull MaterInfoEntity data) {
                Logger.getLogger("TAG").log(Level.INFO, "单击了：" + data.toString());
//                Intent intent = new Intent(mContext, AddMaterActivity.class);
//                intent.putExtra("renter_id", String.valueOf(data.getRenter_id()));
//                intent.putExtra("renter_name", data.getName());
//                startActivity(intent);
                viewModel.showAdd();
                viewModel.mater.setValue(data.getMater().toString());
                viewModel.date.setValue(data.getDate());
                materId = data.getMater_id();
            }

            @Override
            public void onLongClick(@NonNull MaterInfoEntity data) {

            }

            @Override
            public void onRetry() {
//                viewModel.retry();
            }
        });
        binding.list.setAdapter(adapter);
    }

    public void initViewObservable() {
        viewModel.list.observe(this, adapter::submitList);
        viewModel.isRecycleVisible.observe(this, b -> {
            Logger.getLogger(TAG).log(Level.INFO, "Visibility22=" + b);
        });
    }


}
