package com.qj.frame.activity.thinker;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qj.common.mvp.MVPBaseActivity;
import com.qj.common.toast.ToastCommand;
import com.qj.frame.R;
import com.qj.frame.thinker.model.BaseBuildInfo;
import com.qj.frame.thinker.model.BuildInfo;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author qujun
 * @des com.qj.frame.thinker
 * @time 2019/1/31 10:28
 * Because had because, so had so, since has become since, why say why。
 **/

public class ThinkerActivity extends MVPBaseActivity<ThinkerContract.View, ThinkerPresenter> implements ThinkerContract.View {

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
    @BindView(R.id.btn_load)
    Button btnLoad;
    @BindView(R.id.btn_clean)
    Button btnClean;
    @BindView(R.id.btn_kill)
    Button btnKill;
    @BindView(R.id.btn_info)
    Button btnInfo;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_thinker;
    }

    @Override
    protected Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    protected String getToolBarTitle() {
        return "Thinker 热修复框架";
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

        txtTitle.setText("Thinker");

        StringBuilder str = new StringBuilder();
        str.append("1.参考demo配置gradle" + "\n");
        str.append("2.拷贝thinker相关监听类，并注册补丁监听服务" + "\n");
        str.append("3.赋予读写权限" + "\n");
        str.append("4.通过注解生成 application" + "\n");
        str.append("总结:" + "\n");
        str.append("1.Tinker不支持修改AndroidManifest.xml，Tinker不支持新增四大组件(1.9.0支持新增非export的Activity)； " + "\n");
        str.append("2.由于Google Play的开发者条款限制，不建议在GP渠道动态更新代码；" + "\n");
        str.append("3.在Android N上，补丁对应用启动时间有轻微的影响；" + "\n");
        str.append("4.不支持部分三星android-21机型，加载补丁时会主动抛出”TinkerRuntimeException:checkDexInstall failed”；" + "\n");
        str.append("5.对于资源替换，不支持修改remoteView。例如transition动画，notification icon以及桌面图标。" + "\n");
        str.append("5.小米需程序开启自启动权限。(Mix2测试)" + "\n");
        txtContent.setText(str.toString());
    }

    @Override
    protected void loadView() {
        showToast("123", ToastCommand.INFO);
    }


    @OnClick({R.id.btn_load, R.id.btn_clean, R.id.btn_kill, R.id.btn_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                loadPatch();
                break;
            case R.id.btn_clean:
                cleanPatch();
                break;
            case R.id.btn_kill:
                kill();
                break;
            case R.id.btn_info:
                showInfo(ThinkerActivity.this);
                break;
        }
    }

    @Override
    protected void toolBarNavListener() {
        super.toolBarNavListener();
        leftFinish();
    }

    private void loadPatch() {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
    }

    private void cleanPatch() {
        Tinker.with(getApplicationContext()).cleanPatch();
    }

    private void kill() {
        ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public boolean showInfo(Context context) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(getApplicationContext());
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[patch is loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[patch is not loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(getApplicationContext())));
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", BaseBuildInfo.TEST_MESSAGE));

        final TextView v = new TextView(context);
        v.setText(sb);
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTextColor(0xFF000000);
        v.setTypeface(Typeface.MONOSPACE);
        final int padding = 16;
        v.setPadding(padding, padding, padding, padding);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        return true;
    }
}
