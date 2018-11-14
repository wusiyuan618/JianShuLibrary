package com.wusy.adv.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by XIAO RONG on 2018/11/7.
 */

public class WebViewH5Order {
    private static final String TAG = "WebViewH5Order";

    public static void parseCodeOrder(final WebView webView, final Activity activity, Fragment fragment, String code, String data) {
        switch (code) {
            default:
                Log.e(TAG, "WebViewH5Order接收到了未知的指令" + code + "；附带参数——" + decodeToUtf8(data));
                break;
        }
    }

    public static void parseOnActivityForResult(final WebView webView, Activity activity, int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            default:
                Log.e(TAG, "WebViewH5Order接收到未知的回调");
                break;
        }
    }

    /**
     * 解决返回的数据中文乱码
     *
     * @param data
     * @return
     */
    private static String decodeToUtf8(String data) {
        try {
            return URLDecoder.decode(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 调用js的方法
     * @param mJSMethodName JS的方法名
     * @param param 参数
     */
    public void loadJSMethod(WebView webView, String mJSMethodName, String param) {
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:" + mJSMethodName + "( " + param + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String result) {
                    //result为js方法返回结果
                }
            });
        } else {
            webView.loadUrl("javascript:" + mJSMethodName + "( " + param + ")");
        }
    }
}
