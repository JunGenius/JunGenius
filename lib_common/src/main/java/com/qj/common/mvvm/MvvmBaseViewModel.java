package com.qj.common.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.qj.common.mvvm.data.SingleLiveEvent;
import com.qj.common.mvvm.data.SingleMutableLiveEvent;
import com.trello.rxlifecycle2.LifecycleProvider;
import java.util.HashMap;
import java.util.Map;


/**
 * des  基类 vm
 * author qujun
 * time 2019/2/27 10:57
 * Because had because, so had so, since has become since, why say why。
 **/
public class MvvmBaseViewModel extends AndroidViewModel implements IMvvmBaseViewModel {
    private UIChangeLiveData uc;
    private LifecycleProvider lifecycle;
    public MvvmBaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle;
    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }


    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    public final class UIChangeLiveData extends SingleMutableLiveEvent {

        private SingleMutableLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleMutableLiveEvent<Void> finishEvent;
        private SingleMutableLiveEvent<String> toastEvent;
        private SingleMutableLiveEvent<Void> onBackPressedEvent;

        public SingleMutableLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleMutableLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }
        public SingleMutableLiveEvent<String> getToastEvent() {
            return toastEvent = createLiveData(toastEvent);
        }

        public SingleMutableLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private SingleMutableLiveEvent createLiveData(SingleMutableLiveEvent liveData) {
            if (liveData == null) {
                liveData = new SingleMutableLiveEvent();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }
}
