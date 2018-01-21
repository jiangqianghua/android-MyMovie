package com.jqh.mymovie.api;

import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.AlbumList;
import com.jqh.mymovie.model.ErrorInfo;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public interface OnGetAlbumDetailListener {

    void onOnGetAlbumDetailSuccess(Album album);
    void onOnGetAlbumDetailFailed(ErrorInfo info);
}
