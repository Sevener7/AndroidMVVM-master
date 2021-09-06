package com.hqumath.androidmvvm.ui.renter;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.RenterPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentRenterBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.ui.add.addMater.AddMaterActivity;
import com.hqumath.androidmvvm.ui.profile.ProfileActivity;
import com.hqumath.androidmvvm.ui.view.dialog.DialogCancelListener;
import com.hqumath.androidmvvm.ui.view.dialog.DialogConfirmListener;

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
public class RentersFragment extends BaseViewModelFragment<FragmentRenterBinding, RentersViewModel> {
    private static final String TAG = "RentersFragment";
    private RenterPagedListAdapter adapter;

    @Override
    public RentersViewModel getViewModel() {
        return ViewModelProviders.of(this).get(RentersViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_renter;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);
    }

    @Override
    public void initData() {
        viewModel.init();
        adapter = new RenterPagedListAdapter(new RenterPagedListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull RenterInfoEntity data) {
                Logger.getLogger(TAG).log(Level.INFO,"单击了："+data.toString());
                Intent intent = new Intent(mContext, AddMaterActivity.class);
                intent.putExtra("renter_id", String.valueOf(data.getId()));
                intent.putExtra("renter_data", String.format("%s_%s_%s",data.getName(),data.getRent_room(),
                        data.getRent_water()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(@NonNull RenterInfoEntity data) {
                Logger.getLogger(TAG).log(Level.INFO,"长按了："+data.toString());
                showDialog(String.format(Locale.getDefault(),"确定删除%s吗",data.getName()),"删除","取消",
                        () -> {
                            viewModel.deleteByRenterId(data.getId());
                        }, new DialogCancelListener() {
                    @Override
                    public void onClickCancel() {
                        Logger.getLogger(TAG).log(Level.INFO,"点击了取消，对话框消失");
                    }
                });
            }

            @Override
            public void onRetry() {
                viewModel.retry();
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
