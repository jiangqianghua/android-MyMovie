package com.jqh.mymovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jqh.mymovie.base.BaseActivity;
import com.jqh.mymovie.indircatior.CoolIndicatorLayout;
import com.jqh.mymovie.indircatior.IPagerIndicatorView;
import com.jqh.mymovie.indircatior.IPagerTitle;
import com.jqh.mymovie.indircatior.ViewPagerITitleView;
import com.jqh.mymovie.indircatior.ViewPagerIndicatorAdapter;
import com.jqh.mymovie.indircatior.ViewPagerIndicatorLayout;
import com.jqh.mymovie.indircatior.ViewPagerWrapper;
import com.jqh.mymovie.indircatior.ViewPaperIndicatorView;
import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.Site;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2017/12/1.
 */
public class DetailListActivity extends BaseActivity {
    private static final String CHANNEL_ID = "channelid";
    private int channelId ;
    private ViewPager mViewPager ;
    public static final int MAX_SITE = 2 ;
    private String[] mSiteNames = new String[]{"搜狐视频","乐视视频"};
    private List<String> mDataSet = Arrays.asList(mSiteNames);
    @Override
    protected void initView() {
        setSupportActionBar();
        setSupportArrowActionBar(true);
        Intent intent = getIntent();
        if(intent != null){
            channelId = intent.getIntExtra(CHANNEL_ID,0);
        }

        Channel channel = new Channel(channelId,this);
        String titleName = channel.getChannelName();
        setTitle(titleName);
        mViewPager = bindViewId(R.id.pager);
        //指示器
        CoolIndicatorLayout coolIndicatorLayout = bindViewId(R.id.viewpager_indicator);
        // 组配indicator 和 title
        final ViewPagerIndicatorLayout viewPagerIndicatorLayout = new ViewPagerIndicatorLayout(this);
        viewPagerIndicatorLayout.setAdapter(new ViewPagerIndicatorAdapter() {
            @Override
            public int getCount() {
                return mDataSet.size();
            }

            @Override
            public IPagerTitle getTitle(Context context, final int index) {
                final ViewPagerITitleView viewPagerITitleView = new ViewPagerITitleView(context);
                viewPagerITitleView.setText(mDataSet.get(index));
                viewPagerITitleView.setNormalColor(Color.parseColor("#333333"));
                viewPagerITitleView.setSelectedColor(Color.parseColor("#e94220"));
                viewPagerITitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return viewPagerITitleView;
            }

            @Override
            public IPagerIndicatorView getIndicator(Context conext) {
                ViewPaperIndicatorView viewPagerindicatorView = new ViewPaperIndicatorView(conext);
                viewPagerindicatorView.setFillColor(Color.parseColor("#ebe4e3"));
                return viewPagerindicatorView;
            }
        });

        coolIndicatorLayout.setPagerIndicatorLayout(viewPagerIndicatorLayout);
        ViewPagerWrapper.with(coolIndicatorLayout,mViewPager).compose();
        mViewPager.setAdapter(new SitePagerAdapter(getSupportFragmentManager(),this,channelId));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_list;
    }

    @Override
    protected void initData() {

    }

    // 处理左上角返回箭头
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class SitePagerAdapter extends FragmentPagerAdapter{

        private Context mContext ;
        private int mChannleID ;
        private HashMap<Integer,DetailListFragment> mPagerMap ;
        public SitePagerAdapter(FragmentManager fm,Context context,int channelId){
            super(fm);
            mContext = context ;
            mChannleID = channelId ;
            mPagerMap = new HashMap<>();
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if(obj instanceof  DetailListFragment){
                mPagerMap.put(position,(DetailListFragment)obj);
            }
            return obj ;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPagerMap.remove(position);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailListFragment.newInstance(position+1,mChannleID);
        }

        @Override
        public int getCount() {
            return MAX_SITE;
        }
    }

    /**
     * 外部调用，进入到DetailListActivity界面中
     * @param context
     * @param channelId
     */
    public static void LaunchDetailListActivity(Context context,int channelId){
        Intent intent = new Intent(context,DetailListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(CHANNEL_ID,channelId);
        context.startActivity(intent);
    }
}
