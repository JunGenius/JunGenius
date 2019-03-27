package com.qj.frame.activity.retrofit;


import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qj.client.iter.JYCallBack;
import com.qj.client.net.JYRetrofitClient;
import com.qj.common.mvp.MVPBaseActivity;
import com.qj.frame.R;
import com.qj.common.model.WanAndroidInfo;
import com.qj.common.service.MyApiService;
import com.qj.frame.activity.retrofit.webservice.TestWs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * @author qujun
 * @des Android 基于Retrofit2.0+RxJava 网络请求框架
 * @time 2019/2/14 23:38
 * Because had because, so had so, since has become since, why say why。
 **/

public class RetrofitActivity extends MVPBaseActivity<RetrofitContract.View, RetrofitPresenter> implements RetrofitContract.View {


    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_content)
    TextView txtContent;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.btn_post)
    Button btnPost;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.btn_upload_files)
    Button btnUploadFiles;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.btn_custom)
    Button btnCustom;
    @BindView(R.id.btn_webservice)
    Button btnWebservice;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    protected String getToolBarTitle() {
        return "Retrofit + RxJava2 网络请求库";
    }

    @Override
    protected int getToolNavIcon() {
        return R.mipmap.ic_toolbar_back;
    }

    @Override
    protected int getOptionsMenu() {
        return 0;
    }

    @Override
    protected void initViews() {
        super.initViews();

        txtTitle.setText("Retrofit + RXJAVA");

        StringBuilder str = new StringBuilder();
        str.append("1.支持http post get 请求" + "\n");
        str.append("2.支持http 自定义Host请求" + "\n");
        str.append("3.支持http 文件单个或多个上传和下载" + "\n");
        str.append("4.支持http Cache缓存" + "\n");
        str.append("5.webservice请求" + "\n");

        txtContent.setText(str.toString());
    }

    @Override
    protected void loadView() {

    }


    @Override
    protected void toolBarNavListener() {
        super.toolBarNavListener();
        leftFinish();
    }

    @OnClick({R.id.btn_get, R.id.btn_post, R.id.btn_upload, R.id.btn_upload_files, R.id.btn_download, R.id.btn_custom, R.id.btn_webservice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                testGet();
                break;
            case R.id.btn_post:
                testPost();
                break;
            case R.id.btn_upload:
                testUpload();
                break;
            case R.id.btn_upload_files:
                testUploadFiles();
                break;
            case R.id.btn_download:
                testDownload();
                break;
            case R.id.btn_custom:
                testCustomApi();
                break;
            case R.id.btn_webservice:
                testWs();
                break;
        }
    }


    private void testGet() {

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("cid", "60");


        JYRetrofitClient.getInstance(this).createBaseApi().get("article/list/0/json", maps, new JYCallBack<WanAndroidInfo>() {
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
            }

            @Override
            public void onComplete() {
                Log.i("JYRetrofitClient", "onComplete");
            }

            @Override
            public void onNext(WanAndroidInfo wanAndroidInfo) {
                txtInfo.setText(wanAndroidInfo.toString());
            }
        });
    }

    private void testPost() {

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("id", "2334");


        JYRetrofitClient.getInstance(this).createBaseApi().post("lg/uncollect_originId/2333/json", maps, new JYCallBack<WanAndroidInfo>() {
            @Override
            public void onStart() {
                Log.i("JYRetrofitClient", "onStart");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("JYRetrofitClient", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("JYRetrofitClient", "onComplete");
            }

            @Override
            public void onNext(WanAndroidInfo wanAndroidInfo) {
                txtInfo.setText(wanAndroidInfo.toString());
            }
        });
    }

    private void testUpload() {

        File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");

        JYRetrofitClient.getInstance(this,"http://192.168.2.246:80/").createBaseApi().uploadFile("upload", file, new JYCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("UPLOAD", (int) ((float) contentLength / bytesTotal * 100) + "%");
            }
        });
    }

    private void testUploadFiles() {

        File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory(), "2.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "3.jpg");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file);


        JYRetrofitClient.getInstance(this,"http://192.168.2.246:80/").createBaseApi().uploadFiles("upload", files, new JYCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("UPLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
                txtInfo.setText((int) ((float) contentLength / bytesTotal * 100) + "%");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                txtInfo.setText("Complete");
            }
        });
    }

    private void testDownload() {

        String filePath = Environment.getExternalStorageDirectory().getPath();

        String fileName = "123.jpg";

        JYRetrofitClient.getInstance(this,"http://192.168.2.246:80/").createBaseApi().download("upload/123.jpg", filePath, fileName, new JYCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("DOWNLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
                txtInfo.setText((int) ((float) contentLength / bytesTotal * 100) + "%");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                txtInfo.setText("Complete");
            }
        });
    }

    private void testWs() {

        TestWs ws = new TestWs("北京");

        ws.getRequest()
                // 处理相应的实体转换
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Exception {
                        return new Object();
                    }
                }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testCustomApi() {

        MyApiService service = JYRetrofitClient.getInstance(RetrofitActivity.this).create(MyApiService.class);

        // execute and add observable to RxJava
        JYRetrofitClient.getInstance(RetrofitActivity.this).execute(
                service.getData( 0 ), new Observer<WanAndroidInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidInfo info) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
