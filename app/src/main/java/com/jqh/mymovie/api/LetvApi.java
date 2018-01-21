package com.jqh.mymovie.api;

import com.jqh.mymovie.AppManager;
import com.jqh.mymovie.model.Album;
import com.jqh.mymovie.model.AlbumList;
import com.jqh.mymovie.model.Channel;
import com.jqh.mymovie.model.ErrorInfo;
import com.jqh.mymovie.model.Site;
import com.jqh.mymovie.model.Video;
import com.jqh.mymovie.model.sohu.Result;
import com.jqh.mymovie.model.sohu.ResultAlbum;
import com.jqh.mymovie.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public class LetvApi extends BaseSiteApi {

    private static final String TAG = LetvApi.class.getSimpleName();
    private static final int LETV_CHANNELID_MOVIE = 1; //乐视电影频道ID
    private static final int LETV_CHANNELID_SERIES = 2; //乐视电视剧频道ID
    private static final int LETV_CHANNELID_VARIETY = 11; //乐视综艺频道ID
    private static final int LETV_CHANNELID_DOCUMENTRY = 16; //乐视纪录片频道ID
    private static final int LETV_CHANNELID_COMIC = 5; //乐视动漫频道ID
    private static final int LETV_CHANNELID_MUSIC = 9; //乐视音乐频道ID
    private static final int BITSTREAM_SUPER = 100;
    private static final int BITSTREAM_NORMAL = 101;
    private static final int BITSTREAM_HIGH = 102;
    //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/listalbum/act/index/src/1/cg/2/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/1/ps/30/pcode/010110263/version/5.6.2.mindex.html
    private final static String ALBUM_LIST_URL_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_DOCUMENTARY_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/3/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_SHOW_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/album/act/detail/id/10026309/pcode/010410000/version/2.1.mindex.html
    private final static String ALBUM_DESC_URL_FORMAT = "http://static.meizi.app.m.letv.com/" +
            "android/mod/mob/ctl/album/act/detail/id/%s/pcode/010410000/version/2.1.mindex.html";
    //key : bh65OzqYYYmHRQ
    private final static String SEVER_TIME_URL = "http://dynamic.meizi.app.m.letv.com/android/dynamic.php?mod=mob&ctl=timestamp&act=timestamp&pcode=010410000&version=5.4";

    //http://static.app.m.letv.com/android/mod/mob/ctl/videolist/act/detail/id/10026309/vid/0/b/1/s/30/o/-1/m/1/pcode/010410000/version/2.1.mindex.html
    private final static String ALBUM_VIDEOS_URL_FORMAT = "http://static.app.m.letv.com/" +
            "android/mod/mob/ctl/videolist/act/detail/id/%s/vid/0/b/%s/s/%s/o/%s/m/%s/pcode/010410000/version/2.1.mindex.html";

    //arg: mmsid currentServerTime key vid
    private final static String VIDEO_FILE_URL_FORMAT = "http://dynamic.meizi.app.m.letv.com/android/dynamic.php?mmsid=" +
            "%s&playid=0&tss=ios&pcode=010410000&version=2.1&tm=%s&key=%s&vid=" +
            "%s&ctl=videofile&mod=minfo&act=index";

    private final static String VIDEO_REAL_LINK_APPENDIX = "&format=1&expect=1&termid=2&pay=0&ostype=android&hwtype=iphone";

    //http://play.g3proxy.lecloud.com/vod/v2/MjYwLzkvNTIvbGV0di11dHMvMTQvdmVyXzAwXzIyLTEwOTczMjQ5NzUtYXZjLTE5OTY1OS1hYWMtNDgwMDAtMjU4NjI0MC04Mzk3NjQ4OC04MmQxMGVlM2I3ZTdkMGU5ZjE4YzM1NDViMWI4MzI4Yi0xNDkyNDA2MDE2MTg4Lm1wNA==?b=259&mmsid=64244666&tm=1492847915&key=22f2f114ed643e0d08596659e5834cd6&platid=3&splatid=347&playid=0&tss=ios&vtype=21&cvid=711590995389&payff=0&pip=83611a86979ddb3df8ef0fb41034f39c&format=1&sign=mb&dname=mobile&expect=3&p1=0&p2=00&p3=003&tag=mobile&pid=10031263&format=1&expect=1&termid=2&pay=0&ostype=android&hwtype=iphone


    @Override
    public void onGetChannelAlbums(Channel channel, int pageNo, int pageSize, OnGetChannelAlbumListener listener) {
        String url = getChannelAlbumUrl(channel,pageNo,pageSize);
        doGetChannelAlbumsByUrl(url,listener);
    }

    private String getChannelAlbumUrl(Channel channel,int pageNo,int pageSize){
        if(channel.getChannelId() == Channel.DOCUMENTRY){
            return String.format(ALBUM_LIST_URL_DOCUMENTARY_FORMAT,conVertChannleId(channel),pageNo,pageSize);
        }else if(channel.getChannelId() == Channel.SHOW){
            return String.format(ALBUM_LIST_URL_SHOW_FORMAT,conVertChannleId(channel),pageNo,pageSize);
        }
        return String.format(ALBUM_LIST_URL_FORMAT,conVertChannleId(channel),pageNo,pageSize);
    }

    /**
     * 频道id转化
     * @param channel
     * @return
     */
    private int conVertChannleId(Channel channel){
        int channelId = -1; //无效
        switch (channel.getChannelId()){
            case Channel.SHOW:
                channelId = LETV_CHANNELID_SERIES ;
                break;
            case Channel.MOVIE:
                channelId = LETV_CHANNELID_MOVIE ;
                break;
            case Channel.DOCUMENTRY:
                channelId = LETV_CHANNELID_DOCUMENTRY ;
                break;
            case Channel.COMIC:
                channelId = LETV_CHANNELID_COMIC ;
                break;
            case Channel.MUSIC:
                channelId = LETV_CHANNELID_MUSIC ;
                break;
            case Channel.VARIETY:
                channelId = LETV_CHANNELID_VARIETY;
                break;
        }
        return channelId ;
    }


    private void doGetChannelAlbumsByUrl(final String url,final OnGetChannelAlbumListener listener){
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
                String json = response.body().string();
                try {
                    JSONObject resultJson = new JSONObject(json);
                    JSONObject bodyJson = resultJson.optJSONObject("body");
                    if(bodyJson.optInt("album_count") > 0){
                        AlbumList albumList = new AlbumList();
                        JSONArray albumListJson = bodyJson.optJSONArray("album_list");
                        for(int i = 0 ; i < albumListJson.length() ; i++){
                            Album album = new Album(Site.LETV,AppManager.getContext());
                            JSONObject albumJson = albumListJson.getJSONObject(i);
                            album.setAlbumDesc(albumJson.getString("subname"));
                            album.setAlbumId(albumJson.getString("aid"));
                            //album.setDirector(albumJson.getString(""));
                            JSONObject jsonObject = albumJson.getJSONObject("images");
                            album.setHorImageUrl(jsonObject.getString("400*300"));
                            //album.setMainActor(albumJson.getString(""));
                            album.setSubTitle(albumJson.getString("name"));
                            album.setTitle(albumJson.getString("name"));
                            album.setTip(albumJson.getString("subname"));
                            album.setVideoTota(albumJson.getInt("episodes"));
                            albumList.add(album);
                        }
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
                }catch (JSONException e){
                    e.printStackTrace();
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumsByUrl",null,ErrorInfo.ERROR_TYPE_DATA_PARSE_JSON);
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
                Album album = new Album(Site.LETV,AppManager.getContext());
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
        ErrorInfo info = new ErrorInfo(Site.LETV,type);
        info.setFunctionName(functionName);
        info.setExceptionString(e.getMessage());
        info.setTag(TAG);
        info.setUrl(url);
        info.setClassName(TAG);
        return info;
    }

    public void onGetAlbumsDetail(Album album , OnGetAlbumDetailListener listener){

    }

    public void onGetVideo(Album album , int pageNo,int pageSize, OnGetVideoListener listener){

    }

    public void onGetVideoUrl(Video video, OnGetVideoPlayUrlListener listener){

    }
}
