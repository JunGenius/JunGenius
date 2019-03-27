package com.qj.frame.activity.home.main;


import com.qj.common.mvp.BasePresenterImpl;
import com.qj.frame.activity.home.main.bean.MainMenuInfo;
import com.qj.frame.activity.home.main.model.MainModelImpl;


public class MainHomePresenter extends BasePresenterImpl<MainHomeContract.View> implements MainHomeContract.Presenter {


    private MainModelImpl modelImpl = null;

    @Override
    public void attachView(MainHomeContract.View view) {
        super.attachView(view);
        modelImpl = new MainModelImpl();
    }

    @Override
    public void loadData() {
        mView.setAdapter(modelImpl.getMainMenuInfos());
    }

    @Override
    public MainMenuInfo getMainMenuInfo(int pos) {
        return modelImpl.getMainMenuInfoByPos(pos);
    }
}
