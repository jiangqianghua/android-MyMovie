package com.jqh.mymovie.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class VideoList extends ArrayList<Video> {

    private static final String TAG = VideoList.class.getSimpleName();


    public void debug () {

        for(Video a:this)
        {
            Log.d(TAG,">> VideoDetail "+a.toString());
        }
    }
}
