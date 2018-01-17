package com.jqh.mymovie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jqh.mymovie.api.OnGetChannelAlbumListener;
import com.jqh.mymovie.api.SiteApi;
import com.jqh.mymovie.base.BaseFragment;
import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.AlbumList;
import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Site;
import com.jqh.mymovie.utils.ImageUtils;
import com.jqh.mymovie.utils.StrUtils;
import com.jqh.mymovie.widget.PullLoadRecyclerView;

import java.util.List;

/**
 * Created by user on 2017/12/6.
 */
public class DetailListFragment extends BaseFragment {

    private static final String TAG = DetailListFragment.class.getSimpleName();
    private  int mSiteId;
    private  int mchannelId ;
    private static final String CHANNEL_ID = "channelid";
    private static final String SITE_ID = "siteid";
    private TextView mEmptyView ;
    private PullLoadRecyclerView mPullLoadRecyclerView;
    private int mColumns ;
    private DetailListAdapter mDetailListAdapter ;
    private Handler mHandler = new Handler(Looper.getMainLooper());// 在主线程
    public static final int REFRESH_DURATION = 1500;//刷新时长，毫秒
    public static final int LOADMORE_DURATION = 3000;//刷新时长，毫秒

    private int pageNo = 0;
    private int pageSize = 30 ;
//    public DetailListFragment(int siteId , int channld){
//        mSiteId = siteId ;
//        mchannelId = channld ;
//    }

    public DetailListFragment(){

    }

    public static Fragment newInstance(int siteId, int channld){
        DetailListFragment fragment = new DetailListFragment();
//        mSiteId = siteId ;
//        mchannelId = channld ;
        Bundle bundle = new Bundle();
        bundle.putInt(CHANNEL_ID,channld);
        bundle.putInt(SITE_ID,siteId); // 如 通道是电视剧，站点是乐视
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            mSiteId = getArguments().getInt(SITE_ID);
            mchannelId = getArguments().getInt(CHANNEL_ID);
        }
        pageNo = 0 ;
        mDetailListAdapter = new DetailListAdapter(getActivity(),new Channel(mchannelId,getActivity()));
        if(mSiteId == Site.LETV)
        {
            //乐视频道下显示两列
            mColumns = 2 ;
            mDetailListAdapter.setColums(mColumns);
        }
        else
        {
            mColumns = 3;
            mDetailListAdapter.setColums(mColumns);
        }

        loadMoreData();
    }

    @Override
    protected void initView() {
        mEmptyView = bindViewId(R.id.tv_empty);
        mEmptyView.setText(getActivity().getResources().getString(R.string.load_more_text));
        mPullLoadRecyclerView = bindViewId(R.id.pullLoadRecyclerView);
        mPullLoadRecyclerView.setGridLayout(3);
        mPullLoadRecyclerView.setAdapter(mDetailListAdapter);
        mPullLoadRecyclerView.setOnPullLoadMoreListener(new PullLoadRecyclerListener());
    }

    private void reRreshData(){
        // 请求接口刷新数据
        //TODO
    }

    private void loadMoreData(){
        pageNo++;
        // 加载更多数据
        SiteApi.onGetChannelAlbums(getActivity(), pageNo, pageSize, mSiteId, mchannelId, new OnGetChannelAlbumListener() {
            @Override
            public void onOnGetChannelAlbumSuccess(AlbumList albumList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 主线程执行
                        mEmptyView.setVisibility(View.GONE);
                    }
                });
                for(Album album:albumList)
                {
                    //Log.d(TAG,">> album "+ album.toString());
                    mDetailListAdapter.setData(album);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDetailListAdapter.notifyDataSetChanged();
                    }
                });
                // 当前是子线程，数据抛给主线程

            }

            @Override
            public void onOnGetChannelAlbumFailed(ErrorInfo info) {
                Log.d(TAG,info.toString());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEmptyView.setText(getActivity().getResources().getString(R.string.data_failed_tip));
                    }
                });
            }
        });
    }
    public class PullLoadRecyclerListener implements PullLoadRecyclerView.OnPullLoadMoreListener
    {
        @Override
        public void reRresh() {
            //刷新数据
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reRreshData();
                    mPullLoadRecyclerView.setRefreshCompleted();
                }
            },REFRESH_DURATION);

        }

        @Override
        public void loadMore() {
            // 加载数据
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMoreData();
                    mPullLoadRecyclerView.setLoadMoreCompleted();
                }
            },LOADMORE_DURATION);
        }
    }

    class DetailListAdapter extends RecyclerView.Adapter{

        private Context mContext ;
        private Channel mChannel ;
        private AlbumList albumList = new AlbumList() ;
        private int columns ;

        public DetailListAdapter(Context context, Channel channel)
        {
            mContext = context ;
            mChannel = channel ;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.detaillist_item,null);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            view.setTag(itemViewHolder);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(albumList.size() == 0)
                return ;
            Album album =  getItem(position);
            if(holder instanceof ItemViewHolder){
                ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
                itemViewHolder.albumName.setText(StrUtils.getStrByRange(album.getTitle(),4));
                if(album.getTip().isEmpty())
                    itemViewHolder.albumTip.setVisibility(View.GONE);
                else
                    itemViewHolder.albumTip.setText(album.getTip());
                //itemViewHolder.albumPoster.setB
                Point point = ImageUtils.getVerPostSize(mContext,columns);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(point.x,point.y);
                itemViewHolder.albumPoster.setLayoutParams(params);
                ImageUtils.disPlayImage(itemViewHolder.albumPoster,album.getHorImageUrl(),itemViewHolder.albumPoster.getWidth(),itemViewHolder.albumPoster.getHeight());
                if(album.getVerImageUrl() != null){
                    ImageUtils.disPlayImage(itemViewHolder.albumPoster,album.getVerImageUrl(),point.x,point.y);
                }
                else
                {
                    //默认图片
                }
            }
        }

        private  Album getItem(int position)
        {
            return albumList.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public int getItemCount() {
            if(albumList.size()>0)
                return albumList.size();
            return 0;
        }

        public void setColums(int columns)
        {
            this.columns = columns ;
        }

        public void setData(Album album)
        {
            albumList.add(album);
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder{
            private LinearLayout resultContainer;
            private ImageView albumPoster;
            private TextView albumName ;
            private TextView albumTip ;
            public ItemViewHolder(View view)
            {
                super(view);
                albumName = (TextView) view.findViewById(R.id.tv_album_name);
                albumTip = (TextView) view.findViewById(R.id.tv_album_tip);
                albumPoster = (ImageView)view.findViewById(R.id.iv_album_poster);
                resultContainer = (LinearLayout)view.findViewById(R.id.ablum_container);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detaillist;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected <T extends View> T bindViewId(int resId) {
        return super.bindViewId(resId);
    }
}
