package com.wusy.studyroad.protal.workplace.application.VoiceView.view;

import android.view.View;

import com.wusy.studyroad.base.IBaseMVPView;

/**
 * Created by XIAO RONG on 2018/3/23.
 */

public interface IVoiceView extends IBaseMVPView {
    /**
     * 更新录音View的状态
     */
    void updateDialog(double db);
    /**
     * 显示录音动画
     */
    boolean showDialog(View view);
    /**
     * 关闭录音动画
     */
    boolean closeDialog();
    /**
     * 初始化ListView的播放图标
     */
    void initListViewPlayerImg();

}
