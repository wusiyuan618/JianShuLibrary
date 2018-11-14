package com.wusy.adv.webview;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by XIAO RONG on 2018/11/6.
 */

public class WebSettingUtil {
    private static WebSettingUtil webSettingUtil;
    private static WebView webView;
    private static Context context;
    private WebSettings webSettings;
    private WebSettingUtil(){
    }
    public static synchronized WebSettingUtil getInstance(WebView webView, Context context){
        if(webSettingUtil==null) webSettingUtil=new WebSettingUtil();
        WebSettingUtil.context=context;
        WebSettingUtil.webView=webView;
        return webSettingUtil;
    }
    public void initWebSetting(){
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持javascrip
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存模式
        webSettings.setDomStorageEnabled(true);//是否支持持久化存储，保存到本地
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = context.getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);//设置数据库缓存路径
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);//开启database storage API功能
        webSettings.setAppCacheEnabled(true);//设置开启Application H5 Caches功能
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webSettings.setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");
        }
        // 设置可以支持缩放
        webSettings.setSupportZoom(false);//设置是否支持缩放
        webSettings.setBuiltInZoomControls(false);//设置是否出现缩放工具
        webSettings.setDisplayZoomControls(true); // 隐藏webview缩放按钮
        // 自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
    }
}
