package com.yyp.newsclient.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.util.DateUtils;
import com.yyp.newsclient.util.ImageLoaderUtils;

/**
 * 新闻内容的头布局
 * Created by yyp on 2017/10/30.
 */
public class NewsDetailHeaderView extends FrameLayout {

    TextView header_title;
    ImageView header_avatar;
    TextView header_username;
    TextView date;

    public NewsDetailHeaderView(@NonNull Context context) {
        super(context);
    }

    public NewsDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NewsDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View view = inflate(getContext(), R.layout.news_detail_header, null);

        header_title = view.findViewById(R.id.news_detail_header_title);
        header_avatar = view.findViewById(R.id.news_detail_header_avatar);
        header_username = view.findViewById(R.id.news_detail_header_username);
        date = view.findViewById(R.id.news_detail_header_date);
        addView(view);
    }

    public void setData(News news){
        ImageLoaderUtils.loadCircleAvatar(getContext(), news.avatar, header_avatar);
        header_title.setText(news.title);
        header_username.setText(news.source);
        date.setText(DateUtils.getTimeDown(news.behot_time));
    }

    /**
     * 获取当前控件在屏幕上的坐标
     * @param location int[2]
     */
    public void getLocation(int[] location){
        getLocationOnScreen(location);
    }
}
