package com.qj.retrofit;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.qj.client.iter.JYCallBack;
import com.qj.client.net.JYRetrofitClient;
import com.qj.common.model.WanAndroidInfo;
import com.qj.common.service.MyApiService;
import com.qj.common.tool.other.Utils;
import com.qj.retrofit.webservice.TestWs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author qujun
 * @des
 * @time 2019/3/11 13:52
 * Because had because, so had so, since has become since, why say why。
 **/

public class RetrofitNetwork {

    private Context mContext;

    public RetrofitNetwork(Context context) {
        mContext = context;
    }

    public void testGet(final RetrofitCallback callback) {

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("cid", "60");

        JYRetrofitClient.getInstance(mContext).createBaseApi().get("article/list/0/json", maps, new JYCallBack<WanAndroidInfo>() {
            @Override
            public void onStart() {
                Log.i("JYRetrofitClient", "onStart");
            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
            }

            @Override
            public void onError(Throwable e) {
                Log.i("JYRetrofitClient", "onError:" + e.toString());
                if (callback != null)
                    callback.error(e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("JYRetrofitClient", "onComplete");
            }

            @Override
            public void onNext(WanAndroidInfo wanAndroidInfo) {
                if (callback != null)
                    callback.complete(wanAndroidInfo.toString());
            }
        });
    }

    public void testPost(final RetrofitCallback callback) {

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("id", "2334");


        JYRetrofitClient.getInstance(mContext).createBaseApi().post("lg/uncollect_originId/2333/json", maps, new JYCallBack<WanAndroidInfo>() {
            @Override
            public void onStart() {
                Log.i("JYRetrofitClient", "onStart");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("JYRetrofitClient", "onError:" + e.toString());
                if (callback != null)
                    callback.error(e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("JYRetrofitClient", "onComplete");
            }

            @Override
            public void onNext(WanAndroidInfo wanAndroidInfo) {
                if (callback != null)
                    callback.complete(wanAndroidInfo.toString());
            }
        });
    }

    public void testUpload(final RetrofitCallback callback) {

        File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");

        JYRetrofitClient.getInstance(mContext,"http://192.168.2.246:80/").createBaseApi().uploadFile("upload", file, new JYCallBack() {
            @Override
            public void onError(Throwable e) {
                if (callback != null)
                    callback.error(e.toString());
            }

            @Override
            public void onNext(Object o) {
                if (callback != null)
                    callback.complete(o.toString());
            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("UPLOAD", (int) ((float) contentLength / bytesTotal * 100) + "%");
                if (callback != null)
                    callback.progress((int) ((float) contentLength / bytesTotal * 100));
            }
        });
    }

    public void testUploadFiles(final RetrofitCallback callback) {

        File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory(), "2.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "3.jpg");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file);


        JYRetrofitClient.getInstance(mContext,"http://192.168.2.246:80/").createBaseApi().uploadFiles("upload", files, new JYCallBack() {
            @Override
            public void onError(Throwable e) {
                if (callback != null)
                    callback.error(e.toString());
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("UPLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
                if (callback != null)
                    callback.progress((int) ((float) contentLength / bytesTotal * 100));
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if (callback != null)
                    callback.complete("Complete");
            }
        });
    }

    public void testDownload(final RetrofitCallback callback) {

        String filePath = Environment.getExternalStorageDirectory().getPath();

        String fileName = "123.jpg";

        JYRetrofitClient.getInstance(mContext,"http://192.168.2.246:80/").createBaseApi().download("upload/123.jpg", filePath, fileName, new JYCallBack() {
            @Override
            public void onError(Throwable e) {
                if (callback != null)
                    callback.error(e.toString());
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("DOWNLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
                if (callback != null)
                    callback.progress((int) ((float) contentLength / bytesTotal * 100));
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if (callback != null)
                    callback.complete("Complete");
            }
        });
    }

    public void testWs(final RetrofitCallback callback) {

        TestWs ws = new TestWs("北京");

        ws.getRequest()
                // 处理相应的实体转换
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s;
                    }
                }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                if (callback != null)
                    callback.complete(o.toString());
            }

            @Override
            public void onError(Throwable e) {
                if (callback != null)
                    callback.error(e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void testCustomApi(final RetrofitCallback callback) {

        MyApiService service = JYRetrofitClient.getInstance(mContext).create(MyApiService.class);

        // execute and add observable to RxJava
        JYRetrofitClient.getInstance(mContext).execute(
                service.getData( 0 ), new Observer<WanAndroidInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidInfo info) {
                        if (callback != null)
                            callback.complete(info.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null)
                            callback.error(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
