package com.qj.frame.activity.thinker;


import com.qj.common.mvp.BasePresenter;
import com.qj.common.mvp.BaseView;

public class ThinkerContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
