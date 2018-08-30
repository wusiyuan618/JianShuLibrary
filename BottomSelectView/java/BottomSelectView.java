package com.wusy.studyroad.view.bottomSelect;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wusy.studyroad.R;
import com.wusy.studyroad.util.CommonUtil;

import java.util.List;

/**
 * Created by DalaR on 2017/11/20.
 * 底部选择器，多用于首页。与BottomSelectBean一起使用。
 * 使用中需要构建BottomSelectBean的集合list,至少需要初始化标题信息，是否选中，默认图片，选中图片以及Item对应的Fragment
 * 可选择传入实现点击Listen
 * 在Activity中调用addFragmentForItem和createLayout两个方法即可
 */

public class BottomSelectView extends LinearLayout {
    //整个BottomSelectView的布局对象
    private LinearLayout ll_view;
    private BottomSelectViewClickListener clickListener;
    public BottomSelectView(Context context) {
        this(context,null);
    }
    private CommonUtil commonUtil;
    public BottomSelectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_bottomview,this);
        findview();
        init(context);
    }

    private void findview() {
        ll_view= (LinearLayout) findViewById(R.id.view_bottomview);
    }

    private void init(Context context) {
        commonUtil=CommonUtil.getInstance();
    }

    /**
     * 构建底部Item 利用java构建weight为1的LinearLaytou然后向里面添加文件和图片。
     * 并且实现点击事件，默认调用样式切换方法以及碎片切换方法。并对外提供点击接口
     * @param context
     * @param list BottomSelectBean的实体类的集合
     * @param manager 碎片管理器，在处理Fragment与各个Item构建点击链接时使用
     * @param layout fragment使用的Layout。即fragment显示的地方
     */
    public void createLayout(Context context, final List<BottomSelectBean> list, final FragmentManager manager,int layout){
        addFragmentForItem(layout,manager,list);
        for (int i=0;i<list.size();i++) {
            final BottomSelectBean bean=list.get(i);
            LinearLayout inearLayout = new LinearLayout(context);
            inearLayout.setOrientation(LinearLayout.VERTICAL);
            inearLayout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params_inearLayout = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
            params_inearLayout.weight = 1;

            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params_imageView = new LinearLayout.LayoutParams(30, 30);

            TextView textView = new TextView(context);
            textView.setTextSize(12);
            textView.setText(bean.getTitle());
            LinearLayout.LayoutParams params_textView = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (bean.isSelect()) {
                imageView.setImageResource(bean.getSelectIcon());
                textView.setTextColor(getResources().getColor(R.color.selectColor));
            } else {
                imageView.setImageResource(bean.getNormalIcon());
                textView.setTextColor(getResources().getColor(R.color.normalColor));
            }
            inearLayout.addView(imageView, params_imageView);
            inearLayout.addView(textView, params_textView);
            ll_view.addView(inearLayout, params_inearLayout);
            inearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!commonUtil.isNull(bean.getListener())) {
                        changeSelectItem(list,bean);
                        changeShowFragment(list,bean,manager);
                        bean.getListener().clickListener();

                    }
                }
            });
            bean.setImageView(imageView);
            bean.setTextView(textView);
            bean.setLinearLayout(inearLayout);
        }
    }

    /**
     * 底部Item切换改变样式的方法
     * @param list 所有底部Item的集合
     * @param selectBean 被点中的Item
     */
    private void changeSelectItem(List<BottomSelectBean> list,BottomSelectBean selectBean){
        for (int i=0;i<list.size();i++){
            if(selectBean.getTitle().equals(list.get(i).getTitle())){
                list.get(i).setSelect(true);
                list.get(i).getTextView().setTextColor(getResources().getColor(R.color.selectColor));
                list.get(i).getImageView().setImageResource(list.get(i).getSelectIcon());
            }else{
                list.get(i).setSelect(false);
                list.get(i).getTextView().setTextColor(getResources().getColor(R.color.normalColor));
                list.get(i).getImageView().setImageResource(list.get(i).getNormalIcon());
            }
        }
    }

    /**
     * 改变当前显示的Fragment的方法
     * @param list 配置数据集合
     * @param selectBean 选中的Item的实体类
     * @param manager Fragment管理器
     */
    private void changeShowFragment(List<BottomSelectBean> list,BottomSelectBean selectBean,FragmentManager manager){
        if(manager==null)return;
        FragmentTransaction transaction=manager.beginTransaction();
        if(selectBean.getFragment()!=null){
            for (int i=0;i<list.size();i++){
                if(selectBean.getTitle().equals(list.get(i).getTitle())){
                    if(list.get(i).getFragment()!=null) transaction.show(list.get(i).getFragment());
                }else{
                    if(list.get(i).getFragment()!=null) transaction.hide(list.get(i).getFragment());
                }
            }
        }else{
            for (int i=0;i<list.size();i++){
                if(list.get(i).getFragment()!=null) transaction.hide(list.get(i).getFragment());
            }
        }
        transaction.commit();
    }

    /**
     * 为各个Item添加Fragment的方法。
     * @param layout
     * @param manager
     * @param list
     */
    private void addFragmentForItem(int layout, FragmentManager manager,List<BottomSelectBean> list){
        FragmentTransaction transaction=manager.beginTransaction();
        for (int i=0;i<list.size();i++){
            if(list.get(i).getFragment()!=null) {
                transaction.add(layout, list.get(i).getFragment());
                if (list.get(i).isSelect()) {
                    transaction.show(list.get(i).getFragment());
                } else {
                    transaction.hide(list.get(i).getFragment());
                }
            }
        }
        transaction.commit();
    }


    /**
     * 对外提供的Item的点击事件实现接口
     */
    public interface BottomSelectViewClickListener{
        void clickListener();
    }
    public BottomSelectViewClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(BottomSelectViewClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
