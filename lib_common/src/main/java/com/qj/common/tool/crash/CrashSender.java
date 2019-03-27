package com.qj.common.tool.crash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

/**
 * @author qujun
 * @time 2018/7/16 21:50
 * Lonely people will pretend to be very busy.
 */

public class CrashSender implements ReportSender {

    public CrashSender() {

    }

    @Override
    public void send(@NonNull Context context, @NonNull CrashReportData errorContent) throws ReportSenderException {
        String NEW_LINE = System.getProperty("line.separator");
        String err = "APP_VERSION_NAME : " + errorContent.getProperty(ReportField.APP_VERSION_NAME) + NEW_LINE +
                "ANDROID_VERSION : " + errorContent.getProperty(ReportField.ANDROID_VERSION) + NEW_LINE +
                "PHONE_MODEL : " + errorContent.getProperty(ReportField.PHONE_MODEL) + NEW_LINE +
                "STACK_TRACE : " + errorContent.getProperty(ReportField.STACK_TRACE) + NEW_LINE;
        Logger.e(err);
    }
}
