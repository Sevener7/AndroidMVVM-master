package com.hqumath.androidmvvm.ui.renter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyReposPagedListAdapter;
import com.hqumath.androidmvvm.adapters.TotalRenterListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentSwipeListBinding;
import com.hqumath.androidmvvm.databinding.FragmentTotalRenterBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;
import com.hqumath.androidmvvm.ui.add.calculate.CalculatorMaterActivity;
import com.hqumath.androidmvvm.ui.repos.ReposDetailActivity;
import com.hqumath.androidmvvm.ui.repos.StarredViewModel;
import com.hqumath.androidmvvm.ui.view.dialog.DialogCancelListener;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * 文件名称: StarredFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class TotalRenterFragment extends BaseViewModelFragment<FragmentTotalRenterBinding, TotalRenterViewModel> {

    private TotalRenterListAdapter adapter;

    @Override
    public TotalRenterViewModel getViewModel() {
        return ViewModelProviders.of(this).get(TotalRenterViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_total_renter;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);
    }

    @Override
    public void initData() {
        viewModel.init();
        adapter = new TotalRenterListAdapter(new TotalRenterListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull TotalMaterEntity data) {
                Intent intent = new Intent(mContext, CalculatorMaterActivity.class);
                intent.putExtra("dates", data.getDate()+"_"+data.getValue()+"_"+data.getShare_value());
                startActivity(intent);
            }

            @Override
            public void onLongClick(@NonNull TotalMaterEntity data) {
                showDialog(String.format(Locale.getDefault(),"确定删除%s吗",data.getDate()),"删除","取消",
                        () -> {
                            viewModel.deleteByDate(data.getDate());
                        }, new DialogCancelListener() {
                            @Override
                            public void onClickCancel() {
                                Logger.getLogger("TAG").log(Level.INFO,"点击了取消，对话框消失");
                            }
                        });
            }

            @Override
            public void onRetry() {

                viewModel.retry();
            }
        });
        binding.totalList.setAdapter(adapter);
    }

    public void initViewObservable() {
        viewModel.list.observe(this, adapter::submitList);
    }
}
