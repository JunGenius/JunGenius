package com.qj.common.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.qj.common.R;


/**
 * @author qujun
 * @time 2018/7/31 16:49
 * Lonely people will pretend to be very busy.
 */

public class LoadingDialog extends Dialog {

    private static LoadingDialog dialog;

    private LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    //显示dialog的方法
    public static LoadingDialog getDialog(Context context) {

        if (dialog != null)
            return dialog;

        dialog = new LoadingDialog(context, R.style.loading_dialog_style);
        dialog.setContentView(R.layout.loading_dialog);//dialog布局文件
        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog

        return dialog;
    }
}
