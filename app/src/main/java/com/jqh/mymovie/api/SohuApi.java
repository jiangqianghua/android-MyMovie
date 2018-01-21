package com.jqh.mymovie.api;

import android.text.TextUtils;
import android.util.Log;

import com.jqh.mymovie.AppManager;
import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.AlbumList;
import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Site;
import com.jqh.mymovie.model.Video;
import com.jqh.mymovie.model.VideoList;
import com.jqh.mymovie.model.sohu.DetailResult;
import com.jqh.mymovie.model.sohu.Result;
import com.jqh.mymovie.model.sohu.ResultAlbum;
import com.jqh.mymovie.model.sohu.ResultVideos;
import com.jqh.mymovie.model.sohu.VideoDetail;
import com.jqh.mymovie.utils.OkHttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public class SohuApi extends BaseSiteApi {

    public static final String TAG = SohuApi.class.getSimpleName();
    private static final int SOHU_CHANNEL_MOVIE = 1; // 电影频道
    private static final int SOHU_CHANNEL_SERIES = 2;// 电视剧
    private static final int SOHU_CHANNEL_VARIETY = 7;// 综艺
    private static final int SOHU_CHANNEL_DOCUMENTRY = 8;// 电记录
    private static final int SOHU_CHANNEL_COMIC = 16;// 动漫
    private static final int SOHU_CHANNEL_MUSIC = 24; // 音乐

    //某一专辑详情
    //http://api.tv.sohu.com/v4/album/info/9112373.json?plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
    private final static String API_KEY = "plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
    private final static String API_ALBUM_INFO = "http://api.tv.sohu.com/v4/album/info/";
    //http://api.tv.sohu.com/v4/search/channel.json?cid=2&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47&page=1&page_size=1
    private final static String API_CHANNEL_ALBUM_FORMAT = "http://api.tv.sohu.com/v4/search/channel.json" +
            "?cid=%s&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&" +
            "sver=6.2.0&sysver=4.4.2&partner=47&page=%s&page_size=%s";
    //http://api.tv.sohu.com/v4/album/videos/9112373.json?page=1&page_size=50&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
    private final static String API_ALBUM_VIDOES_FORMAT = "http://api.tv.sohu.com/v4/album/videos/%s.json?page=%s&page_size=%s&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
    // 播放url
    //http://api.tv.sohu.com/v4/video/info/3669315.json?site=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=4.5.1&sysver=4.4.2&partner=47&aid=9112373
    private final static String API_VIDEO_PLAY_URL_FORMAT = "http://api.tv.sohu.com/v4/video/info/%s.json?site=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=4.5.1&sysver=4.4.2&partner=47&aid=%s";
    //真实url格式 m3u8
    //http://hot.vrs.sohu.com/ipad3669271_4603585256668_6870592.m3u8?plat=6uid=f5dbc7b40dad477c8516885f6c681c01&pt=5&prod=app&pg=1
    @Override
    public void onGetChannelAlbums(Channel channel, int pageNo, int pageSize, OnGetChannelAlbumListener listener) {
        String url = getChannelAbumUrl(channel,pageNo,pageSize);
        doGetChannelAlbumsByUrl(url,listener);
    }

    private String getChannelAbumUrl(Channel channel, int pageNo,int pageSize)
    {
        return String.format(API_CHANNEL_ALBUM_FORMAT,toConvertChannelId(channel),pageNo,pageSize);
    }

    /**
     * 频道id转化
     * @param channel
     * @return
     */
    private int toConvertChannelId(Channel channel){
        int channelId = -1; //无效
        switch (channel.getChannelId()){
            case Channel.SHOW:
                channelId = SOHU_CHANNEL_SERIES ;
                break;
            case Channel.MOVIE:
                channelId = SOHU_CHANNEL_MOVIE ;
                break;
            case Channel.DOCUMENTRY:
                channelId = SOHU_CHANNEL_DOCUMENTRY ;
                break;
            case Channel.COMIC:
                channelId = SOHU_CHANNEL_COMIC ;
                break;
            case Channel.MUSIC:
                channelId = SOHU_CHANNEL_MUSIC ;
                break;
            case Channel.VARIETY:
                channelId = SOHU_CHANNEL_VARIETY;
                break;
        }
        return channelId ;
    }

    /**
     * 网络请求
     * @param url
     * @param listener
     */
    public void doGetChannelAlbumsByUrl(final String url, final OnGetChannelAlbumListener listener){
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener != null)
                {
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumsByUrl",e,ErrorInfo.ERROR_TYPE_URL);
                    listener.onOnGetChannelAlbumFailed(info);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumsByUrl",null,ErrorInfo.ERROR_TYPE_HTTP);
                    listener.onOnGetChannelAlbumFailed(info);
                    return ;
                }

                //取得数据转Result
                //转Result成Album
                //Album存AlbumList
                Result result = AppManager.getGson().fromJson(response.body().string(),Result.class);
                AlbumList albumList = toConvertAlbumList(result);
                if(albumList != null)
                {
                    if(albumList.size() > 0 && listener != null)
                    {
                        listener.onOnGetChannelAlbumSuccess(albumList);
                    }
                }
                else
                {
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumsByUrl",null,ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                    listener.onOnGetChannelAlbumFailed(info);
                }
            }
        });
    }

    private AlbumList toConvertAlbumList(Result result){
        if(result.getData() != null && result.getData().getList() != null &&
                result.getData().getList().size() > 0)
        {
            // 有数据
            AlbumList albumList  = new AlbumList();
            for(ResultAlbum resultAlbum:result.getData().getList())
            {
                Album album = new Album(Site.SOHU,AppManager.getContext());
                album.setAlbumDesc(resultAlbum.getTvDesc());
                album.setAlbumId(resultAlbum.getAlbumId());
                album.setDirector(resultAlbum.getDirector());
                album.setHorImageUrl(resultAlbum.getHorHighPic());
                album.setMainActor(resultAlbum.getMainActor());
                album.setSubTitle(resultAlbum.getAlbumName());
                album.setTitle(resultAlbum.getAlbumName());
                album.setTip(resultAlbum.getTip());
                album.setVideoTota(resultAlbum.getTotalVideoCount());
                albumList.add(album);
            }
            return albumList;
        }
        return null ;
    }

    private ErrorInfo buildErrorInfo(String url,String functionName,Exception e,int type){
        ErrorInfo info = new ErrorInfo(Site.SOHU,type);
        info.setFunctionName(functionName);
        info.setExceptionString(e.getMessage());
        info.setTag(TAG);
        info.setUrl(url);
        info.setClassName(TAG);
        return info;
    }

    public void onGetAlbumsDetail(final Album album , final OnGetAlbumDetailListener listener){
        final String  url = API_ALBUM_INFO  + album.getAlbumId()+".json?"+API_KEY;
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener != null)
                {
                    ErrorInfo info = buildErrorInfo(url,"onGetAlbumsDetail",e,ErrorInfo.ERROR_TYPE_URL);
                    listener.onOnGetAlbumDetailFailed(info);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    ErrorInfo info = buildErrorInfo(url,"onGetAlbumsDetail",null,ErrorInfo.ERROR_TYPE_HTTP);
                    listener.onOnGetAlbumDetailFailed(info);
                    return ;
                }

                DetailResult result =  AppManager.getGson().fromJson(response.body().string(),DetailResult.class);
                if(result.getData() != null){
                    if(result.getData().getLastVideoCount() > 0){
                        album.setVideoTota(result.getData().getLastVideoCount());
                    }
                    else{
                        album.setVideoTota(result.getData().getTotalVideoCount());
                    }
                }
                if(listener != null){
                    listener.onOnGetAlbumDetailSuccess(album);
                }
            }
        });
    }


    public void onGetVideo(Album album , int pageNo,int pageSize, final OnGetVideoListener listener){

        final String url = String.format(API_ALBUM_VIDOES_FORMAT,album.getAlbumId(),pageNo,pageSize);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener != null)
                {
                    ErrorInfo info = buildErrorInfo(url,"onGetVideo",e,ErrorInfo.ERROR_TYPE_URL);
                    listener.onGetVideoFailed(info);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    ErrorInfo info = buildErrorInfo(url,"onGetVideo",null,ErrorInfo.ERROR_TYPE_HTTP);
                    listener.onGetVideoFailed(info);
                    return ;
                }
                ResultVideos result = AppManager.getGson().fromJson(response.body().string(),ResultVideos.class);
                VideoList videoList = new VideoList();
                if(result != null && result.getData() != null){
                    //Log.d(TAG ,"result video >> "+result.toString());
                    for(VideoDetail video:result.getData().getVideoList()){
                        Video v = new Video();
                        v.setHorHigPic(video.getHorHigPic());
                        v.setVerHighPic(video.getVerHighPic());
                        v.setVid(video.getVid());
                        v.setVideoName(video.getVideoName());
                        v.setAid(video.getAid());
                        videoList.add(v);
                    }
                }
                if(listener != null){
                    listener.onOnGetVideoSuccess(videoList);
                }
            }
        });

    }

    /**
     * 返回播放数据源
     * @param video
     * @param listener
     */
    public void onGetVideoUrl(final Video video, final OnGetVideoPlayUrlListener listener) {

        final String url = String.format(API_VIDEO_PLAY_URL_FORMAT, video.getVid(), video.getAid());
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo info = buildErrorInfo(url, "onGetVideoUrl", e, ErrorInfo.ERROR_TYPE_URL);
                    listener.onGetFailed(info);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo info = buildErrorInfo(url, "onGetVideoUrl", null, ErrorInfo.ERROR_TYPE_HTTP);
                    listener.onGetFailed(info);
                    return;
                }
                try {
                    JSONObject result = new JSONObject(response.body().string());
                    JSONObject data = result.optJSONObject("data");
                    String normaUrl = data.optString("url_nor");
                    if (!TextUtils.isEmpty(normaUrl)) {
                        normaUrl += "uid=" + getUUID() + "&pt=5&prod=app&pg=1";
                        video.setNormalUrl(normaUrl);
                        if(listener != null){
                            listener.onGetNormal(video,normaUrl);
                        }
                    }

                    String superUrl = data.optString("url_super");
                    if (!TextUtils.isEmpty(superUrl)) {
                        superUrl += "uid=" + getUUID() + "&pt=5&prod=app&pg=1";
                        video.setSuperUrl(superUrl);
                        if(listener != null){
                            listener.onGetSuperUrl(video,superUrl);
                        }
                    }

                    String highUrl = data.optString("url_high");
                    if (!TextUtils.isEmpty(highUrl)) {
                        highUrl += "uid=" + getUUID() + "&pt=5&prod=app&pg=1";
                        video.setHighUrl(highUrl);
                        if(listener != null){
                            listener.onGetHighUrl(video,highUrl);
                        }
                    }

                } catch (Exception e) {

                }
            }
        });
    }


    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
