package com.qj.common.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.alibaba.android.arouter.launcher.ARouter;
import com.qj.common.R;
import com.qj.common.toast.ToastCommand;
import com.qj.common.tool.other.StatusBarUtil;
import com.qj.common.view.loading.LoadingDialog;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView, View.OnClickListener {

    public T mPresenter;

    protected Bundle mBundle = null;

    protected LoadingDialog loadingDialog = null;

    protected Context mContext;

    protected Bundle savedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        mPresenter = getInstance(this, 1);

        mPresenter.attachView((V) this);

        int layoutResId = getLayoutResId();
        if (-1 == layoutResId) {
            throw new RuntimeException("layoutResId is not set!");
        }

        mContext = this;

        getLifecycle().addObserver(mPresenter);

        beforeSetView();

        setContentView(getLayoutResId());

        ButterKnife.bind(this);

        setStatusBar();

        readInfo();

        initViews();

        initToolBar();

        setListener();

        loadView();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId = getOptionsMenu();
        if (menuId == 0) {
            return true;
        }
        getMenuInflater().inflate(menuId, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }


    // =====================   方法  ==========================

    protected void beforeSetView() {

    }

    protected void initViews() {
        loadingDialog = LoadingDialog.getDialog(this);
    }

    protected void setListener() {
    }

    protected void initToolBar() {

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

    protected void toolBarNavListener() {

    }


    protected void readInfo() {
        mBundle = getIntent().getExtras();
    }

    protected void setStatusBar() {
        StatusBarUtil.setStatusBarFullTransparent(this);
        StatusBarUtil.setFitSystemWindow(false, this);
    }

    /**
     * 界面跳转
     *
     * @param clazz 目标Activity
     */
    protected void readyGo(Class<?> clazz) {
        readyGo(clazz, null);
    }


    /**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    protected void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    protected void readyGoByFlagThenKill(Class<?> clazz, int flags) {
        Intent intent = new Intent(this, clazz);
        intent.addFlags(flags);
        startActivity(intent);
        finish();
    }

    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * backForResult
     *
     * @param result 返回值
     * @param bundle 传回数据
     */
    protected void backForResult(int result, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(result, intent);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * Arouter界面跳转
     *
     * @param arouterPath 目标Activity
     */
    protected void readyGoArouter(String arouterPath) {
        ARouter.getInstance().build(arouterPath)
                .withTransition(R.anim.in_from_right, R.anim.out_to_left)
                .navigation();
    }

    /**
     * Arouter界面跳转
     *
     * @param arouterPath 目标Activity
     */
    protected void readyGoArouterWithBudle(String arouterPath, Bundle bundle) {
        ARouter.getInstance().build(arouterPath)
                .with(bundle)
                .withTransition(R.anim.in_from_right, R.anim.out_to_left)
                .navigation();
    }

    protected void leftFinish() {
        finish();
        left();
    }

    protected void rightFinish() {
        finish();
        right();
    }

    protected void left() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    protected void right() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @SuppressLint("CheckResult")
    protected void showToast(String msg, ToastCommand command) {

        switch (command) {
            case INFO:
                Toasty.info(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            case ERROR:
                Toasty.error(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCESS:
                Toasty.success(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNING:
                Toasty.warning(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            default:
                Toasty.normal(getContext(), msg, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    protected void onBack() {
        leftFinish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    // 监听手机上的BACK键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBack();
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


    // =====================   抽象方法  ==========================

    protected abstract int getLayoutResId();

    protected abstract Toolbar getToolBar();

    protected abstract String getToolBarTitle();

    protected abstract int getToolNavIcon();

    protected abstract int getOptionsMenu();

    protected abstract void loadView();
}
