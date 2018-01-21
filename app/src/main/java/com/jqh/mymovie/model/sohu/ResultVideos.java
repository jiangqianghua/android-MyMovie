package com.jqh.mymovie.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class ResultVideos {

    @Expose
    private long status ;

    @Expose
    private String statusText ;
    // 详情
    @Expose
    private VideosData data ;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public VideosData getData() {
        return data;
    }

    public void setData(VideosData data) {
        this.data = data;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "ResultVideos{" +
                "status=" + status +
                ", statusText='" + statusText + '\'' +
                ", data=" + data +
                '}';
    }
}
