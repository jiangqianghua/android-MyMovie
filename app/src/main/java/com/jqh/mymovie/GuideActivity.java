package com.jqh.mymovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{

    private List<View> mViewList ;
    private ViewPager mViewPager ;
    private ImageView[] mDotList ;
    private int mLastPos ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();

        initViewPager();

        initDots();
    }

    private void initView()
    {
        mViewList = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(this);
        mViewList.add(inflater.inflate(R.layout.guide_one_layout,null));
        mViewList.add(inflater.inflate(R.layout.guide_two_layout,null));
        mViewList.add(inflater.inflate(R.layout.guide_three_layout,null));
    }

    private void initViewPager()
    {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPagerAdapter adapter = new MyPagerAdapter(mViewList,this);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 初始化点
     */
    private void initDots()
    {
        LinearLayout dotsLayout = (LinearLayout)findViewById(R.id.ll_dots_layout);
        mDotList = new ImageView[mViewList.size()];
        for(int i = 0 ; i < mViewList.size() ; i++)
        {
            mDotList[i] = (ImageView) dotsLayout.getChildAt(i);
            mDotList[i].setEnabled(false);
        }
        mLastPos = 0 ;
        mDotList[0].setEnabled(true);
    }
    // command + N


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentDotPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setCurrentDotPosition(int pos)
    {
        mDotList[pos].setEnabled(true);
        mDotList[mLastPos].setEnabled(false);
        mLastPos = pos ;
    }

    class MyPagerAdapter extends PagerAdapter{

        private List<View> mImageViewList ;
        private Context mContext ;

        MyPagerAdapter(List<View> list ,Context context)
        {
            mImageViewList = list ;
            mContext = context ;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(mImageViewList != null && mImageViewList.size() > 0) {
                container.addView(mImageViewList.get(position));
                if(position == mImageViewList.size()-1)
                {
                    ImageView imageView  = (ImageView) mImageViewList.get(position).findViewById(R.id.enterhome_iv);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startHomtActivity();
                            setGuided();
                        }
                    });
                }
                return mImageViewList.get(position);
            }
            else
            {
                return null ;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(mImageViewList != null && mImageViewList.size() > 0)
                container.removeView(mImageViewList.get(position));
        }

        @Override
        public int getCount() {
            if(mImageViewList != null)
            {
                return mImageViewList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    /**
     * 设置已经引导页过了
     */
    private void setGuided()
    {
        SharedPreferences mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putBoolean("misFirstIn", false);
        editor.commit();


    }
    /**
     * 进入主页面
     */
    private void startHomtActivity()
    {
        Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
