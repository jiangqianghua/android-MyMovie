package com.jqh.mymovie.api;

import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.AlbumList;
import com.jqh.mymovie.model.ErrorInfo;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public interface OnGetChannelAlbumListener {

    void onOnGetChannelAlbumSuccess(AlbumList albumList);
    void onOnGetChannelAlbumFailed(ErrorInfo info);
}
