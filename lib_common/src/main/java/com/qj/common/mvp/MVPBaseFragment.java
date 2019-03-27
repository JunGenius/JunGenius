package com.qj.common.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.qj.common.R;
import com.qj.common.toast.ToastCommand;

import java.lang.reflect.ParameterizedType;

import es.dmoral.toasty.Toasty;

public abstract class MVPBaseFragment<V extends BaseView,T extends BasePresenterImpl<V>> extends Fragment implements BaseView{

    public T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= getInstance(this,1);
        mPresenter.attachView((V) this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public  <T> T getInstance(Object o, int i) {
            try {
                return ((Class<T>) ((ParameterizedType) (o.getClass()
                        .getGenericSuperclass())).getActualTypeArguments()[i])
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassCastException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        return null;
        }



    /**
     * 界面跳转
     *
     * @param clazz 目标Activity
     */
    protected void readyGo(Class<?> clazz) {
        readyGo(clazz, null);
    }

    /**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        startActivityForResult(intent, requestCode);
    }


    /**
     * startActivityForResult with bundle
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void left() {
        ((Activity)getContext()).overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    protected void right() {
        ((Activity)getContext()).overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);
    }

    protected void showToast(String msg, ToastCommand command) {

        switch (command) {
            case INFO:
                Toasty.info(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            case ERROR:
                Toasty.error(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCESS:
                Toasty.success(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNING:
                Toasty.warning(getContext(), msg, Toast.LENGTH_SHORT, true).show();
                break;
            default:
                Toasty.normal(getContext(), msg, Toast.LENGTH_SHORT).show();
                break;
        }

    }


}
