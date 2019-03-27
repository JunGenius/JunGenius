package com.qj.common.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.qj.common.toast.ToastCommand;
import com.qj.common.tool.other.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import es.dmoral.toasty.Toasty;


/**
 * des   activity 基类
 * author qujun
 * time 2019/2/27 10:33
 * Because had because, so had so, since has become since, why say why。
 **/

public abstract class MvvmBaseActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends RxAppCompatActivity implements IMvvmBaseActivity {

    protected V binding;
    protected VM viewModel;
    private int viewModelId;

    protected Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        // 状态栏
        setStatusBar();
        // toolbar
        if (isCustomToolBar())
            initToolBar();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = MvvmBaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }


    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    private void registorUIChangeLiveDataCallBack() {

        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(MvvmBaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(MvvmBaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });

        viewModel.getUC().getToastEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String info) {
                showToast(info, ToastCommand.INFO);
            }
        });
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void showToast(String msg, ToastCommand command) {

        switch (command) {
            case INFO:
                Toasty.info(mContext, msg, Toast.LENGTH_SHORT, true).show();
                break;
            case ERROR:
                Toasty.error(mContext, msg, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCESS:
                Toasty.success(mContext, msg, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNING:
                Toasty.warning(mContext, msg, Toast.LENGTH_SHORT, true).show();
                break;
            default:
                Toasty.normal(mContext, msg, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * =====================================================================
     **/

    @Override
    public void initParam() {
        mContext = this;
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initToolBar() {
        Toolbar toolbar = getToolBar();
        if (toolbar == null) {
            return;
        }
        // 只能在setSupportActionBar 之前设置, 否则无效
        toolbar.setTitle(getToolBarTitle());
        setSupportActionBar(toolbar);
        int icon = getToolNavIcon();
        if (icon > 0) {
            toolbar.setNavigationIcon(icon);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarNavListener();
            }
        });
    }

    /**
     * toolbar
     */
    public abstract Toolbar getToolBar();

    /**
     * toolbar 标题
     */
    public abstract String getToolBarTitle();

    /**
     * toolbar  nav 图标
     */
    public abstract @DrawableRes
    int getToolNavIcon();

    /**
     * toolbar  nav 点击事件
     */
    public void toolBarNavListener() {

    }

    /**
     * 设置状态栏
     */
    public void setStatusBar() {
        StatusBarUtil.setStatusBarFullTransparent(this);
        StatusBarUtil.setFitSystemWindow(false, this);
    }

    // 是否自定义toolbar
    public boolean isCustomToolBar() {
        return true;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }
}
