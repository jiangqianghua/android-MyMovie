package com.jqh.mymovie.detail;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jqh.mymovie.R;
import com.jqh.mymovie.model.Video;
import com.jqh.mymovie.model.VideoList;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class VideoItemAdapter extends BaseAdapter {


    private Context mContext ;
    private int mTotalCount ;
    private OnVideoSectedistener mListener ;

    private boolean isShowTitleContent ;

    private VideoList mVideoList = new VideoList();
    public VideoItemAdapter(Context context, int totalCount, OnVideoSectedistener listener){
        mContext = context ;
        mTotalCount = totalCount ;
        mListener = listener ;
    }

    // 是否显示每集，每期内容，
    public void setIsShowTitleContent(boolean isShow){
        isShowTitleContent = isShow ;
    }

    private boolean getIsShowTitleContent(){
        return isShowTitleContent ;
    }
    @Override
    public int getCount() {
        return mVideoList.size();
    }

    @Override
    public Video getItem(int position) {
        if(mVideoList.size() > 0)
            return mVideoList.get(position);
        return null ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        final Video video = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.videoContainer = (LinearLayout)convertView.findViewById(R.id.ll_video_container);
            viewHolder.videoTitle = (Button)convertView.findViewById(R.id.btn_video_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(getIsShowTitleContent()){
            if(!TextUtils.isEmpty(video.getVideoName())){
                viewHolder.videoTitle.setText(video.getVideoName());
            }
            else
            {
                viewHolder.videoTitle.setText(String.valueOf(position+1));
            }
        }else
        {
            viewHolder.videoTitle.setText(String.valueOf(position+1));
        }
        // 处理首次显示button
//        if(position == 0){
//            mListener.onVideoSelected(video,position);
//        }
        viewHolder.videoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onVideoSelected(video,position);
            }
        });
        return convertView;
    }

    public void addVideo(Video video){
        mVideoList.add(video);
    }

    class ViewHolder{
        LinearLayout videoContainer;
        Button videoTitle ;
    }
}
