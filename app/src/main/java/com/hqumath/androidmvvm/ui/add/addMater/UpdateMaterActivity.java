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
import com.hqumath.androidmvvm.databinding.ActivityUpdateMaterBinding;
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
public class UpdateMaterActivity extends BaseViewModelActivity<ActivityUpdateMaterBinding, UpdateMaterViewModel> {
    private static final String TAG = "AddMaterActivity";
    private int renterId;
    @Override
    public UpdateMaterViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UpdateMaterViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_update_mater;
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
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initData() {
        renterId = Integer.parseInt(getIntent().getStringExtra("renter_id"));
        //binding.toolbar.setTitle(userName);
        String data[] = getIntent().getStringExtra("renter_data").split("_");
        setTitle(data[0]);
        binding.setViewModel(viewModel);
        viewModel.init(renterId);
    }
    public void initViewObservable() {

    }


}
