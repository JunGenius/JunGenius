package com.qj.frame.activity.home.main;

import com.qj.common.mvp.BasePresenter;
import com.qj.common.mvp.BaseView;
import com.qj.frame.activity.home.main.bean.MainMenuInfo;

import java.util.List;


public class MainHomeContract {
    interface View extends BaseView {

        void setAdapter(List<MainMenuInfo> data);
    }

    interface Presenter extends BasePresenter<View> {

        void loadData();

        MainMenuInfo getMainMenuInfo(int pos);
    }
}
