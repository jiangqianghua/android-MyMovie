package com.jqh.mymovie.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class VideosData {

    @Expose
    private int count ;

    @SerializedName("videos")
    @Expose
    private List<VideoDetail> videoList = new ArrayList<VideoDetail>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<VideoDetail> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDetail> videoList) {
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "VideosData{" +
                "count=" + count +
                ", videoList=" + videoList +
                '}';
    }
}
