package com.wusy.studyroad.protal.workplace.application.VoiceView.presenter;

import com.wusy.studyroad.base.IBaseMVPPresenter;

/**
 * Created by XIAO RONG on 2018/3/23.
 */

public interface IVoicePresenter extends IBaseMVPPresenter {
    /**
     * 开始播放
     */
    void startPlayer(String path);
    /**
     * 停止播放
     */
    void stopPlayer();
    /**
     * 开始录音
     */
    void startRecroding();
    /**
     * 停止录音
     */
    long endRecroding();
    /**
     * 获取是否正在播放
     */
    boolean isPlayer();
}
