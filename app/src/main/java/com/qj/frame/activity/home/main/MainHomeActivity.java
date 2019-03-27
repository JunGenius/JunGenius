package com.qj.frame.activity.home.main;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.qj.common.mvp.MVPBaseActivity;
import com.qj.common.view.NestedListView.NestedListView;
import com.qj.frame.R;
import com.qj.frame.activity.home.main.adapter.MainAdapter;
import com.qj.frame.activity.home.main.bean.MainMenuInfo;
import com.qj.frame.activity.home.model.LeftMenuComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainHomeActivity extends MVPBaseActivity<MainHomeContract.View, MainHomePresenter> implements MainHomeContract.View {

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.lv_main)
    NestedListView lvMain;


    private LeftMenuComponent leftMenuComponent;

    private MainAdapter mAdapter = null;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    protected String getToolBarTitle() {
        return "开源框架集锦";
    }

    @Override
    protected int getToolNavIcon() {
        return 0;
    }

    @Override
    protected int getOptionsMenu() {
        return 0;
    }

    @Override
    protected void setStatusBar() {

    }

    @Override
    protected void loadView() {
        mPresenter.loadData();
    }

    @Override
    protected void initViews() {
        super.initViews();

        leftMenuComponent = new LeftMenuComponent(this, savedInstanceState, toolbar);

        collapsingToolbar.setTitle(getString(R.string.drawer_item_collapsing_toolbar_drawer));

        floatingActionButton.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_favorite).actionBar().color(Color.WHITE));


        mAdapter = new MainAdapter(this, new ArrayList<MainMenuInfo>(), R.layout.listview_main_item);

        lvMain.setAdapter(mAdapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toView(position);
            }
        });
    }

    private void toView(int pos) {

        if (pos < 1)
            readyGo(mPresenter.getMainMenuInfo(pos).getCls());
        else
            readyGoArouter(mPresenter.getMainMenuInfo(pos).getArouterPath());
        right();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = leftMenuComponent.getDrawerBuilder().saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = leftMenuComponent.getAccountHeader().saveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }


    @Override
    protected void toolBarNavListener() {
        super.toolBarNavListener();

        if (leftMenuComponent.getDrawerBuilder().isDrawerOpen()) {
            leftMenuComponent.getDrawerBuilder().closeDrawer();
            return;
        }

        leftMenuComponent.getDrawerBuilder().openDrawer();

    }

    @Override
    public void setAdapter(List<MainMenuInfo> data) {
        mAdapter.updateData(data);
        mAdapter.notifyDataSetChanged();
    }

}
