package com.qj.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qj.common.arouter.ARouterPath;
import com.qj.common.model.WanSubInfo;
import com.qj.common.mvvm.MvvmBaseActivity;
import com.qj.common.tool.other.StatusBarUtil;
import com.qj.mvvm.databinding.MvvmHomeBinding;

/**
 * author qujun
 * des
 * time 2019/2/15 15:12
 * Because had because, so had so, since has become since, why say why。
 **/

//路径需要注意的是至少需要有两级，/xx/xx
@Route(path = ARouterPath.MvvmListAty)
public class MvvmActivity extends MvvmBaseActivity<MvvmHomeBinding, MvvmViewModel> {

    private MvvmTestAdapter mvvmAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.mvvm_home;
    }

    @Override
    public int initVariableId() {
        return com.qj.mvvm.BR.mvvmItemModel;
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void initData() {
        super.initData();

        mvvmAdapter = new MvvmTestAdapter();

        binding.setMvvmAdapter(mvvmAdapter);

        binding.setLinearLayoutManager(new LinearLayoutManager(this));
//        // Layout Managers:
//        binding.girlsList.setLayoutManager(new LinearLayoutManager(this));
//
//        binding.girlsList.setNestedScrollingEnabled(false);

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.getLiveObservableData().observe(this, new Observer<PagedList<WanSubInfo>>() {
            @Override
            public void onChanged(@Nullable PagedList<WanSubInfo> wanSubInfos) {
                mvvmAdapter.submitList(wanSubInfos);
            }
        });
    }


    @Override
    public MvvmViewModel initViewModel() {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        return ViewModelProviders.of(this).get(MvvmViewModel.class);
    }

    @Override
    public Toolbar getToolBar() {
        return binding.includeHeadView.toolbar;
    }

    @Override
    public int getToolNavIcon() {
        return R.mipmap.ic_toolbar_back;
    }

    @Override
    public String getToolBarTitle() {
        return "Mvvm";
    }
}
