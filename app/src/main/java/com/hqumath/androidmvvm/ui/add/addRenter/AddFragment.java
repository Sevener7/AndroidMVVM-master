package com.hqumath.androidmvvm.ui.add.addRenter;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentAddBinding;

/**
 * FileName: AddFragment
 * Date: 2021/6/16 15:21
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class AddFragment extends BaseViewModelFragment<FragmentAddBinding, AddViewModel> {
    @Override
    public AddViewModel getViewModel() {
        return ViewModelProviders.of(this).get(AddViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_add;
    }

    @Override
    public void initView() {
        binding.btnAddSure.setOnClickListener(v -> {
            boolean valid = true;
            if (TextUtils.isEmpty(viewModel.name.getValue())) {
                valid = false;
                binding.userNameLayout.setError(getString(R.string.user_name_warning));
            } else {
                binding.userNameLayout.setErrorEnabled(false);
            }
            if (TextUtils.isEmpty(viewModel.rentRoom.getValue())) {
                valid = false;
                binding.userRentRoomLayout.setError(getString(R.string.password_warning));
            } else {
                binding.userRentRoomLayout.setErrorEnabled(false);
            }
            if (TextUtils.isEmpty(viewModel.rentWater.getValue())) {
                valid = false;
                binding.userRentWaterLayout.setError(getString(R.string.password_warning));
            } else {
                binding.userRentWaterLayout.setErrorEnabled(false);
            }
            if (TextUtils.isEmpty(viewModel.mark.getValue())) {
                valid = false;
                binding.userMarkLayout.setError(getString(R.string.password_warning));
            } else {
                binding.userMarkLayout.setErrorEnabled(false);
            }
            if (valid) {
                binding.btnAddSure.setEnabled(false);
                viewModel.saveRenter();
                binding.userNameEt.setText("");
                binding.userRentRoomEt.setText("");
                binding.userRentWaterEt.setText("");
                binding.userMarkEt.setText("");
                binding.btnAddSure.setEnabled(true);
            }
        });
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
    }
}
