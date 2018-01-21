package com.jqh.mymovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jqh.mymovie.AppManager;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class Video implements Parcelable{

    private Long vid ;

    private Long aid ;

    private String videoName ;

    private String horHigPic ;

    private String verHighPic ;

    private String title;

    private int site ;

    private String superUrl ;

    private String normalUrl ;

    private String highUrl ;

    public String getSuperUrl() {
        return superUrl;
    }

    public void setSuperUrl(String superUrl) {
        this.superUrl = superUrl;
    }

    public String getNormalUrl() {
        return normalUrl;
    }

    public void setNormalUrl(String normalUrl) {
        this.normalUrl = normalUrl;
    }

    public String getHighUrl() {
        return highUrl;
    }

    public void setHighUrl(String highUrl) {
        this.highUrl = highUrl;
    }

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

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Video() {
    }

    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>(){
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    private Video(Parcel in){
        vid = in.readLong();
        aid = in.readLong();
        videoName = in.readString();
        horHigPic = in.readString();
        verHighPic = in.readString();
        title = in.readString();
        site = in.readInt();
        superUrl = in.readString();
        normalUrl = in.readString();
        highUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeLong(vid);
        parcel.writeLong(aid);
        parcel.writeString(videoName);
        parcel.writeString(horHigPic);
        parcel.writeString(verHighPic);
        parcel.writeString(title);
        parcel.writeInt(site);
        parcel.writeString(superUrl);
        parcel.writeString(normalUrl);
        parcel.writeString(highUrl);
    }
    @Override
    public String toString() {
        return "Video{" +
                "vid=" + vid +
                ", aid=" + aid +
                ", videoName='" + videoName + '\'' +
                ", horHigPic='" + horHigPic + '\'' +
                ", verHighPic='" + verHighPic + '\'' +
                ", title='" + title + '\'' +
                ", site=" + site +
                ", superUrl='" + superUrl + '\'' +
                ", normalUrl='" + normalUrl + '\'' +
                ", highUrl='" + highUrl + '\'' +
                '}';
    }
}
