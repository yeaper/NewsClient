package com.yyp.newsclient.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.model.News;

/**
 * 视频内容的头布局
 * Created by yyp on 2017/10/30.
 */
public class VideoDetailHeaderView extends FrameLayout {

    TextView header_title;
    TextView header_play_times;
    TextView header_date;

    public VideoDetailHeaderView(@NonNull Context context) {
        super(context);
        initView();
    }

    public VideoDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View view = inflate(getContext(), R.layout.video_detail_header, null);

        header_title = view.findViewById(R.id.video_detail_header_title);
        header_play_times = view.findViewById(R.id.video_detail_header_play_times);
        header_date = view.findViewById(R.id.video_detail_header_date);
        addView(view);
    }

    public void setData(News news){
        header_title.setText(news.title);
        header_play_times.setText("1.3万次播放");
        header_date.setText("2017年11月4日发布");
    }

}
