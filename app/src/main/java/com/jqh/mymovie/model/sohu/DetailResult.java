package com.jqh.mymovie.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * 封装搜狐的返回数据
 * Created by jiangqianghua on 18/1/13.
 */

public class DetailResult {

    @Expose
    private long status ;

    @Expose
    private String statusText ;
    // 详情
    @Expose
    private ResultAlbum data ;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public ResultAlbum getData() {
        return data;
    }

    public void setData(ResultAlbum data) {
        this.data = data;
    }
}
