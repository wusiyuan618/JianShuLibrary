package com.wusy.studyroad.view.moduleComponents;

import java.io.Serializable;

/**
 * Created by DalaR on 2017/11/27.
 */

public class ModuleViewBean implements Serializable {
    /**
     * 模块Item的图片
     */
    private int ImageResource;
    /**
     * 模块Item的内容1 多为标题
     */
    private String title;
    /**
     * 模块Item的内容2 多为详情内容
     */
    private String content;
    /**
     * 用于判端显示的布局
     * 0=LinearLayout
     * 1=GridLayout
     */
    private int layoutNum;

    public ModuleViewBean() {
    }

    public ModuleViewBean(int imageResource, String title, String content, int layoutNum) {
        ImageResource = imageResource;
        this.title = title;
        this.content = content;
        this.layoutNum = layoutNum;
    }

    public int getImageResource() {
        return ImageResource;
    }

    public void setImageResource(int imageResource) {
        ImageResource = imageResource;
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

    public int getLayoutNum() {
        return layoutNum;
    }

    public void setLayoutNum(int layoutNum) {
        this.layoutNum = layoutNum;
    }
}
