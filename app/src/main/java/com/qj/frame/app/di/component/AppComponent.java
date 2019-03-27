package com.qj.frame.app.di.component;

import android.content.Context;

import com.qj.common.datebase.dao.DaoSession;
import com.qj.frame.app.AppApplication;
import com.qj.frame.app.di.module.AppModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * @author qujun
 * @time 2018/8/30 15:19
 * Lonely people will pretend to be very busy.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(AppApplication application);

    Context getContext();

    DaoSession getDaoSession();
}
