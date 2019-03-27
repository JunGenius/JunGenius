package com.qj.mvvm;

import android.app.Application;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.qj.common.mvvm.MvvmBaseViewModel;
import com.qj.mvvm.MvvmItemModel;

/**
 * @author qujun
 * @des
 * @time 2019/2/15 15:20
 * Because had because, so had so, since has become since, why say why。
 **/

public class MvvmModel {

    private PagedList<MvvmItemModel> data = null;



    public PagedList<MvvmItemModel> getData() {
        return data;
    }

    public void setData(PagedList<MvvmItemModel> data) {
        this.data = data;
    }
}
