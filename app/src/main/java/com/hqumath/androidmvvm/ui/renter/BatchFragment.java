package com.hqumath.androidmvvm.ui.renter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.RenterBatchPagedListAdapter;
import com.hqumath.androidmvvm.adapters.RenterPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentBatchBinding;
import com.hqumath.androidmvvm.databinding.FragmentRenterBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowBatchRenterInfoEntity;
import com.hqumath.androidmvvm.ui.add.addMater.AddMaterActivity;
import com.hqumath.androidmvvm.ui.add.addMater.UpdateMaterActivity;
import com.hqumath.androidmvvm.ui.view.dialog.DialogCancelListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * 文件名称: FollowingFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述: 我的追随者 paging分页 db+net
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BatchFragment extends BaseViewModelFragment<FragmentBatchBinding, BatchViewModel> {
    private static final String TAG = "RentersFragment";
    private RenterBatchPagedListAdapter adapter;
    private String date;

    @Override
    public BatchViewModel getViewModel() {
        return ViewModelProviders.of(this).get(BatchViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_batch;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        date = ft.format(dNow);
    }

    @Override
    public void initData() {
        viewModel.init();
        adapter = new RenterBatchPagedListAdapter(new RenterBatchPagedListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull ShowBatchRenterInfoEntity data) {
                Logger.getLogger(TAG).log(Level.INFO, "单击了：" + data.toString());
//                Intent intent = new Intent(mContext, AddMaterActivity.class);
//                intent.putExtra("renter_id", String.valueOf(data.getId()));
//                intent.putExtra("renter_data", String.format("%s_%s_%s",data.getName(),data.getRent_room(),
//                        data.getRent_water()));
//                startActivity(intent);
            }

            @Override
            public void onLongClick(@NonNull ShowBatchRenterInfoEntity data) {
                Logger.getLogger(TAG).log(Level.INFO, "长按了：" + data.toString());
//                showDialog(String.format(Locale.getDefault(),"请选择删除还是编辑%s",data.getName()),"删除","编辑",
//                        () -> {
//                            viewModel.deleteByRenterId(data.getId());
//                        }, new DialogCancelListener() {
//                    @Override
//                    public void onClickCancel() {
//                        Intent intent = new Intent(mContext, UpdateMaterActivity.class);
//                        intent.putExtra("renter_id", String.valueOf(data.getId()));
//                        intent.putExtra("renter_data", String.format("%s_%s_%s",data.getName(),data.getRent_room(),
//                                data.getRent_water()));
//                        startActivity(intent);
//                    }
//                });
            }

            @Override
            public void onRetry() {
                viewModel.retry();
            }

            @Override
            public void saveEdit(ShowBatchRenterInfoEntity renterInfoEntity, String value) {
                if (value.length() > 2) {
                    viewModel.addMater(renterInfoEntity,Integer.parseInt(value),date);
                }
            }
        });
        binding.list.setAdapter(adapter);
    }

    public void initViewObservable() {
        viewModel.list.observe(this, adapter::submitList);
        viewModel.refreshState.observe(this, state ->
                binding.swipeRefreshLayout.setRefreshing(state == NetworkState.LOADING));
//        viewModel.networkState.observe(this, adapter::setNetworkState);
    }
}
