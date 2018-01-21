package com.jqh.mymovie.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.jqh.mymovie.R;

/**
 * Created by jiangqianghua on 18/1/21.
 */

public class LoadingView extends LinearLayout {
    public LoadingView(Context context) {
        super(context);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.loading_view_layout,this);
    }
}
