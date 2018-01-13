package com.jqh.mymovie.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * 封装搜狐的返回数据
 * Created by jiangqianghua on 18/1/13.
 */

public class Result {

    @Expose
    private long status ;

    @Expose
    private String statusText ;

    @Expose
    private Data data ;


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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
