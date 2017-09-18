package com.jqh.mymovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public abstract  class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        initData();
    }


    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();

    protected <T extends View> T binViewId(int resId)
    {
        return (T)findViewById(resId);
    }

    protected void setSupportActionBar()
    {
        mToolBar = binViewId(R.id.toolbar);
        if(mToolBar != null)
        {
            setSupportActionBar(mToolBar);
        }
    }

    protected void setActionBarIcon(int resId)
    {
        if(mToolBar != null)
        {
            mToolBar.setNavigationIcon(resId);
        }
    }
}
