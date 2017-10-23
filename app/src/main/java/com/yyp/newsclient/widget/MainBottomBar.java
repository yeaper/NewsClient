package com.yyp.newsclient.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.interfaces.OnBottomBarClickListener;
import com.yyp.newsclient.theme.ColorUiInterface;
import com.yyp.newsclient.theme.util.ViewAttributeUtil;

/**
 * 首页底部导航栏控件
 * Created by yyp on 2017/10/10.
 */
public class MainBottomBar extends LinearLayout implements ColorUiInterface {

    private Context ctx;
    private OnBottomBarClickListener onBottomBarClickListener;

    private int attr_background = -1;

    public MainBottomBar(Context context) {
        super(context);
    }

    public MainBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;

        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public MainBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public void addItem(@DrawableRes int normal1, @DrawableRes int click1, @DrawableRes int normal2, @DrawableRes int click2, String text){
        View item = LayoutInflater.from(ctx).inflate(R.layout.item_main_tab, this, false);
        final MainBottomBarImage image = item.findViewById(R.id.main_tab_img);
        final TextView txt = item.findViewById(R.id.main_tab_text);
        image.setImages(normal1, click1, normal2, click2);
        txt.setText(text);

        addView(item);
    }

    /**
     * 重置所有图片、文字状态
     */
    private void resetSelectItem(){
        for(int i=0;i<getChildCount();i++){
            View item = getChildAt(i);
            MainBottomBarImage image = item.findViewById(R.id.main_tab_img);
            TextView txt = item.findViewById(R.id.main_tab_text);
            image.setImageSelected(false);
            txt.setSelected(false);
        }
    }

    public void setOnBottomBarClickListener(final OnBottomBarClickListener onBottomBarClickListener) {
        this.onBottomBarClickListener = onBottomBarClickListener;
        setChildClick();
    }

    /**
     * 点击子项目时的处理
     */
    private void setChildClick(){
        for(int i=0;i<getChildCount();i++){
            final int finalI = i;
            final MainBottomBarImage image = getChildAt(i).findViewById(R.id.main_tab_img);
            final TextView txt = getChildAt(i).findViewById(R.id.main_tab_text);
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetSelectItem();
                    image.setImageSelected(true);
                    txt.setSelected(true);
                    if(onBottomBarClickListener != null){
                        onBottomBarClickListener.onBottomBarClick(finalI);
                    }
                }
            });
        }
    }

    //--------------------------动态换主题----------------------
    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_background!=-1)
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
    }
}
