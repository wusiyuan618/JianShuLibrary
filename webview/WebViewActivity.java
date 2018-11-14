package com.wusy.adv.webview;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.wusy.adv.R;

/**
 * Created by XIAO RONG on 2018/11/6.
 */

public class WebViewActivity extends Activity {
    private final String TAG = "WebViewActivity";
    private final String PRE = "protocol://android";
    private WebView webView;
    private String url = "http://222.211.90.120:6071/td_mobile/mingtu/login/html/login.html";
    private CustomWebChromeClient webChromeClient;
    private NullPageControll nullPageControll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webview);
        nullPageControll=new NullPageControll(this,webView);
        WebSettingUtil.getInstance(webView,this).initWebSetting();
        webView.loadUrl(url);
        webView.setWebViewClient(new CustomWebViewClient(PRE,this,nullPageControll));
        webChromeClient=new CustomWebChromeClient(this,nullPageControll);
        webView.setWebChromeClient(webChromeClient);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        WebViewH5Order.parseOnActivityForResult(webView,this,requestCode,resultCode,intent);
        webChromeClient.forActivityResult(requestCode,resultCode,intent);
    }
}
