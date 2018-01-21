package com.jqh.mymovie.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jqh.mymovie.AppManager;
import com.jqh.mymovie.model.Site;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class VideoDetail {

    @Expose
    private Long vid ; // 视频id

    @Expose
    private long aid ; //专辑id

    @Expose
    @SerializedName("video_name")
    private String videoName ;


    @Expose
    @SerializedName("hor_high_pic")
    private String horHigPic ;

    @Expose
    @SerializedName("ver_high_pic")
    private String verHighPic ;


    @Expose
    private String title;

    private int site ;


    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getHorHigPic() {
        return horHigPic;
    }

    public void setHorHigPic(String horHigPic) {
        this.horHigPic = horHigPic;
    }

    public String getVerHighPic() {
        return verHighPic;
    }

    public void setVerHighPic(String verHighPic) {
        this.verHighPic = verHighPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }
}
