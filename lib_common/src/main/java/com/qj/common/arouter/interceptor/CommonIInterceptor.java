package com.qj.common.arouter.interceptor;


import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.orhanobut.logger.Logger;


/**
 * @author qujun
 * @des
 * @time 2019/2/26 10:36
 * Because had because, so had so, since has become since, why say why。
 **/
@Interceptor(priority = 1)
public class CommonIInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String group = postcard.getGroup();

        Logger.i("CommonIInterceptor" , group);

        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Logger.i("CommonIInterceptor" , "init");
    }
}
