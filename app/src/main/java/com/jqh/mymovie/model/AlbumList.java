package com.jqh.mymovie.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public class AlbumList extends ArrayList<Album> {

    private static final String TAG = AlbumList.class.getSimpleName();


    public void debug () {

        for(Album a:this)
        {
            Log.d(TAG,">> albumlist "+a.toString());
        }
    }
}
