package com.jqh.mymovie.api;

import android.util.Log;

import com.jqh.mymovie.AppManager;
import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.AlbumList;
import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Site;
import com.jqh.mymovie.model.sohu.Result;
import com.jqh.mymovie.model.sohu.ResultAlbum;
import com.jqh.mymovie.utils.OkHttpUtils;

import java.io.IOException;

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
                album.setVideoTota(resultAlbum.getTotalVideocount());
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

}
