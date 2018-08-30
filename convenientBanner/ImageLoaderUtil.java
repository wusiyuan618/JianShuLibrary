package com.example.internet;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * Created by 达拉然的风 on 2017/5/17.
 */

public class ImageLoaderUtil {
    public static ImageLoaderUtil imageLoaderUtil;
    private Context mC;
    private int defultFailImage=R.mipmap.ic_launcher;
    private int defultLoadingImage=R.mipmap.ic_launcher;
    private DisplayImageOptions options;

    private ImageLoaderUtil(Context context) {
        this.mC=context;
        init();
    }

    public synchronized static ImageLoaderUtil getInstance(Context context) {
        if (imageLoaderUtil == null) {
            imageLoaderUtil = new ImageLoaderUtil(context);
        }
        return imageLoaderUtil;
    }
    private void init(){
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(mC);
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 对图片加载中时以及图片加载失败时显示的图片进行定制
     * @param defultFailImage 加载错误时显示IDE图片
     * @param defultLoadingImage 加载中时显示的照片
     */
    public void setPointImage(int defultFailImage,int defultLoadingImage){
        this.defultLoadingImage=defultLoadingImage;
        this.defultFailImage=defultFailImage;
    }

    /**
     * 加载网络图片方法
     * @param imageUrl 图片的URL
     * @param imageView 承载图片的控件ImageView及其子类
     */
    public void loadingImage(String imageUrl, ImageView imageView){
        if(options==null){
            options=createDefultOptions();
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * 加载网络图片方法
     * @param imageUrl 图片的URL
     * @param imageView 承载图片的控件ImageView及其子类
     * @param options 对图片加载的进行定制
     */
    public void loadingImage(String imageUrl, ImageView imageView,DisplayImageOptions options){
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * ImageLoader加载本地图片，可以将本地图片地址转化成URL然后去获取输入流
     * @param type 上传图片的来源类型——file、assets、drawable
     * @param imagePath 上传图片的路径。路径写法示例：
     *                  file--“/mnt/sdcard/image.png”
     *                  assets--“image.png”
     *                  drawable--“R.drawable.image”
     * @return 返回值是图片的URL，通过loadingImage方法传入即可加载
     */
    public String createImageUrl(String type,String imagePath){
        String imageUrl="";
        switch (type){
            case "file":
                imageUrl=ImageDownloader.Scheme.FILE.wrap(imagePath);
            break;
            case "assets":
                imageUrl= ImageDownloader.Scheme.ASSETS.wrap(imagePath);
                break;
            case "drawable":
                imageUrl=ImageDownloader.Scheme.DRAWABLE.wrap(imagePath);
                break;
            default:
                imageUrl="fail";
                break;
        }
        return imageUrl;
    }
    /**
     * 释放DisplayImageOptions
     */
    public void cleanOptions(){
        options=null;
    }

    /**
     * 在列表中大量加载图片。如果滑动屏幕，希望能够暂停图片加载
     * @param listView
     * @param isStop 传入ture表示滑动时停止加载
     */
    public void setScrollStopLoad(ListView listView,boolean isStop){
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), isStop, isStop));
    }
    public void setScrollStopLoad(GridView gridView, boolean isStop){
        gridView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), isStop, isStop));
    }

    /**
     * 创建一个默认的options
     * @return
     */
    private DisplayImageOptions createDefultOptions(){
        DisplayImageOptions  options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defultLoadingImage)
                .showImageOnFail(defultFailImage)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }
}
