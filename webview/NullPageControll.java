package com.wusy.adv.webview;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wusy.adv.R;

/**
 * Created by XIAO RONG on 2018/11/7.
 */

public class NullPageControll {
    private Activity activity;
    private ViewGroup nullpage,reloadpage;
    private TextView tv_reload,title;
    private ImageView img_back,img_error;
    private ProgressBar progressBar;
    private WebView webView;
    private int progress=0;
    public NullPageControll(Activity activity, WebView webView){
        this.activity=activity;
        this.webView=webView;
        findView();
        init();
    }
    private void init(){
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    activity.finish();
                }
            }
        });
    }
    private void findView(){
        nullpage=activity.findViewById(R.id.nullpage);
        reloadpage=activity.findViewById(R.id.reloadpage);
        tv_reload=activity.findViewById(R.id.reload);
        img_back=activity.findViewById(R.id.back);
        title=activity.findViewById(R.id.title);
        progressBar=activity.findViewById(R.id.progressbar);
        img_error=activity.findViewById(R.id.errorimg);
    }
    public void setTitle(String str){
        title.setText(str);
    }

    /**
     * 开始加载时调用
     */
    public void  beginload(){
        isShowNullPage(true);
        isShowReloadPage(false);
        startProgress();
    }

    /**
     * 错误加载时调用
     */
    public void errorload(){
        isShowReloadPage(true);
        isShowReloadPage(true);
        finishProgress();
    }
    /**
     * 将各个部件还原初始状态
     */
    public void initNullPage(){
        progress=0;
        progressBar.setProgress(progress);
        progressBar.setVisibility(View.GONE);
        isShowNullPage(false);
        isShowReloadPage(false);
    }

    /**
     * 更换错误加载时显示的图片，默认显示页面不存在的提示
     * @param resource
     */
    public void changeErrorImage(int resource){
        img_error.setImageResource(resource);
    }
    /**
     * 是否显示nullpage页面
     * @param isShow
     */
    private void isShowNullPage(boolean isShow){
        nullpage.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    /**
     * 是否显示reloadpage页面
     * @param isShow
     */
    private void isShowReloadPage(boolean isShow){
        reloadpage.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    /**
     * 开始加载动画
     * 这是一个假的进度条动画，目的是有个更好的体验效果
     */
    private void startProgress(){
        progress=0;
        progressBar.setProgress(progress);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress<95){
                    try {
                        Thread.sleep(20);
                        progress+=1;
                        progressBar.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    /**
     * 结束加载动画
     */
    private void finishProgress(){
        progress=100;
        progressBar.setProgress(progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                setTitle("");
            }
        },100);
    }
}
