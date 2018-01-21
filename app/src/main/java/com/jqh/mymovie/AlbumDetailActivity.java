package com.jqh.mymovie;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jqh.mymovie.api.OnGetAlbumDetailListener;
import com.jqh.mymovie.api.OnGetVideoPlayUrlListener;
import com.jqh.mymovie.api.SiteApi;
import com.jqh.mymovie.base.BaseActivity;
import com.jqh.mymovie.detail.AlbumPlayGridFragment;
import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Video;
import com.jqh.mymovie.utils.ImageUtils;

public class AlbumDetailActivity extends BaseActivity {

    private String TAG = "AlbumDetailActivity";
    private Album mAlbum ;
    private int mVideoNo ;
    private boolean mIsShowDesc;

    private ImageView mAlbumImg ;
    private TextView mAlbumName ;
    private TextView mDirector ;
    private TextView mMainActor ;
    private TextView mAlbumDesc ;

    private boolean mIsFavor ;
    private AlbumPlayGridFragment mFragment ;

    private Button mSuperBitstreamButton;
    private Button mNormalBitstreamButton;
    private Button mHighBitstreamButton;

    private int mCurrentVideoPosition ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void initView() {
        mAlbum = getIntent().getParcelableExtra("album");
        mVideoNo = getIntent().getIntExtra("videoNo",0);
        mIsShowDesc = getIntent().getBooleanExtra("isShowDesc",false);
        setSupportActionBar();
        setSupportArrowActionBar(true);
        setTitle(mAlbum.getTitle());

        mAlbumImg = bindViewId(R.id.iv_album_image);
        mAlbumName = bindViewId(R.id.tv_ablum_name);
        mDirector = bindViewId(R.id.tv_album_director);
        mMainActor = bindViewId(R.id.tv_album_mainactor);
        mAlbumDesc = bindViewId(R.id.tv_album_desc);

        mSuperBitstreamButton = bindViewId(R.id.bt_super);
        mSuperBitstreamButton.setOnClickListener(mOnSuperClickListener);
        mNormalBitstreamButton = bindViewId(R.id.bt_normal);
        mNormalBitstreamButton.setOnClickListener(mOnNormalClickListener);
        mHighBitstreamButton = bindViewId(R.id.bt_high);
        mHighBitstreamButton.setOnClickListener(mOnHighClickListener);
    }

