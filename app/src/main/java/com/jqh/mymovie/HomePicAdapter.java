package com.jqh.mymovie;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jiangqianghua on 17/10/29.
 */

public class HomePicAdapter extends PagerAdapter {

    private Context mContext ;
    public HomePicAdapter(Activity activity)
    {
        mContext = activity ;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_pic_item,null);
        TextView textView = (TextView)view.findViewById(R.id.tv_dec);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_img);

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


}
