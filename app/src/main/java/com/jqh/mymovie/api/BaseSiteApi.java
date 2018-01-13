package com.jqh.mymovie.api;

import com.jqh.mymovie.model.Channel;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public  abstract class BaseSiteApi {
    public abstract void onGetChannelAlbums(Channel channel,int pageNo,int pageSize,OnGetChannelAlbumListener listener);
}