    @Override
    protected void initData() {
        mAlbumName.setText(mAlbum.getTitle());
        if(!TextUtils.isEmpty(mAlbum.getDirector())) {
            mDirector.setText(getResources().getString(R.string.director) + mAlbum.getDirector());
            mDirector.setVisibility(View.VISIBLE);
        }else{
            mDirector.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mAlbum.getMainActor())) {
            mMainActor.setText(getResources().getString(R.string.director) + mAlbum.getMainActor());
            mMainActor.setVisibility(View.VISIBLE);
        }else{
            mMainActor.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mAlbum.getAlbumDesc())) {
            mAlbumDesc.setText(mAlbum.getAlbumDesc());
            mAlbumDesc.setVisibility(View.VISIBLE);
        }else{
            mAlbumDesc.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mAlbum.getHorImageUrl()))
            ImageUtils.disPlayImage(mAlbumImg,mAlbum.getHorImageUrl() );
        if(!TextUtils.isEmpty(mAlbum.getVerImageUrl()))
            ImageUtils.disPlayImage(mAlbumImg,mAlbum.getVerImageUrl());

        SiteApi.onGetAlbumDetail(1, mAlbum, new OnGetAlbumDetailListener() {
            @Override
            public void onOnGetAlbumDetailSuccess(final Album album) {
              //  Log.d(TAG,"onGetAlbumDetail >>" + album.getVideoTota() );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFragment =  AlbumPlayGridFragment.newInstance(album,mIsShowDesc,0);
                        mFragment.setOnPlayVideoSelectedListener(mOnPlayVideoSelectedListener);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container,mFragment);
                        ft.commit();
                        // 延迟构造
                        getSupportFragmentManager().executePendingTransactions();
                    }
                });

            }

            @Override
            public void onOnGetAlbumDetailFailed(ErrorInfo info) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // 点击返回按钮
                finish();
                return true;
            case R.id.action_favor_item:
                if(mIsFavor){
                    mIsFavor = false ;
                    invalidateOptionsMenu();
                }
                return true;
            case R.id.action_unfavor_item:
                if(!mIsFavor){
                    mIsFavor = true ;
                    invalidateOptionsMenu();
                }
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 添加左边的工具栏目
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_detail_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favitem =menu.findItem(R.id.action_favor_item);
        MenuItem unfavitem = menu.findItem(R.id.action_unfavor_item);
        favitem.setVisible(mIsFavor);
        unfavitem.setVisible(!mIsFavor);
        return super.onPrepareOptionsMenu(menu);
    }

    public static void launch(Activity activity , Album album, int videoNo, boolean isShowDesc){
        Intent intent = new Intent(activity,AlbumDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("album",album);
        intent.putExtra("videoNo",videoNo);
        intent.putExtra("isShowDesc",isShowDesc);
        activity.startActivity(intent);

    }

    public static void launch(Activity activity , Album album){
        Intent intent = new Intent(activity,AlbumDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("album",album);
        activity.startActivity(intent);
    }

    private AlbumPlayGridFragment.OnPlayVideoSelectedListener mOnPlayVideoSelectedListener = new AlbumPlayGridFragment.OnPlayVideoSelectedListener() {
        @Override
        public void OnPlayVideoSelected(Video video, int position) {
            // 获取播放相关信息
            mCurrentVideoPosition = position ;
            SiteApi.onGetVideoPlayUrl(mAlbum.getSite().getSiteId(),video,mOnGetVideoPlayUrlListener);
        }
    };

    public class StreamType{
        public static final int SUPER = 1 ;
        public static final int NORMAL = 2 ;
        public static final int HIGH = 3 ;
    }
    private OnGetVideoPlayUrlListener mOnGetVideoPlayUrlListener = new OnGetVideoPlayUrlListener() {
        @Override
        public void onGetSuperUrl(final Video video, final String url) {
            Log.d(TAG,"super url="+url +" video="+video.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSuperBitstreamButton.setVisibility(View.VISIBLE);
                    mSuperBitstreamButton.setTag(R.id.key_video_url,url);
                    mSuperBitstreamButton.setTag(R.id.key_video,video);
                    mSuperBitstreamButton.setTag(R.id.key_current_video_number,mCurrentVideoPosition);
                    mSuperBitstreamButton.setTag(R.id.key_video_stream,StreamType.SUPER);

                }
            });


        }

        @Override
        public void onGetNormal(final Video video,final String url) {
            Log.d(TAG,"normal url="+url +" video="+video.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNormalBitstreamButton.setVisibility(View.VISIBLE);
                    mNormalBitstreamButton.setTag(R.id.key_video_url,url);
                    mNormalBitstreamButton.setTag(R.id.key_video,video);
                    mNormalBitstreamButton.setTag(R.id.key_current_video_number,mCurrentVideoPosition);
                    mNormalBitstreamButton.setTag(R.id.key_video_stream,StreamType.NORMAL);
                }
            });

        }

        @Override
        public void onGetHighUrl(final Video video, final String url) {
            Log.d(TAG,"high url="+url +" video="+video.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mHighBitstreamButton.setVisibility(View.VISIBLE);
                    mHighBitstreamButton.setTag(R.id.key_video_url,url);
                    mHighBitstreamButton.setTag(R.id.key_video,video);
                    mHighBitstreamButton.setTag(R.id.key_current_video_number,mCurrentVideoPosition);
                    mHighBitstreamButton.setTag(R.id.key_video_stream,StreamType.HIGH);
                }
            });
        }

        @Override
        public void onGetFailed(ErrorInfo errorInfo) {
            Log.d(TAG,"onGetFailed :" + errorInfo);
            hideAllButton();
        }
    };

    private void hideAllButton(){
        mSuperBitstreamButton.setVisibility(View.GONE);
        mNormalBitstreamButton.setVisibility(View.GONE);
        mHighBitstreamButton.setVisibility(View.GONE);
    }

    private View.OnClickListener mOnSuperClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handleButtonClick(v);

        }
    };

    private View.OnClickListener mOnNormalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handleButtonClick(v);
        }
    };

    private View.OnClickListener mOnHighClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handleButtonClick(v);
        }
    };

    private void handleButtonClick(View v){
        Button button = (Button)v ;
        String url = (String)button.getTag(R.id.key_video_url);
        int type = (int)button.getTag(R.id.key_video_stream);
        Video video = (Video)button.getTag(R.id.key_video);
        int currpos = (int)button.getTag(R.id.key_current_video_number);
        if(AppManager.isNetWorkAvailable() && AppManager.isNetWorkWifiAvailable()){
            Intent intent = new Intent(AlbumDetailActivity.this,PlayActivity.class);
            intent.putExtra("url",url);
            intent.putExtra("type",type);
            intent.putExtra("currentPosition",currpos);
            intent.putExtra("video",video);
            startActivity(intent);
        }else{
            // TODO
        }
    }
}
