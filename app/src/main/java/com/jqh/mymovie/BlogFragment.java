package com.jqh.mymovie;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jqh.mymovie.base.BaseFragment;

/**
 * Created by jiangqianghua on 17/9/23.
 */

public class BlogFragment extends BaseFragment {
    private WebView mWebView ;
    private ProgressBar mProgressBar ;
    private static final int MAX_VALUE = 100 ;
    //private static final String BLOG_VALUE = "http://m.blog.csdn.net/blog/index?username=hejjuanlin";
    private static final String BLOG_VALUE = "http://m.blog.csdn.net/qq_24210469";
    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initData() {
        mWebView = bindViewId(R.id.webview);
        WebSettings settings = mWebView.getSettings() ;
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        mProgressBar = bindViewId(R.id.pb_progress);
        mProgressBar.setMax(MAX_VALUE);

        mWebView.loadUrl(BLOG_VALUE);
        mWebView.setWebChromeClient(mWebChromeClient);
    }


    private WebChromeClient mWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if(newProgress == MAX_VALUE)
            {
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    };


}
