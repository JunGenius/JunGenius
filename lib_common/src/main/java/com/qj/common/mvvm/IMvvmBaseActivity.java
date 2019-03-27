package com.qj.common.mvvm;

/**
 * des   基类activity接口
 * author qujun
 * time 2019/2/27 10:35
 * Because had because, so had so, since has become since, why say why。
 **/
public interface IMvvmBaseActivity {

    /**
     * 初始化界面传递参数
     */
    void initParam();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

    /**
     *  初始化toolbar
     */
    void initToolBar();
}
