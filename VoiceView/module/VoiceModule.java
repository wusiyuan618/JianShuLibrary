package com.wusy.studyroad.protal.workplace.application.VoiceView.module;

import com.wusy.studyroad.protal.workplace.application.VoiceView.bean.RecoderFileBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIAO RONG on 2018/3/26.
 */

public class VoiceModule implements IVoiceModule {
    private List<RecoderFileBean> list;
    @Override
    public List<RecoderFileBean> getList() {
        if(list==null) list=new ArrayList<>();
        return list;
    }

    @Override
    public void requestData() {

    }
}
