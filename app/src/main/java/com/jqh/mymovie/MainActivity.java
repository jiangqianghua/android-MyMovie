package com.jqh.mymovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler ;
    private SharedPreferences mSharedPreferences ;

    public static final int GO_HOME = 1 ;
    public static final int GO_GUIDE = 2 ;

    public static final int ENTER_DOURATION = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new MyHandler();
        mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        init();
    }

    private void init()
    {
        boolean isFirstIn = mSharedPreferences.getBoolean("misFirstIn",true);
        if(isFirstIn)
        {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,ENTER_DOURATION);
        }
        else
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME,ENTER_DOURATION);
        }
    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case GO_GUIDE:
                    startGuideActivity();
                    break;
                case GO_HOME:
                    startHomeActivity();
                    break;
                default:
                    break;
            }
        }
    }
    // 进入到导航页
    private void startGuideActivity()
    {
        Intent intent = new Intent(MainActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    // 进入到主页面
    private void startHomeActivity()
    {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
