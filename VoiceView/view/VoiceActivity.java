package com.wusy.studyroad.protal.workplace.application.VoiceView.view;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wusy.studyroad.R;
import com.wusy.studyroad.base.BaseMVPActivity;
import com.wusy.studyroad.protal.workplace.application.VoiceView.presenter.AudioListAdapter;
import com.wusy.studyroad.protal.workplace.application.VoiceView.presenter.VoicePresenter;
import com.wusy.studyroad.protal.workplace.application.VoiceView.util.AudioRecoderDialog;
import com.wusy.studyroad.view.TitleView;


/**
 * Created by XIAO RONG on 2018/3/23.
 */
public class VoiceActivity extends BaseMVPActivity<IVoiceView,VoicePresenter>
        implements IVoiceView,View.OnTouchListener {
    private AudioRecoderDialog recoderDialog;
    private TitleView titleView;
    private ListView listView;
    private TextView button;
    private long downTime;

    @Override
    public void updateDialog(double db) {
        if(recoderDialog!=null){
            recoderDialog.setLevel((int)db);
            recoderDialog.setTime(System.currentTimeMillis()-downTime);
        }
    }

    @Override
    public boolean showDialog(View view) {
        downTime = System.currentTimeMillis();
        recoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
        button.setBackgroundResource(R.drawable.shape_recoder_btn_recoding);
        return true;
    }

    @Override
    public boolean closeDialog() {
        recoderDialog.dismiss();
        recoderDialog.setTime(0);
        button.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
        return true;
    }

    @Override
    public void initListViewPlayerImg() {
        for (int i=0;i<listView.getChildCount();i++){
            ((ImageView)listView.getChildAt(i).findViewById(R.id.item_recoder_player))
                    .setImageResource(R.mipmap.player);
        }
    }

    @Override
    public void showList(AudioListAdapter adapter) {
        if(listView.getAdapter()==null){
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    protected VoicePresenter createPresenter() {
        return new VoicePresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recoder;
    }

    @Override
    public void findView() {
        button = (TextView) findViewById(android.R.id.button1);
        listView = (ListView) findViewById(android.R.id.list);
        titleView= (TitleView) findViewById(R.id.titleView);
    }

    @Override
    public void init() {
        button.setOnTouchListener(this);
        recoderDialog = new AudioRecoderDialog(this);
        recoderDialog.setShowAlpha(0.98f);
        titleView.setTitle("语音录制")
                .showBackButton(true,this)
                .build();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getmPresenter().startRecroding();
                showDialog(v);
                return true;
            case MotionEvent.ACTION_UP:
                long time=getmPresenter().endRecroding();
                if(time==0){
                    Toast.makeText(this,"录制时间太短",Toast.LENGTH_SHORT).show();
                    return false;
                }
                closeDialog();
                return true;
        }
        return false;
    }
}
