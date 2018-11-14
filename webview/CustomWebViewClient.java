package com.wusy.adv.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wusy.adv.R;

import java.util.Map;

/**
 * Created by XIAO RONG on 2018/11/6.
 */

public class CustomWebViewClient extends WebViewClient {
    private final String TAG = "CustomWebViewClient";
    private String pre = "";//约定的字段，用于拦截浏览器跳转，然后自定义操作
    private Activity activity;
    private NullPageControll nullPageControll;
    private boolean isError=false;

    public CustomWebViewClient(String pre, Activity activity, NullPageControll nullPageControll) {
        this.pre = pre;
        this.activity = activity;
        this.nullPageControll = nullPageControll;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i(TAG, "拦截到的url----" + url);
        if (url.contains(pre)) {
            Map<String, String> map = getParamsMap(url, pre);
            String code = map.get("code");
            String data = map.get("data");
            WebViewH5Order.parseCodeOrder(view, activity, null, code, data);
            return true;
        } else {
            //。。。这里执行浏览器的正常跳转
            return false;
        }
    }

    //开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i(TAG, "onPageStarted");
         nullPageControll.beginload();
    }

    //在页面加载结束时调用。我们可以关闭loading条，切换程序动作
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "onPageFinished");
        if (!isError){
            nullPageControll.initNullPage();
        }else{
            nullPageControll.errorload();
            isError=false;
        }
    }

    //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
//        Log.i(TAG,"onLoadResource");
    }

    //加载页面的服务器没有网络或者超时，触发
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Log.i(TAG, "onReceivedError");
        isError=true;
        nullPageControll.changeErrorImage(R.mipmap.notinternet);
    }

    @TargetApi(android.os.Build.VERSION_CODES.M)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        int statusCode = errorResponse.getStatusCode();
        if (!request.getUrl().toString().contains(".html")) return;
        Log.i(TAG, "onReceivedHttpError——" + statusCode + "\turl——" + request.getUrl());
        if (404 == statusCode || 500 == statusCode) {
            isError=true;
            nullPageControll.changeErrorImage(R.mipmap.notpage);
        }
    }

    //处理https请求
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        Log.i(TAG, "onReceivedSslError");
    }

    /**
     * 通过拦截到的URL和约定的标识，获取HTML传递过来的信息
     *
     * @param url
     * @param pre
     * @return
     */
    private Map<String, String> getParamsMap(String url, String pre) {
        ArrayMap<String, String> queryStringMap = new ArrayMap<>();
        if (url.contains(pre)) {
            int index = url.indexOf(pre);
            int end = index + pre.length();
            String queryString = url.substring(end + 1);

            String[] queryStringSplit = queryString.split("&");

            String[] queryStringParam;
            for (String qs : queryStringSplit) {
                if (qs.toLowerCase().startsWith("data=")) {
                    //单独处理data项，避免data内部的&被拆分
                    int dataIndex = queryString.indexOf("data=");
                    String dataValue = queryString.substring(dataIndex + 5);
                    queryStringMap.put("data", dataValue);
                } else {
                    queryStringParam = qs.split("=");

                    String value = "";
                    if (queryStringParam.length > 1) {
                        //避免后台有时候不传值,如“key=”这种
                        value = queryStringParam[1];
                    }
                    queryStringMap.put(queryStringParam[0].toLowerCase(), value);
                }
            }
        }
        return queryStringMap;
    }
}
