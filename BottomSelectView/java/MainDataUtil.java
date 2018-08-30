package com.wusy.studyroad.main.bean;

import android.util.Log;

import com.wusy.studyroad.R;
import com.wusy.studyroad.view.bottomSelect.BottomSelectBean;
import com.wusy.studyroad.me.fragment.MeFragment;
import com.wusy.studyroad.message.fragment.MessageFragment;
import com.wusy.studyroad.protal.fragment.ProtalFragment;
import com.wusy.studyroad.view.bottomSelect.BottomSelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DalaR on 2017/11/20.
 */

public class MainDataUtil {
    private static MainDataUtil util;
    private MainDataUtil(){}

    public synchronized static MainDataUtil getInstance(){
        if(util==null){
            util=new MainDataUtil();
        }
        return util;
    }

    public List<BottomSelectBean> getBottomSelectData(){
        List<BottomSelectBean> list=new ArrayList<>();

        BottomSelectBean protal=new BottomSelectBean();
        protal.setSelect(true);
        protal.setTitle("首页");
        protal.setNormalIcon(R.mipmap.main_main_normal);
        protal.setSelectIcon(R.mipmap.main_main_select);
        protal.setFragment(new ProtalFragment());
        protal.setListener(new BottomSelectView.BottomSelectViewClickListener() {
            @Override
            public void clickListener() {
                Log.i("MainDataUtil","首页-----------------");
            }
        });
        list.add(protal);

        BottomSelectBean message=new BottomSelectBean();
        message.setSelect(false);
        message.setTitle("消息");
        message.setNormalIcon(R.mipmap.main_message_normal);
        message.setSelectIcon(R.mipmap.main_message_select);
        message.setFragment(new MessageFragment());
        message.setListener(new BottomSelectView.BottomSelectViewClickListener() {
            @Override
            public void clickListener() {
                Log.i("MainDataUtil","消息-----------------");
            }
        });
        list.add(message);

        BottomSelectBean me=new BottomSelectBean();
        me.setSelect(false);
        me.setTitle("我");
        me.setNormalIcon(R.mipmap.main_me_normal);
        me.setSelectIcon(R.mipmap.main_me_select);
        me.setFragment(new MeFragment());
        me.setListener(new BottomSelectView.BottomSelectViewClickListener() {
            @Override
            public void clickListener() {
                Log.i("MainDataUtil","我-----------------");
            }
        });
        list.add(me);
        return list;
    }
}
