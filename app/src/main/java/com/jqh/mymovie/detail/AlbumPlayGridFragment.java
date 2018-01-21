package com.jqh.mymovie.detail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.jqh.mymovie.R;
import com.jqh.mymovie.api.OnGetVideoListener;
import com.jqh.mymovie.api.SiteApi;
import com.jqh.mymovie.base.BaseFragment;
import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Video;
import com.jqh.mymovie.model.VideoList;
import com.jqh.mymovie.widget.CustomGridView;

/**
 * Created by jiangqianghua on 18/1/20.
 */

public class AlbumPlayGridFragment extends BaseFragment {

    private String TAG = AlbumPlayGridFragment.class.getSimpleName();
    private Album mAlbum ;
    private boolean mIsShowDesc ;
    private int mInitVideoPosition;
    private int mPageNo ;
    private int mPageSize ;

    private VideoItemAdapter mVideoItemAdapter;

    private CustomGridView mCustomGridView ;

    private int mPageTotal ;
    private View mEmptyView ;
    private boolean isFirstSelection = true ;
    private int mCurrentPosition ;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OnPlayVideoSelectedListener mOnPlayVideoSelectedListener ;

    public void setOnPlayVideoSelectedListener(OnPlayVideoSelectedListener mOnPlayVideoSelectedListener) {
        this.mOnPlayVideoSelectedListener = mOnPlayVideoSelectedListener;
    }
    // 视频被选中回调
    public interface OnPlayVideoSelectedListener{
        void OnPlayVideoSelected(Video video ,int position);
    }

    public AlbumPlayGridFragment() {
    }

    public static AlbumPlayGridFragment newInstance(Album album, boolean isShowDesc,int initVideoPosition){
        AlbumPlayGridFragment fragment = new AlbumPlayGridFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("album",album);
        bundle.putBoolean("isShowDesc",isShowDesc);
        bundle.putInt("initVideoPosition",initVideoPosition);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mAlbum = getArguments().getParcelable("album");
            mIsShowDesc = getArguments().getBoolean("isShowDesc");
            mInitVideoPosition = getArguments().getInt("initVideoPosition");
            mCurrentPosition = mInitVideoPosition ;
            mPageNo= 0 ;
            mPageSize = 50 ;
            mVideoItemAdapter = new VideoItemAdapter(getActivity(),mAlbum.getVideoTota(),mVideoSelectedListener);
            mVideoItemAdapter.setIsShowTitleContent(mIsShowDesc);
            mPageTotal = (mAlbum.getVideoTota() + mPageSize - 1)/mPageSize;
            loadData();
        }
    }

    private  OnVideoSectedistener mVideoSelectedListener = new OnVideoSectedistener() {
        @Override
        public void onVideoSelected(Video video, int position) {
            if(mCustomGridView != null){
                mCustomGridView.setSelection(position);
                mCustomGridView.setItemChecked(position,true);
                mCurrentPosition = position ;
                if(mOnPlayVideoSelectedListener != null)
                    mOnPlayVideoSelectedListener.OnPlayVideoSelected(video,position);
            }
        }
    };

    private void loadData(){
        mPageNo++;
        SiteApi.onGetVideo(1, mAlbum, mPageNo,mPageSize, new OnGetVideoListener() {
            @Override
            public void onOnGetVideoSuccess(VideoList videoList) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mEmptyView.setVisibility(View.GONE);
//                    }
//                });
                for(Video v:videoList){
                   // Log.d(TAG," video >> "+ v.toString());
                    mVideoItemAdapter.addVideo(v);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //mCustomGridView.notifyChanged();
                        if(mVideoItemAdapter != null)
                            mVideoItemAdapter.notifyDataSetChanged();
                        if(mVideoItemAdapter.getCount() > mInitVideoPosition
                                && isFirstSelection){
                            mCustomGridView.setSelection(mInitVideoPosition);
                            mCustomGridView.setItemChecked(mInitVideoPosition,true);
                            isFirstSelection = false ;
                            mCustomGridView.smoothScrollToPosition(mInitVideoPosition);
                        }
                    }
                });

            }

            @Override
            public void onGetVideoFailed(ErrorInfo info) {

            }
        });
    }

    @Override
    protected void initView() {
//        mEmptyView = bindViewId(R.id.tv_empty);
//        mEmptyView.setVisibility(View.VISIBLE);
        mCustomGridView =  bindViewId(R.id.gv_video_layout);
        // 综艺界面显示一列，电视剧显示6列
        mCustomGridView.setNumColumns(!mIsShowDesc?6:1);
        mCustomGridView.setAdapter(mVideoItemAdapter);
        if(mAlbum.getVideoTota() > 0 && mAlbum.getVideoTota() > mPageSize){
            mCustomGridView.setHasMoreItem(true);
        }else{
            mCustomGridView.setHasMoreItem(false);
        }

        mCustomGridView.setOnLoadMoreListener(new CustomGridView.OnLoadMoreListener() {
            @Override
            public void onLoadMoreItems() {
                loadData();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album_desc;
    }

    @Override
    protected void initData() {

    }
}
