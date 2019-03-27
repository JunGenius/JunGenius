package com.qj.frame.app.di.module;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qj.common.datebase.dao.DaoMaster;
import com.qj.common.datebase.dao.DaoSession;
import com.qj.common.tool.crash.CrashSenderFactory;
import com.qj.frame.R;
import com.qj.frame.app.model.AppInfo;

import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.config.ACRAConfiguration;
import org.acra.config.ACRAConfigurationException;
import org.acra.config.ConfigurationBuilder;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author qujun
 * @time 2018/8/30 15:01
 * Lonely people will pretend to be very busy.
 */

@Module
public class AppModule {

    private Context context;

    private DaoSession daoSession;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    DiskLogAdapter provideDiskLogAdapter() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
//                .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag(AppInfo.APP_TAG)   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        FormatStrategy strategy = CsvFormatStrategy.newBuilder()
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK))
                .Path(AppInfo.LoggerPath)
                .Level(Logger.ERROR)
                .build();

        return new DiskLogAdapter(strategy);
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {

        if (daoSession != null)
            return daoSession;
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(context, "maintenance-db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        daoSession = mDaoMaster.newSession();
        return daoSession;
    }

    @Provides
    @Singleton
    ACRAConfiguration provideACRAConfiguration() {
        try {
            return new ConfigurationBuilder((Application) context)
                    .setReportSenderFactoryClasses(CrashSenderFactory.class)
                    .setCustomReportContent(ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT)
                    .setMode(ReportingInteractionMode.TOAST)
                    .setResToastText(R.string.crash_toast_text)
                    .build();
        } catch (ACRAConfigurationException e) {
            e.printStackTrace();
            Logger.e("ACRAConfiguration", e.toString());
            return null;
        }
    }

}
