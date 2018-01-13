package com.jqh.mymovie.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jqh.mymovie.R;

/**
 * Created by jiangqianghua on 18/1/14.
 */

public class ImageUtils {

    public static void disPlayImage(ImageView view,String url,int w , int h){
        if(view != null && url != null && w > 0 && h > 0)
        {
            if(w > h)
            {
                Glide.with(view.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存
                        .error(R.drawable.ic_loading_hor)
                        .centerCrop()  // 图片居中
                        .override(h,w)
                        .into(view)  //加到imageview
                ;
            }
            else
            {
                Glide.with(view.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存
                        .error(R.drawable.ic_loading_hor)
                        .centerCrop()  // 图片居中
                        .override(w,h)
                        .into(view)  //加到imageview
                ;
            }
        }
    }
}
