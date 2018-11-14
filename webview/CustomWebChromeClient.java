package com.wusy.adv.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.wusy.adv.R;

import java.util.regex.Pattern;

/**
 * Created by XIAO RONG on 2018/11/7.
 * WebChromeClient 可以辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
 */

public class CustomWebChromeClient extends WebChromeClient {
    private final String TAG="CustomWebChromeClient";
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 2;
    private NullPageControll nullPageControll;
    private Activity activity;
    public CustomWebChromeClient(Activity activity,NullPageControll nullPageControll){
        this.activity=activity;
        this.nullPageControll=nullPageControll;
    }
    //获取网页的加载进度并显示
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Log.i(TAG,"onProgressChanged——"+newProgress);
    }
    //获取Web网页中的标题
    @Override
    public void onReceivedTitle(WebView view, String title) {
        Log.i(TAG,"onReceivedTitle——"+title);
        if(isChinese(title)) nullPageControll.setTitle(title);
        // android 6.0 以下通过title获取加载错误的信息
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (title.contains("404") || title.contains("500") || title.contains("Error")) {
                nullPageControll.changeErrorImage(R.mipmap.notpage);
                nullPageControll.errorload();
            }
        }
    }

    // For Lollipop 5.0+ Devices
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (uploadMessage != null) {
            uploadMessage.onReceiveValue(null);
            uploadMessage = null;
        }
        uploadMessage = filePathCallback;
        Intent intent = fileChooserParams.createIntent();
        try {
            activity.startActivityForResult(intent, REQUEST_SELECT_FILE);
        } catch (ActivityNotFoundException e) {
            uploadMessage = null;
            Log.i(TAG, "Cannot Open File Chooser");
            return false;
        }
        return true;
    }
    private boolean isChinese(String str){
        String regEx="^[\\u0391-\\uFFE5]+$ ";
        return Pattern.compile(regEx).matcher(str).matches();
    }

    /**
     * Activity回调调用
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void forActivityResult(int requestCode, int resultCode, Intent intent){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != WebViewActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else{
            Log.e(TAG,"Failed to Upload Image");
        }
    }
    // For 3.0+ Devices (Start)
    // onActivityResult attached before constructor
    protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
    }

    //For Android 4.1 only
    protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
    }

    protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }
}
