package com.example.internet;

import java.io.Serializable;

/**
 * Created by 达拉然的风 on 2017/5/18.
 */

public class BannerBean implements Serializable {
    private String title;
    private String content;
    private String img1;
    private String img2;
    private String img3;
    private String img4;

    public BannerBean(String title, String content, String img1, String img2, String img3, String img4) {
        this.title = title;
        this.content = content;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }
}
