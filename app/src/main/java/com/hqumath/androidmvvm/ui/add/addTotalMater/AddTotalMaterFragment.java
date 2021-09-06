package com.hqumath.androidmvvm.ui.add.addTotalMater;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentAddBinding;
import com.hqumath.androidmvvm.databinding.FragmentAddTotalMaterBinding;
import com.hqumath.androidmvvm.ui.add.addRenter.AddViewModel;

/**
 * FileName: AddFragment
 * Date: 2021/6/16 15:21
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class AddTotalMaterFragment extends BaseViewModelFragment<FragmentAddTotalMaterBinding, AddTotalMaterViewModel> {
    @Override
    public AddTotalMaterViewModel getViewModel() {
        return ViewModelProviders.of(this).get(AddTotalMaterViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_add_total_mater;
    }

    @Override
    public void initView() {
        binding.btnAddSure.setOnClickListener(v -> {
            boolean valid = true;
            if (TextUtils.isEmpty(viewModel.date.getValue())) {
                valid = false;
                binding.userNameLayout.setError(getString(R.string.user_name_warning));
            } else {
                binding.userNameLayout.setErrorEnabled(false);
            }
            if (TextUtils.isEmpty(viewModel.totalMater.getValue())) {
                valid = false;
                binding.userRentRoomLayout.setError(getString(R.string.password_warning));
            } else {
                binding.userRentRoomLayout.setErrorEnabled(false);
            }

            if (valid) {
                binding.btnAddSure.setEnabled(false);
                viewModel.saveTotalMater();
                binding.userNameEt.setText("");
                binding.userRentRoomEt.setText("");
                binding.btnAddSure.setEnabled(true);
            }
        });
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
    }
}
