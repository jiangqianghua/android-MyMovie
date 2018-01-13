package com.jqh.mymovie.api;

import android.content.Context;

import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.Site;

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
}
