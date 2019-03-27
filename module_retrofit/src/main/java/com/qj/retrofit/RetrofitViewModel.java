package com.qj.retrofit;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qj.common.mvvm.MvvmBaseViewModel;
import com.qj.common.mvvm.binding.command.BindingAction;
import com.qj.common.mvvm.binding.command.BindingCommand;
import com.qj.common.mvvm.data.SingleMutableLiveEvent;

/**
 * @author qujun
 * @des
 * @time 2019/3/8 09:54
 * Because had because, so had so, since has become since, why say why。
 **/

public class RetrofitViewModel extends MvvmBaseViewModel {

    private SingleMutableLiveEvent<RetrofitModel> mData = new SingleMutableLiveEvent<>();

    private RetrofitModel model = null;

    private RetrofitNetwork retrofitNetwork = null;

    public RetrofitViewModel(@NonNull Application application) {
        super(application);
        model = new RetrofitModel();
        retrofitNetwork = new RetrofitNetwork(application);
        init();
    }

    private void init() {
        StringBuilder str = new StringBuilder();
        str.append("1.支持http post get 请求" + "\n");
        str.append("2.支持http 自定义Host请求" + "\n");
        str.append("3.支持http 文件单个或多个上传和下载" + "\n");
        str.append("4.支持http Cache缓存" + "\n");
        str.append("5.webservice请求" + "\n");
        model.setContent(str.toString());
        mData.setValue(model);
    }

    public SingleMutableLiveEvent<RetrofitModel> getData() {
        return mData;
    }


    public final BindingCommand getClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testGet(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {

                }
            });
        }
    });

    public final BindingCommand postClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testPost(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {

                }
            });
        }
    });

    public final BindingCommand uploadClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testUpload(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {

                }
            });
        }
    });

    public final BindingCommand uploadsClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testUploadFiles(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {

                }
            });
        }
    });

    public final BindingCommand downloadClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testDownload(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {

                }
            });
        }
    });

    public final BindingCommand customClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testCustomApi(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {

                }
            });
        }
    });

    public final BindingCommand wsClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            retrofitNetwork.testWs(new RetrofitCallback() {
                @Override
                public void progress(int progress) {

                }

                @Override
                public void complete(String info) {
                    model.setInfo(info);
                    mData.setValue(model);
                }

                @Override
                public void error(String info) {
                    model.setInfo(info);
                }
            });
        }
    });

}
