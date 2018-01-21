package com.jqh.mymovie.api;

import android.content.Context;

import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.Site;
import com.jqh.mymovie.model.Video;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public class SiteApi {

    public static void onGetChannelAlbums(Context context ,int pageNO, int pageSize , int siteId, int channelId, OnGetChannelAlbumListener listener){
        switch (siteId){
            case Site.LETV:
                new LetvApi().onGetChannelAlbums(new Channel(channelId,context),pageNO,pageSize,listener);
                break;
            case Site.SOHU:
                new SohuApi().onGetChannelAlbums(new Channel(channelId,context),pageNO,pageSize,listener);
                break;
        }
    }

    public static void onGetAlbumDetail(int siteId ,Album album,OnGetAlbumDetailListener listener){
        switch (siteId){
            case Site.LETV:
                new LetvApi().onGetAlbumsDetail(album,listener);
                break;
            case Site.SOHU:
                new SohuApi().onGetAlbumsDetail(album,listener);
                break;
        }
    }

    public static  void onGetVideo(int siteId ,Album album ,int pageNo,int pageSize, OnGetVideoListener listener){
        switch (siteId){
            case Site.LETV:
                new LetvApi().onGetVideo(album,pageNo,pageSize,listener);
                break;
            case Site.SOHU:
                new SohuApi().onGetVideo(album,pageNo,pageSize,listener);
                break;
        }
    }

    public static  void onGetVideoPlayUrl(int siteId , Video video, OnGetVideoPlayUrlListener listener){
        switch (siteId){
            case Site.LETV:
                new LetvApi().onGetVideoUrl(video,listener);
                break;
            case Site.SOHU:
                new SohuApi().onGetVideoUrl(video,listener);
                break;
        }
    }
}
