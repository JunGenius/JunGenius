package com.qj.frame.activity.home.main.bean;

import android.support.annotation.DrawableRes;

import com.qj.frame.R;

/**
 * @author qujun
 * @des
 * @time 2019/1/31 10:05
 * Because had because, so had so, since has become since, why say why。
 **/

public class MainMenuInfo {

    private String title = "";

    private String content = "";

    private String address = "";

    private Class cls = null;

    private String arouterPath = "";

    private @DrawableRes int icon = R.mipmap.ic_menu_star;

    public MainMenuInfo(String title, String content, String address ,Class cls , @DrawableRes int icon) {
        this(title , content , address , cls , "" , icon);
    }

    public MainMenuInfo(String title, String content, String address ,String arouterPath , @DrawableRes int icon) {
        this(title , content , address , null , arouterPath , icon);
    }

    public MainMenuInfo(String title, String content, String address,Class cls ,String arouterPath , @DrawableRes int icon) {
        this.title = title;
        this.content = content;
        this.address = address;
        this.arouterPath = arouterPath;
        this.cls = cls;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }


    public String getArouterPath() {
        return arouterPath;
    }

    public void setArouterPath(String arouterPath) {
        this.arouterPath = arouterPath;
    }
}
