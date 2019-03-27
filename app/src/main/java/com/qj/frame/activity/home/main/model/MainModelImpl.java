package com.qj.frame.activity.home.main.model;

import com.qj.common.arouter.ARouterPath;
import com.qj.frame.R;
import com.qj.frame.activity.home.main.bean.MainMenuInfo;
import com.qj.frame.activity.retrofit.RetrofitActivity;
import com.qj.frame.activity.thinker.ThinkerActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qujun
 * @des
 * @time 2019/1/31 10:01
 * Because had because, so had so, since has become since, why say why。
 **/

public class MainModelImpl {

    private List<MainMenuInfo> mMainMenuInfos = null;

    public List<MainMenuInfo> getMainMenuInfos() {

        if (mMainMenuInfos == null)
            mMainMenuInfos = new ArrayList<>();

        mMainMenuInfos.add(
                new MainMenuInfo("Thinker 热修复",
                        "Tinker是微信官方的Android热补丁解决方案，它支持动态下发代码、So库以及资源，让应用能够在不需要重新安装的情况下实现更新。当然，你也可以使用Tinker来更新你的插件。",
                        "https://github.com/Tencent/tinker",
                        ThinkerActivity.class,
                        R.mipmap.ic_menu_star));

        mMainMenuInfos.add(
                new MainMenuInfo("Retrofit2.0+RxJava ",
                        "Retrofit 是一个 RESTful 的 HTTP 网络请求框架的封装。" + "\n"
                                + "1.网络请求的工作本质上是 OkHttp 完成，而 Retrofit 仅负责 网络请求接口的封装" + "\n"
                                + "2.App应用程序通过 Retrofit 请求网络，实际上是使用 Retrofit 接口层封装请求参数、Header、Url 等信息，之后由 OkHttp 完成后续的请求操作" + "\n"
                                + "3.在服务端返回数据之后，OkHttp 将原始的结果交给 Retrofit，Retrofit根据用户的需求对结果进行解析",
                        "https://github.com/square/retrofit",
                        ARouterPath.RetrofitAty,
                        R.mipmap.ic_menu_star));

        mMainMenuInfos.add(
                new MainMenuInfo("MVVM",
                        "Retrofit 是一个 RESTful 的 HTTP 网络请求框架的封装。" + "\n"
                                + "1.网络请求的工作本质上是 OkHttp 完成，而 Retrofit 仅负责 网络请求接口的封装" + "\n"
                                + "2.App应用程序通过 Retrofit 请求网络，实际上是使用 Retrofit 接口层封装请求参数、Header、Url 等信息，之后由 OkHttp 完成后续的请求操作" + "\n"
                                + "3.在服务端返回数据之后，OkHttp 将原始的结果交给 Retrofit，Retrofit根据用户的需求对结果进行解析",
                        "https://github.com/square/retrofit",
                        ARouterPath.MvvmListAty,
                        R.mipmap.ic_menu_star));

        return mMainMenuInfos;
    }


    public MainMenuInfo getMainMenuInfoByPos(int pos) {
        return mMainMenuInfos.get(pos);
    }
}
