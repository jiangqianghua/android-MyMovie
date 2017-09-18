package com.jqh.mymovie;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout ;
    private NavigationView mNavigationView ;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

        setSupportActionBar();
        setActionBarIcon(R.drawable.ic_drawer_home);
        setTitle("首页");

        mDrawerLayout = bindViewId(R.id.draw_layout);
        mNavigationView = bindViewId(R.id.navigation_view);
        // 绑定toobar 和 drawlayout的关系
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.drawer_open,R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    protected void initData() {

    }
}
