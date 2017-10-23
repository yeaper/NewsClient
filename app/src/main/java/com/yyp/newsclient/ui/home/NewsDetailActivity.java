package com.yyp.newsclient.ui.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseActivity;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends BaseActivity implements GestureDetector.OnGestureListener {

    RecyclerView recyclerView;
    NewsAdapter mAdapter;
    protected List<News> mDatas = new ArrayList<>();

    GestureDetector gestureDetector;
    LinearLayout bottombar;
    Animation animationIn;
    Animation animationOut;
    boolean isShowing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_news_detail);
    }

    @Override
    protected void bindViews() {
        gestureDetector = new GestureDetector(this, this);
        bottombar = (LinearLayout) findViewById(R.id.bottom_root);
        // 动画
        animationIn = AnimationUtils.loadAnimation(this, R.anim.bottom_pop_in);
        animationOut = AnimationUtils.loadAnimation(this, R.anim.bottom_pop_out);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        recyclerView = initCommonRecyclerView(createAdapter(), null);
    }

    @Override
    protected void setListener() {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // 事件交给 gestureDetector 处理
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    protected BaseQuickAdapter createAdapter() {
        return mAdapter = new NewsAdapter(mDatas);
    }

    /*----------------手势监听--------------------*/

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
        float minMove = 0; // 最小滑动距离
        float minVelocity = 0; // 最小滑动速度
        float beginY = motionEvent.getY();
        float endY = motionEvent1.getY();

        if(beginY - endY > minMove && Math.abs(velocityY) > minVelocity) { // 上滑
            if (isShowing) {
                bottombar.clearAnimation();
                bottombar.setAnimation(animationOut);
                animationOut.start();
                bottombar.setVisibility(View.INVISIBLE);
                isShowing = false;
            }
        } else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity) { // 下滑
            if(!isShowing){
                bottombar.clearAnimation();
                bottombar.setAnimation(animationIn);
                animationIn.start();
                bottombar.setVisibility(View.VISIBLE);
                isShowing = true;
            }
        }


        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
