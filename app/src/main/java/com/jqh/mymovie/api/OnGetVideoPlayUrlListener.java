package com.jqh.mymovie.api;

import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Video;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public interface OnGetVideoPlayUrlListener {

    void onGetSuperUrl(Video video,String url);// 超清
    void onGetNormal(Video video,String url);// 标清
    void onGetHighUrl(Video video,String url); // 高清
    void onGetFailed(ErrorInfo errorInfo);// 获取失败
}
