package com.jqh.mymovie.api;

import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.VideoList;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public interface OnGetVideoListener {

    void onOnGetVideoSuccess(VideoList videoList);
    void onGetVideoFailed(ErrorInfo info);
}
