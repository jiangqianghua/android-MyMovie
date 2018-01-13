package com.jqh.mymovie.model;

import android.content.Context;

import com.jqh.mymovie.R;

import java.io.Serializable;

/**
 * Created by user on 2017/11/30.
 */
public class Channel implements Serializable {

    public static final int SHOW = 1 ;
    public static final int MOVIE = 2 ;
    public static final int COMIC = 3;
    public static final int DOCUMENTRY = 4 ;
    public static final int MUSIC = 5 ;
    public static final int VARIETY = 6;
    public static final int LIVE = 7 ;
    public static final int FAVORITE = 8 ;
    public static final int HISTORY = 9 ;
    public static final int CHANNEL_COUNT = 9 ;


    private int channelId ;
    private String channelName ;
    private int resId ;
    private Context mContext ;

    public Channel(int id, Context context)
    {
        mContext = context ;
        channelId = id ;
        switch (channelId){
            case SHOW:
                channelName = mContext.getResources().getString(R.string.channel_series);
                resId = R.drawable.ic_show;
                break;
            case MOVIE:
                channelName = mContext.getResources().getString(R.string.channel_movie);
                resId = R.drawable.ic_movie;
                break;
            case COMIC:
                channelName = mContext.getResources().getString(R.string.channel_comic);
                resId = R.drawable.ic_comic;
                break;
            case DOCUMENTRY:
                channelName = mContext.getResources().getString(R.string.channel_documentary);
                resId = R.drawable.ic_documentary;
                break;
            case MUSIC:
                channelName = mContext.getResources().getString(R.string.channel_music);
                resId = R.drawable.ic_music;
                break;
            case VARIETY:
                channelName = mContext.getResources().getString(R.string.channel_variety);
                resId = R.drawable.ic_variety;
                break;
            case LIVE:
                channelName = mContext.getResources().getString(R.string.channel_live);
                resId = R.drawable.ic_live;
                break;
            case FAVORITE:
                channelName = mContext.getResources().getString(R.string.channel_favorite);
                resId = R.drawable.ic_bookmark;
                break;
            case HISTORY:
                channelName = mContext.getResources().getString(R.string.channel_history);
                resId = R.drawable.ic_history;
                break;
        }
    }
    public static int getSHOW() {
        return SHOW;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getImageResId()
    {
        return resId ;
    }
}
