package com.wusy.studyroad.view.moduleComponents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wusy.studyroad.R;

import java.util.List;

/**
 * Created by DalaR on 2017/11/27.
 */

public class ModuleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ModuleViewBean> list;
    private Context mC;
    public OnItemClickListen onItemClickListen;
    public final int LINEARLAYOUT=0,GRIDLAYOUT=1;

    public ModuleViewAdapter (Context context,List<ModuleViewBean> list){
        this.list=list;
        this.mC=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==GRIDLAYOUT) {
            ModuleViewHolder holder = new ModuleViewHolder(LayoutInflater.from(
                    mC).inflate(R.layout.moduleview_item_grid, parent, false));
            return holder;
        }else{
            ModuleViewHolder holder = new ModuleViewHolder(LayoutInflater.from(
                    mC).inflate(R.layout.moduleview_item_linear, parent, false));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ModuleViewHolder){
            ModuleViewHolder moduleViewHolder= (ModuleViewHolder) holder;
            moduleViewHolder.tv_title.setText(list.get(position).getTitle());
            moduleViewHolder.tv_content.setText(list.get(position).getContent());
            moduleViewHolder.img.setImageResource(list.get(position).getImageResource());
            moduleViewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListen!=null){
                        onItemClickListen.onItemClick(v,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getLayoutNum();
    }

    public interface OnItemClickListen{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        this.onItemClickListen = onItemClickListen;
    }
}
