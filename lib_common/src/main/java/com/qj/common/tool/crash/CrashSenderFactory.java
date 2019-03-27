package com.qj.common.tool.crash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qj.common.tool.crash.CrashSender;

import org.acra.config.ACRAConfiguration;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderFactory;

/**
 * @author qujun
 * @time 2018/7/16 22:20
 * Lonely people will pretend to be very busy.
 */

public class CrashSenderFactory implements ReportSenderFactory {
    @NonNull
    @Override
    public ReportSender create(@NonNull Context context, @NonNull ACRAConfiguration config) {
        return new CrashSender();
    }
}
