package com.example.internet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 达拉然的风 on 2017/5/18.
 */

public class ConvenientBannerActivity extends Activity implements OnItemClickListener{
    private ConvenientBanner convenientBanner;
    private ArrayList<BannerBean> localImages = new ArrayList<BannerBean>();
    ImageLoaderUtil imageLoaderUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenientbanner);
        imageLoaderUtil=ImageLoaderUtil.getInstance(this);
        init();
        bannerInit();
    }

    private void init() {
        convenientBanner= (ConvenientBanner) findViewById(R.id.convenientbanner);
        localImages.add(new BannerBean("海贼王一","尾田荣一郎小时候是看着鸟山明的《龙珠》长大的，那部作品对他影响巨大。当时尾田最大的心愿就是能在单行本中看到更多的集数。",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=d8a388fd690f6e490e438b35f053f3c9&imgtype=0&src=http%3A%2F%2Fimg.news.d.cn%2FUE%2Fnet%2FUEUpload%2F6357315944959662505177784.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=267d33f39b69ad2c856b3d66141bf4bf&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201502%2F12%2F20150212231238_4GeHk.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=954e5a1c61b842f3302dfcc8671d8224&imgtype=0&src=http%3A%2F%2Fgss0.bdstatic.com%2FyqACvGbaBA94lNC68IqT0jB-xx1xbK%2Fztd%2Fw%3D350%3Bq%3D70%2Fsign%3D76348eecfd36afc30e0c396083229af9%2F30adcbef76094b36d9d4d8c1a9cc7cd98d109d5c.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=2ee67c5757345fd4c74c9819a8862f70&imgtype=0&src=http%3A%2F%2Fbcs.91.com%2Frbpiczy%2FWallpaper%2F2015%2F1%2F9%2Fd4b1ade76d3549bdbd94937201a32c0d-9.jpg"));
        localImages.add(new BannerBean("海贼王二","尾田荣一郎小时候是看着鸟山明的《龙珠》长大的，那部作品对他影响巨大。当时尾田最大的心愿就是能在单行本中看到更多的集数。",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=d8a388fd690f6e490e438b35f053f3c9&imgtype=0&src=http%3A%2F%2Fimg.news.d.cn%2FUE%2Fnet%2FUEUpload%2F6357315944959662505177784.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=267d33f39b69ad2c856b3d66141bf4bf&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201502%2F12%2F20150212231238_4GeHk.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=954e5a1c61b842f3302dfcc8671d8224&imgtype=0&src=http%3A%2F%2Fgss0.bdstatic.com%2FyqACvGbaBA94lNC68IqT0jB-xx1xbK%2Fztd%2Fw%3D350%3Bq%3D70%2Fsign%3D76348eecfd36afc30e0c396083229af9%2F30adcbef76094b36d9d4d8c1a9cc7cd98d109d5c.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=2ee67c5757345fd4c74c9819a8862f70&imgtype=0&src=http%3A%2F%2Fbcs.91.com%2Frbpiczy%2FWallpaper%2F2015%2F1%2F9%2Fd4b1ade76d3549bdbd94937201a32c0d-9.jpg"));
        localImages.add(new BannerBean("海贼王三","尾田荣一郎小时候是看着鸟山明的《龙珠》长大的，那部作品对他影响巨大。当时尾田最大的心愿就是能在单行本中看到更多的集数。",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=d8a388fd690f6e490e438b35f053f3c9&imgtype=0&src=http%3A%2F%2Fimg.news.d.cn%2FUE%2Fnet%2FUEUpload%2F6357315944959662505177784.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=267d33f39b69ad2c856b3d66141bf4bf&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201502%2F12%2F20150212231238_4GeHk.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=954e5a1c61b842f3302dfcc8671d8224&imgtype=0&src=http%3A%2F%2Fgss0.bdstatic.com%2FyqACvGbaBA94lNC68IqT0jB-xx1xbK%2Fztd%2Fw%3D350%3Bq%3D70%2Fsign%3D76348eecfd36afc30e0c396083229af9%2F30adcbef76094b36d9d4d8c1a9cc7cd98d109d5c.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495086599771&di=2ee67c5757345fd4c74c9819a8862f70&imgtype=0&src=http%3A%2F%2Fbcs.91.com%2Frbpiczy%2FWallpaper%2F2015%2F1%2F9%2Fd4b1ade76d3549bdbd94937201a32c0d-9.jpg"));

    }
    private void bannerInit(){
        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        },localImages)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(3000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.normol, R.mipmap.select})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //设置点击监听事件
                .setOnItemClickListener(ConvenientBannerActivity.this);
                //设置手动影响（设置了该项无法手动切换）
//                .setManualPageable(true);
    }
    @Override
    public void onItemClick(int position) {

    }

    //为了方便改写，来实现复杂布局的切换
    private class LocalImageHolderView implements Holder<BannerBean> {
        private View view;
        private ImageView img1,img2,img3,img4;
        private TextView tv_title,tv_content;
        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
            view= LayoutInflater.from(context).inflate(R.layout.item_banner,null);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean data) {
            tv_title= (TextView) view.findViewById(R.id.item_banner_title);
            tv_title.setText(data.getTitle());
            tv_content= (TextView) view.findViewById(R.id.item_banner_content);
            tv_content.setText(data.getContent());
            img1= (ImageView) view.findViewById(R.id.item_banner_img1);
            img2= (ImageView) view.findViewById(R.id.item_banner_img2);
            img3= (ImageView) view.findViewById(R.id.item_banner_img3);
            img4= (ImageView) view.findViewById(R.id.item_banner_img4);
            imageLoaderUtil.loadingImage(data.getImg1(),img1);
            imageLoaderUtil.loadingImage(data.getImg2(),img2);
            imageLoaderUtil.loadingImage(data.getImg3(),img3);
            imageLoaderUtil.loadingImage(data.getImg4(),img4);
        }
    }
}
