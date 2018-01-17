package com.jqh.mymovie.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jqh.mymovie.R;

/**
 * Created by jiangqianghua on 18/1/14.
 */

public class ImageUtils {

    private static final float VER_POSTER_RATO = 0.73f ;
    private static final float HOR_POSTER_RATO = 1.5f ;

    public static void disPlayImage(ImageView view,String url,int w , int h){
        if(view != null && url != null && w > 0 && h > 0)
        {
            if(w > h)
            {
                Glide.with(view.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存
                        .error(R.drawable.ic_loading_hor)
                        .fitCenter()  // 图片居中
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

    /**
     * 让图片获取到最佳比例
     * @param context
     * @param columns
     * @return
     */
    public static Point getVerPostSize(Context context, int columns){
        int width = getScreenWidthPixel(context)/columns;
        width = width - (int)context.getResources().getDimension(R.dimen.dimen_6dp);
        int height = Math.round((float)width/VER_POSTER_RATO);
        Point point = new Point();
        point.x = width ;
        point.y = height ;
        return point ;
    }

    /**
     * 让图片获取到最佳比例
     * @param context
     * @param columns
     * @return
     */
    public static Point getHorPostSize(Context context, int columns){
        int width = getScreenWidthPixel(context)/columns;
        width = width - (int)context.getResources().getDimension(R.dimen.dimen_6dp);
        int height = Math.round((float)width/HOR_POSTER_RATO);
        Point point = new Point();
        point.x = width ;
        point.y = height ;
        return point ;
    }


    public static int getScreenWidthPixel(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x ;
        return width ;
    }
}
