package com.qj.mvvm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.qj.common.model.WanSubInfo;
import com.qj.common.mvvm.MvvmBaseViewModel;
import com.qj.common.mvvm.binding.command.BindingAction;
import com.qj.common.mvvm.binding.command.BindingCommand;

import java.util.concurrent.Executor;

/**
 * @author qujun
 * @des
 * @time 2019/2/15 15:18
 * Because had because, so had so, since has become since, why say why。
 **/

public class MvvmViewModel extends MvvmBaseViewModel {

    //生命周期观察的数据
    private LiveData<PagedList<WanSubInfo>> mLiveObservableData;

    private PagedList.Config myPagingConfig = null;

    private DataSourceFactory factoty = null;

    public MvvmViewModel(@NonNull Application application) {
        super(application);

        myPagingConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build();

        factoty = new DataSourceFactory(application);

    }


    public LiveData<PagedList<WanSubInfo>> getLiveObservableData() {

        if (mLiveObservableData != null)
            return mLiveObservableData;

        mLiveObservableData = new LivePagedListBuilder<>(
                factoty.getEquInfoPagedList(), /* page size */ myPagingConfig).setFetchExecutor(new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                command.run();
            }
        }).build();

        return mLiveObservableData;
    }
}
