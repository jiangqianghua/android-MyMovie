package com.jqh.mymovie.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public class Data {

    @SerializedName("count")
    @Expose
    private int count ;

    @SerializedName("videos")
    @Expose
    private List<ResultAlbum> list;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultAlbum> getList() {
        return list;
    }

    public void setList(List<ResultAlbum> list) {
        this.list = list;
    }
}
