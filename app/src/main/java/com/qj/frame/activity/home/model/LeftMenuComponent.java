package com.qj.frame.activity.home.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.octicons_typeface_library.Octicons;
import com.qj.frame.R;

/**
 * @author qujun
 * @des 左侧菜单信息
 * @time 2019/1/30 10:11
 * Because had because, so had so, since has become since, why say why。
 **/

public class LeftMenuComponent {

    private AccountHeader headerResult;

    private Drawer result;

    private Context context;

    private Bundle savedInstanceState;

    private Toolbar toolbar;

    private OnCheckedChangeListener onCheckedChangeListener;

    public LeftMenuComponent(Context context, Bundle savedInstanceState, Toolbar toolbar) {
        this.context = context;
        this.savedInstanceState = savedInstanceState;
        this.toolbar = toolbar;

        initHead();

        initDrawBuild();
    }


    private void initHead() {

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.mipmap.ic_left_menu_profile).withNameShown(false);

        headerResult =
                new AccountHeaderBuilder()
                        .withActivity((Activity) context)
                        .withSelectionListEnabled(false)
                        .withCompactStyle(true)
                        .addProfiles(profile)
                        .withHeaderBackground(R.mipmap.ic_left_menu_head)
                        .withSavedInstance(savedInstanceState)
                        .build();
    }

    private void initDrawBuild() {

        result = new DrawerBuilder()
                .withActivity((Activity) context)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withFullscreen(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("mode").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn)
                )
                .withSavedInstance(savedInstanceState)
                .build();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public Drawer getDrawerBuilder() {
        return result;
    }

    public AccountHeader getAccountHeader(){
        return headerResult;
    }
}
