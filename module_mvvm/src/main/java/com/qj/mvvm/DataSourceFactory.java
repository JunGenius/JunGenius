package com.qj.mvvm;

import android.arch.paging.DataSource;
import android.arch.paging.ItemKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;

import com.qj.common.model.WanSubInfo;

import java.util.List;


/**
 * @author qujun
 * @time 2018/11/12 14:25
 * Lonely people will pretend to be very busy.
 */

public class DataSourceFactory {

    private LimitOffsetDataSource<WanSubInfo> dataSource;

    public DataSourceFactory(Context context) {

//        dataSource = new LimitOffsetDataSource<WanSubInfo>(context) {
//            @Override
//            protected List<WanSubInfo> convertData(List<WanSubInfo> dataList) {
//                return dataList;
//            }
//        };

        dataSource = new LimitOffsetDataSource<WanSubInfo>(context) {


            @Override
            protected List<WanSubInfo> convertData(List<WanSubInfo> dataList) {
                return dataList;
            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<WanSubInfo> callback) {
                super.loadBefore(params, callback);

            }
        };
    }

    public DataSource.Factory<Integer, WanSubInfo> getEquInfoPagedList() {

        return new DataSource.Factory<Integer, WanSubInfo>() {
            @Override
            public LimitOffsetDataSource<WanSubInfo> create() {

                return dataSource;
            }
        };
    }

}
