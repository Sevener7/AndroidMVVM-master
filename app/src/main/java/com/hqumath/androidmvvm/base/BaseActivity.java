package com.hqumath.androidmvvm.base;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hqumath.androidmvvm.ui.view.dialog.DialogCancelListener;
import com.hqumath.androidmvvm.ui.view.dialog.DialogConfirmListener;
import com.hqumath.androidmvvm.ui.view.dialog.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * ****************************************************************
 * 文件名称: BaseActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/2 14:56
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseActivity<V extends ViewDataBinding> extends RxAppCompatActivity {
    protected V binding;
    protected BaseActivity mContext;
    private ProgressDialog mProgressDialog;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        binding.setLifecycleOwner(this);
        //viewModel
        initViewModel();
        //初始化ui
        initView();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    public void initViewModel() {
    }

    public abstract void initView();

    public abstract void initData();

    public void initViewObservable() {
    }

    public void showProgressDialog() {
        showProgressDialog("loading...");
    }

    public void showProgressDialog(String content) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(content);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示用户等待框
     *
     * @param msg 提示信息
     * @param confirmListener 确定回调
     * @param cancelListener 取消回调
     *
     */
    public void showDialog(String msg,String leftText,String rightText, DialogConfirmListener confirmListener,
                              DialogCancelListener cancelListener) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return;
        } else {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setLoading(msg,leftText,rightText,confirmListener,cancelListener);
            loadingDialog.show();
        }
    }


}
