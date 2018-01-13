package com.jqh.mymovie.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.jqh.mymovie.AppManager;

/**
 * Created by jiangqianghua on 18/1/13.
 */

/**
 * 专辑Model
 */
public class Album implements Parcelable {


    private String albumId;//专辑id
    private int videoTota ; //集数
    private String title ;//专辑名称
    private String subTitle;//专辑子标题
    private String director; //导演
    private String mainActor; //主要演员
    private String verImageUrl;//专辑竖图
    private String horImageUrl;//专辑横图
    private String albumDesc ;//专辑描述
    private Site site ;  //网站
    private String tip ;// 提示
    private boolean iscompleted ;//专辑是否更新完
    private String letvStye ;// 乐视特殊字段
    private Context mContext ;

    public static final Parcelable.Creator<Album> CREATE = new Parcelable.Creator<Album>(){
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
    public Album(int siteId, Context context)
    {
        site = new Site(siteId,context);
        mContext = context ;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getVideoTota() {
        return videoTota;
    }

    public void setVideoTota(int videoTota) {
        this.videoTota = videoTota;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getVerImageUrl() {
        return verImageUrl;
    }

    public void setVerImageUrl(String verImageUrl) {
        this.verImageUrl = verImageUrl;
    }

    public String getHorImageUrl() {
        return horImageUrl;
    }

    public void setHorImageUrl(String horImageUrl) {
        this.horImageUrl = horImageUrl;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean iscompleted() {
        return iscompleted;
    }

    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }

    public String getLetvStye() {
        return letvStye;
    }

    public void setLetvStye(String letvStye) {
        this.letvStye = letvStye;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Album(Parcel in){
        albumId = in.readString();
        videoTota = in.readInt();
        title = in.readString();
        subTitle = in.readString();
        director = in.readString();
        mainActor = in.readString();
        verImageUrl = in.readString();
        horImageUrl = in.readString();
        albumDesc = in.readString();
        tip = in.readString();
        site = new Site(in.readInt(),mContext);
        iscompleted = in.readByte() != 0;
        letvStye = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(albumId);
        parcel.writeInt(videoTota);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeString(director);
        parcel.writeString(mainActor);
        parcel.writeString(verImageUrl);
        parcel.writeString(horImageUrl);
        parcel.writeString(albumDesc);
        parcel.writeString(tip);
        parcel.writeInt(site.getSiteId());
        parcel.writeByte((byte)(iscompleted?1:0));
        parcel.writeString(letvStye);

    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId='" + albumId + '\'' +
                ", videoTota=" + videoTota +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", director='" + director + '\'' +
                ", mainActor='" + mainActor + '\'' +
                ", verImageUrl='" + verImageUrl + '\'' +
                ", horImageUrl='" + horImageUrl + '\'' +
                ", albumDesc='" + albumDesc + '\'' +
                ", site=" + site +
                ", tip='" + tip + '\'' +
                ", iscompleted=" + iscompleted +
                ", letvStye='" + letvStye + '\'' +
                ", mContext=" + mContext +
                '}';
    }


    public String toJson(){
        String ret = AppManager.getGson().toJson(this);
        return ret ;
    }

    public Album fromJson(String json){
        Album album = AppManager.getGson().fromJson(json,Album.class);
        return album ;
    }
}
