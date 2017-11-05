package com.jqh.mymovie;

import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

/**
 * Created by jiangqianghua on 17/9/23.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected void initView() {
        LoopViewPager viewPager = bindViewId(R.id.loopviewpager);
        CircleIndicator indicator = bindViewId(R.id.indicator);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }
}
