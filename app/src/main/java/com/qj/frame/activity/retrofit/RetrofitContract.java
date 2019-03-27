package com.qj.frame.activity.retrofit;


import com.qj.common.mvp.BasePresenter;
import com.qj.common.mvp.BaseView;

public class RetrofitContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
