package com.hqumath.androidmvvm.ui.add.calculate;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.CalculatorListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityCalculatorMaterBinding;
import com.hqumath.androidmvvm.entity.ShowCalculatorBeans;
import com.hqumath.androidmvvm.ui.view.dialog.DialogCancelListener;

import java.util.Locale;
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
public class CalculatorMaterActivity extends BaseViewModelActivity<ActivityCalculatorMaterBinding, CalculatorMaterViewModel> {
    private static final String TAG = "AddMaterActivity";
//    private int renterId;
    private Integer rentRoom;
    private Integer rentWater;
    private String dates;

    private CalculatorListAdapter adapter;
    @Override
    public CalculatorMaterViewModel getViewModel() {
        return ViewModelProviders.of(this).get(CalculatorMaterViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_calculator_mater;
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

    }

    @Override
    public void initData() {
        dates = getIntent().getStringExtra("dates");
        binding.setViewModel(viewModel);
        viewModel.init(dates.split("_")[0],Double.parseDouble(dates.split("_")[1]),
                Double.parseDouble(dates.split("_")[2]));
        setTitle(dates.split("_")[0]);
        adapter = new CalculatorListAdapter(new CalculatorListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull ShowCalculatorBeans data) {
                Logger.getLogger("TAG").log(Level.INFO,"单击了："+data.toString());

//                Intent intent = new Intent(mContext, AddMaterActivity.class);
//                intent.putExtra("renter_id", String.valueOf(data.getRenter_id()));
//                intent.putExtra("renter_name", data.getName());
//                startActivity(intent);
            }

            @Override
            public void onLongClick(@NonNull ShowCalculatorBeans data) {

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
            Logger.getLogger(TAG).log(Level.INFO,"Visibility22="+b);
        });
    }

}
