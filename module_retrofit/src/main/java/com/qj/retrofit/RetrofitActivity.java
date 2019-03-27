package com.qj.retrofit;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qj.common.arouter.ARouterPath;
import com.qj.common.mvvm.MvvmBaseActivity;
import com.qj.retrofit.databinding.RetrofitActivityBinding;

/**
 * @author qujun
 * @des
 * @time 2019/3/8 09:50
 * Because had because, so had so, since has become since, why say why。
 **/

@Route(path = ARouterPath.RetrofitAty)
public class RetrofitActivity extends MvvmBaseActivity<RetrofitActivityBinding , RetrofitViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.retrofit_activity;
    }

    @Override
    public int initVariableId() {
        return com.qj.retrofit.BR.viewModel;
    }

    @Override
    public Toolbar getToolBar() {
        return binding.includeHeadView.toolbar;
    }

    @Override
    public String getToolBarTitle() {
        return "Retrofit + RxJava2 网络请求库";
    }

    @Override
    public int getToolNavIcon() {
        return R.mipmap.ic_toolbar_back;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.getData().observe(this, new Observer<RetrofitModel>() {
            @Override
            public void onChanged(@Nullable RetrofitModel retrofitModel) {
                binding.setMDataModel(retrofitModel);
            }
        });
    }
}
