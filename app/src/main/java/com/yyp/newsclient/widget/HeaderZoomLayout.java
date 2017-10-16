package com.yyp.newsclient.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class HeaderZoomLayout extends ScrollView {
    private View mHeaderView;
    private int mHeaderWidth;
    private int mHeaderHeight;

    // 是否正在下拉
    private boolean mIsPulling;
    // 记录顶部位置
    private int mLastY;
    // 滑动放大系数，系数越大，滑动时放大程度越大
    private float mScaleRatio = 0.7f;
    // 最大的放大倍数
    private float mScaleTimes = 2f;
    // 回弹时间系数，系数越小，回弹越快
    private float mReplyRatio = 0.5f;

    public HeaderZoomLayout(Context context) {
        this(context, null);
    }

    public HeaderZoomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderZoomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View child = getChildAt(0);
        if (child != null && child instanceof ViewGroup) {
            // 获取默认第一个子View
            // 注：需搭配布局层级使用，即只缩放背景ImageView
            mHeaderView = ((ViewGroup) ((ViewGroup) child).getChildAt(0)).getChildAt(0);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 布局宽高每改变一次，就获取一次控件的测量宽高
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHeaderView == null)
            return super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!mIsPulling) {
                    //第一次下拉
                    if (getScrollY() == 0) {
                        //在顶部的时候，记录顶部位置
                        mLastY = (int) ev.getY();
                    } else {
                        break;
                    }
                }

                if(ev.getY()-mLastY<0) //上滑
                    return super.onTouchEvent(ev);
                mIsPulling = true;
                setZoom((int) ((ev.getY() - mLastY) * mScaleRatio));
                return true;
            case MotionEvent.ACTION_UP:
                mIsPulling = false;
                replyView();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 放大、缩小view
     */
    private void setZoom(float s) {
        float scaleTimes = (mHeaderWidth + s) / mHeaderWidth;
        // 如超过最大放大倍数，直接返回
        if (scaleTimes > mScaleTimes) return;
        // 向右边缩放
        ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
        layoutParams.width = (int) (mHeaderWidth * scaleTimes);
        layoutParams.height = (int) (mHeaderHeight * scaleTimes);
        // 向左边缩放
        ((MarginLayoutParams) layoutParams).setMargins((int)-s / 2, 0, 0, 0);
        mHeaderView.setLayoutParams(layoutParams);
    }

    /**
     * 回弹
     */
    private void replyView() {
        final float distance = mHeaderView.getMeasuredWidth() - mHeaderWidth; // 负值
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }
}
