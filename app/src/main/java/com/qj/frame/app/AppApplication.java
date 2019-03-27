package com.qj.frame.app;

import android.app.Application;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bulong.rudeness.RudenessScreenHelper;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.qj.client.net.JYRetrofitConfigManager;
import com.qj.client.ws.WebServiceConfigManager;
import com.qj.common.tool.other.Utils;
import com.qj.frame.app.di.component.AppComponent;
import com.qj.frame.app.di.component.DaggerAppComponent;
import com.qj.frame.app.di.module.AppModule;
import com.qj.frame.app.thinker.SimpleTinkerInApplicationLike;

import org.acra.ACRA;
import org.acra.config.ACRAConfiguration;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

/**
 * @author qujun
 * @time 2018/8/30 14:57
 * Lonely people will pretend to be very busy.
 */

public class AppApplication extends SimpleTinkerInApplicationLike {

    public static AppComponent appComponent;

    @Inject
    public DiskLogAdapter diskLogAdapter;

    @Inject
    public ACRAConfiguration config;

    private Application application;

    public AppApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
        this.application = application;


    }


    @Override
    public void onCreate() {

        super.onCreate();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(application)).build();

        // 依赖注入
        appComponent.inject(this);

        initUntil();

        initAcra();

        initLogger();

        initScreenAdapter();

        initFrescoImage();

//        initX5();

        iniToast();

        initRetrofitConfig();

        initArouter();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


    private void initUntil() {
        Utils.init(application);
    }

    private void initAcra() {
        ACRA.init(application, config);
    }

    private void initLogger() {
        Logger.addLogAdapter(diskLogAdapter);
    }

    private void initScreenAdapter() {
        //设计图标注的宽度
        int designWidth = 205;
        new RudenessScreenHelper(application, designWidth).activate();
    }

    private void initFrescoImage() {
//        FrescoImageLoader.init(this, R.color.colorBluePrimary, FileUtil.getSdAbsolutePath() + AppInfo.ImagePath);
    }

//    private void initX5() {
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Logger.i("X5", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(application.getApplicationContext(), cb);
//    }


    private void iniToast() {
        Toasty.Config.getInstance().setTextSize(14).apply();
    }


    private void initRetrofitConfig() {

        new JYRetrofitConfigManager.Builder().setBASE_URL("http://www.wanandroid.com/").build();

        new WebServiceConfigManager.Builder()

                .setBASE_URL("http://61.161.203.93:8081/dzbc/") // 请求地址

                .setNAME_SPACE("http://quickhigh.net/") // 命名空间

                .setHEAD_PAGE("SJBooks.asmx") // 页面

                .build();
    }

    private void initArouter() {
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(application);
    }
}
