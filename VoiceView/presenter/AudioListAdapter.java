package com.wusy.studyroad.protal.workplace.application.VoiceView.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wusy.studyroad.R;
import com.wusy.studyroad.protal.workplace.application.VoiceView.bean.RecoderFileBean;

import java.util.ArrayList;


/**
 * Created by MarioStudio on 2016/5/12.
 */
public class AudioListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<RecoderFileBean> list;
    private IVoicePresenter presenter;
    public AudioListAdapter(IVoicePresenter presenter) {
        this.presenter=presenter;
        mContext=presenter.getMVPView().getContext();
        mInflater = LayoutInflater.from(mContext);
        this.list= (ArrayList<RecoderFileBean>) presenter.getModule().getList();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_recoder_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_name=(TextView) convertView.findViewById(R.id.item_recoder_name);
            viewHolder.img_delete= (ImageView) convertView.findViewById(R.id.item_recoder_delete);
            viewHolder.img_player=(ImageView)convertView.findViewById(R.id.item_recoder_player);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(list.get(position).getFile().getName());
        viewHolder.img_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(presenter.isPlayer()){
                    viewHolder.img_player.setImageResource(R.mipmap.player);
                    presenter.stopPlayer();
                }else{
                    viewHolder.img_player.setImageResource(R.mipmap.stop);
                    presenter.startPlayer(list.get(position).getFile().getAbsolutePath());
                }
            }
        });
        viewHolder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioListAdapter.this.list.remove(position);
                AudioListAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView img_delete,img_player;
        TextView tv_name;
    }
    public ArrayList<RecoderFileBean> getList() {
        return list;
    }

    public void setList(ArrayList<RecoderFileBean> list) {
        this.list = list;
    }
}
