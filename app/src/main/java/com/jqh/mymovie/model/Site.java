package com.jqh.mymovie.model;

import android.content.Context;

import com.jqh.mymovie.R;

/**
 * Created by jiangqianghua on 18/1/10.
 */

public class Site {
    public static final int SOHU = 1 ;
    public static final int LETV = 2 ;



    private int siteId ;
    private String siteName ;
    private int resId ;
    private Context mContext ;

    public Site(int id, Context context) {
        mContext = context;
        siteId = id;
        switch (siteId) {
            case LETV:
                siteName = mContext.getResources().getString(R.string.site_letv);
                resId = R.drawable.ic_show;
                break;
            case SOHU:
                siteName = mContext.getResources().getString(R.string.site_sohu);
                resId = R.drawable.ic_movie;
                break;

        }
    }

    public int getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}