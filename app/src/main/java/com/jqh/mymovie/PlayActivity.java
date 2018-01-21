package com.jqh.mymovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jqh.mymovie.model.Video;

public class PlayActivity extends AppCompatActivity {

    private String TAG = "PlayActivity";
    private String url ;
    private Video video ;
    private int streamType ;
    private int currentPosition ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        url = getIntent().getStringExtra("url");
        streamType = getIntent().getIntExtra("type",0);
        currentPosition = getIntent().getIntExtra("currentPosition",0);
        video = (Video)getIntent().getParcelableExtra("video");
        Log.d(TAG, "url = "+url + " streamType = "+streamType + " currpos = "+currentPosition);
        Log.d(TAG,"video = "+ video.toString());

    }
}
