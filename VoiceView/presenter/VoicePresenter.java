package com.wusy.studyroad.protal.workplace.application.VoiceView.presenter;
import com.wusy.studyroad.base.BaseMVPPresenter;
import com.wusy.studyroad.base.IBaseMVPView;
import com.wusy.studyroad.protal.workplace.application.VoiceView.bean.RecoderFileBean;
import com.wusy.studyroad.protal.workplace.application.VoiceView.module.IVoiceModule;
import com.wusy.studyroad.protal.workplace.application.VoiceView.module.VoiceModule;
import com.wusy.studyroad.protal.workplace.application.VoiceView.util.AudioRecoderUtils;
import com.wusy.studyroad.protal.workplace.application.VoiceView.view.IVoiceView;

import java.io.File;

/**
 * Created by XIAO RONG on 2018/3/23.
 */

public class VoicePresenter extends BaseMVPPresenter<IVoiceView> implements IVoicePresenter
        ,AudioRecoderUtils.OnAudioStatusUpdateListener,AudioRecoderUtils.OnMediaPlayerComplationListen{
    private AudioListAdapter adapter;
    private AudioRecoderUtils utils;
    private IVoiceModule module;
    private IVoiceView view;

    public VoicePresenter(IVoiceView view) {
        this.view=view;
        utils=AudioRecoderUtils.getInstance();
        utils.setOnAudioStatusUpdateListener(this);
        utils.setMediaPlayerComplationListen(this);
        module=new VoiceModule();
        adapter=new AudioListAdapter(this);
    }

    @Override
    public void startPlayer(String path) {
        utils.playerStart(path);
    }

    @Override
    public void stopPlayer() {
        utils.playerStop();
    }

    @Override
    public void startRecroding() {
        utils.startRecord(utils.getFilePath());
    }

    @Override
    public long endRecroding() {
        long time=utils.stopRecord();
        module.getList().add(new RecoderFileBean(time,new File(utils.getCurrentFilePath())));
        view.showList(adapter);
        return time;
    }

    @Override
    public IBaseMVPView getMVPView() {
        return view;
    }

    @Override
    public IVoiceModule getModule() {
        return module;
    }

    @Override
    public boolean isPlayer() {
        return utils.isPlayer();
    }

    @Override
    public void onUpdate(double db) {
        view.updateDialog(db);
    }

    @Override
    public void onCompletion() {
        view.initListViewPlayerImg();
    }
}
