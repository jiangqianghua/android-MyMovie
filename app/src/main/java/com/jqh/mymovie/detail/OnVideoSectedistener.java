package com.jqh.mymovie.detail;

import com.jqh.mymovie.model.Video;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public interface OnVideoSectedistener {
    void onVideoSelected(Video video,int position);
}
