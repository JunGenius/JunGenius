package com.qj.common.tool.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author qujun
 * @time 2018/7/19 8:42
 * Lonely people will pretend to be very busy.
 */

public class StatusBarUtil {

    /**
     * 全透状态栏
     */
    public static void setStatusBarFullTransparent(Context context) {

        Activity activity = (Activity) context;

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0

            Window window = activity.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            return;
        }

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 半透明状态栏
     */
    public static void setStatusHalfTransparent(Context context) {
        Activity activity = (Activity) context;

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            return;

        }

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */

    public static void setFitSystemWindow(boolean fitSystemWindow, Context context) {
        Activity activity = (Activity) context;
        View contentViewGroup = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }


}
