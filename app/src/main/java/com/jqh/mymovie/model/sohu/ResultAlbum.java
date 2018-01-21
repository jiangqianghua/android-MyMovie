package com.jqh.mymovie.model.sohu;

/**
 * Created by jiangqianghua on 18/1/13.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 真正的搜狐频道数据结构
 * http://api.tv.sohu.com/v4/search/channel.json?
 * cid=2&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd
 * &sver=6.2.0&sysver=4.4.2&partner=47&page=1&page_size=30
 */
public class ResultAlbum {

    //SerializedName作用是属性跟json的字段对应
    //解决定义的属性和json不一样的情况


    @SerializedName("album_desc")
    @Expose
    private String tvDesc ; //描述

    @SerializedName("director")
    @Expose
    private String director ;//导演

    @SerializedName("hor_high_pic")
    @Expose
    private String horHighPic;//横图

    @SerializedName("ver_high_pic")
    @Expose
    private String verHighPic;//竖图

    @SerializedName("main_actor")
    @Expose
    private String mainActor;//主演

    @SerializedName("album_name")
    @Expose
    private String albumName ;//专辑名字

    //@SerializedName("tip")
    @Expose
    private String tip ;//更新到XX集，更新到多少期

    @SerializedName("aid")
    @Expose
    private String albumId;//专辑id


    @SerializedName("latest_video_count")  //专辑更到到多少集
    @Expose
    private int lastVideoCount ;

    @SerializedName("total_video_count") //专辑总集数
    @Expose
    private int totalVideoCount;

    public String getTvDesc() {
        return tvDesc;
    }

    public void setTvDesc(String tvDesc) {
        this.tvDesc = tvDesc;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getHorHighPic() {
        return horHighPic;
    }

    public void setHorHighPic(String horHighPic) {
        this.horHighPic = horHighPic;
    }

    public String getVerHighPic() {
        return verHighPic;
    }

    public void setVerHighPic(String verHighPic) {
        this.verHighPic = verHighPic;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }


    public int getLastVideoCount() {
        return lastVideoCount;
    }

    public void setLastVideoCount(int lastVideoCount) {
        this.lastVideoCount = lastVideoCount;
    }

    public int getTotalVideoCount() {
        return totalVideoCount;
    }

    public void setTotalVideoCount(int totalVideoCount) {
        this.totalVideoCount = totalVideoCount;
    }
}
