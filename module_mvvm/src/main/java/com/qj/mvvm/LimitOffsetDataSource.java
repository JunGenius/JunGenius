package com.qj.mvvm;

import android.arch.paging.ItemKeyedDataSource;
import android.arch.paging.PositionalDataSource;
import android.content.Context;
import android.support.annotation.NonNull;
import com.qj.client.net.JYRetrofitClient;
import com.qj.common.model.WanAndroidInfo;
import com.qj.common.model.WanSubInfo;
import com.qj.common.service.MyApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author qujun
 * @time 2018/11/9 16:16
 * Lonely people will pretend to be very busy.
 */

public abstract class LimitOffsetDataSource<T> extends ItemKeyedDataSource<Integer , WanSubInfo> {


    private Context mContext;

    private MyApiService service;

    private int mTotalSize = 0;

    private int mPage = 0;

    public LimitOffsetDataSource(Context mContext) {
        this.mContext = mContext;
        service = JYRetrofitClient.getInstance(mContext).create(MyApiService.class);
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<WanSubInfo> callback) {

        JYRetrofitClient.getInstance(mContext).execute(
                service.getData( 0 ), new Observer<WanAndroidInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidInfo info) {

                        mTotalSize = info.getData().getTotal();

                        if (mTotalSize == 0) {
                            //如果当前表数据为空直接返回
                            callback.onResult(new ArrayList<WanSubInfo>());

                            return;
                        }
//
                        callback.onResult(info.getData().getDatas());

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onResult(new ArrayList<WanSubInfo>());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    @Override
//    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<T> callback) {
//
//
//        JYRetrofitClient.getInstance(mContext).execute(
//                service.getData( 0 ), new Observer<WanAndroidInfo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(WanAndroidInfo info) {
//
//                        mTotalSize = info.getData().getTotal();
//
//                        if (mTotalSize == 0) {
//                            //如果当前表数据为空直接返回
//                            callback.onResult(Collections.<T>emptyList(), 0, 0);
//
//                            return;
//                        }
//                        final int firstLoadPosition = computeInitialLoadPosition(params, mTotalSize);
//
//                        final int firstLoadSize = computeInitialLoadSize(params, firstLoadPosition, mTotalSize);
//
//                        if (info.getData().getDatas() != null && info.getData().getDatas().size() == firstLoadSize) {
//                            callback.onResult((List<T>)info.getData().getDatas(), firstLoadPosition, mTotalSize);
//                        } else {
//                            // null list, or size doesn't match request - DB modified between count and load
//                            invalidate();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onResult(Collections.<T>emptyList(), 0, 0);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//        // bound the size requested, based on known count
//
////        List<T> list = loadRange(firstLoadPosition, firstLoadSize);
////        if (list != null && list.size() == firstLoadSize) {
////            callback.onResult(list, firstLoadPosition, totalCount);
////        } else {
////            // null list, or size doesn't match request - DB modified between count and load
////            invalidate();
////        }
//    }

    /**
     * 提供对外数据接口方便改数据需求
     *
     * @param dataList
     * @return
     */
    @SuppressWarnings("WeakerAccess")
    protected abstract List<T> convertData(List<T> dataList);

//    @Override
//    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<T> callback) {
//
//        if(params.startPosition == mTotalSize)
//            return;
//        loadRange(params.startPosition / params.loadSize, callback);
//    }
//
//    @Override
//    public boolean isInvalid() {
//        return super.isInvalid();
//    }
//
//
//    public void loadRange(int page, final LoadRangeCallback<T> callback) {
//
//
//        // execute and add observable to RxJava
//        JYRetrofitClient.getInstance(mContext).execute(
//                service.getData(page), new Observer<WanAndroidInfo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(WanAndroidInfo info) {
//                        if (info != null) {
//                            callback.onResult((List<T>) info.getData().getDatas());
//                        } else {
//                            invalidate();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }


    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<WanSubInfo> callback) {
        JYRetrofitClient.getInstance(mContext).execute(
                service.getData(params.key), new Observer<WanAndroidInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidInfo info) {
                        if (info != null) {
                            callback.onResult( info.getData().getDatas());
                        } else {
                            invalidate();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onResult(new ArrayList<WanSubInfo>());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<WanSubInfo> callback) {
//        JYRetrofitClient.getInstance(mContext).execute(
//                service.getData( 0 ), new Observer<WanAndroidInfo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(WanAndroidInfo info) {
//
//                        mTotalSize = info.getData().getTotal();
//
//                        if (mTotalSize == 0) {
//                            //如果当前表数据为空直接返回
//                            callback.onResult(new ArrayList<WanSubInfo>());
//
//                            return;
//                        }
////
//                        callback.onResult(info.getData().getDatas());
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onResult(new ArrayList<WanSubInfo>());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull WanSubInfo item) {
        return mPage++;
    }
}
