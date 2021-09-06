package com.hqumath.androidmvvm.ui.view.dialog;

/**
 * FileName: LoadingDialog
 * Date: 2021/6/18 15:08
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.DialogLoadingBinding;

import io.reactivex.annotations.NonNull;


public class LoadingDialog extends Dialog {

    private DialogLoadingBinding binding;


    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Fullscreen);
        setCanceledOnTouchOutside(false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dialog_loading, null, false);
        setContentView(binding.getRoot());
    }

    public void setLoading(String msg,String leftText,String rightText, DialogConfirmListener confirmListener,
                              DialogCancelListener cancelListener) {
        if (!TextUtils.isEmpty(msg)) {
            binding.tvContent.setText(msg);
            binding.btnConfirm.setText(leftText);
            binding.btnCancel.setText(rightText);
        }
        if (confirmListener != null) {
            binding.btnConfirm.setVisibility(View.VISIBLE);
            binding.btnConfirm.setOnClickListener(v -> {
                confirmListener.onClickConfirm();
                dismiss();
            });

        } else {
            binding.btnConfirm.setVisibility(View.GONE);
        }
        if (cancelListener != null) {
            binding.btnCancel.setVisibility(View.VISIBLE);
            binding.btnCancel.setOnClickListener(v -> {
                cancelListener.onClickCancel();
                dismiss();
            });

        } else {
            binding.btnCancel.setVisibility(View.GONE);
        }

    }


}

