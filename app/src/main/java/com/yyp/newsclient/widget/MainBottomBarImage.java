package com.yyp.newsclient.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 自定义底部导航栏图片控件
 * Created by yyp on 2017/10/10.
 */
public class MainBottomBarImage extends FrameLayout {

    private View childView1, childView2;
    private float childView1X, childView1Y, childView2X, childView2Y;
    private float centerX, centerY;
    private int normal1, click1;
    private int normal2, click2;

    public MainBottomBarImage(@NonNull Context context) {
        super(context);
    }

    public MainBottomBarImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MainBottomBarImage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 添加两个图片
     */
    private void initView() {
        childView1 = new ImageView(getContext());
        childView2 = new ImageView(getContext());
        addView(childView1);
        addView(childView2);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        centerX = getHeight() / 5;
        centerY = getWidth() / 5;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        childView1 = getChildAt(0);
        childView2 = getChildAt(1);
        childView1X = childView1.getX();
        childView1Y = childView1.getY();
        childView2X = childView2.getX();
        childView2Y = childView2.getY();
    }

    /**
     * 设置图片
     * @param normal1 外部正常图片
     * @param click1 外部选中图片
     * @param normal2 内部正常图片
     * @param click2 内部选中图片
     */
    public void setImages(@DrawableRes int normal1, @DrawableRes int click1, @DrawableRes int normal2, @DrawableRes int click2) {
        this.normal1 = normal1;
        this.click1 = click1;
        this.normal2 = normal2;
        this.click2 = click2;
        setImageSelected(false);
    }

    /**
     * 根据点击状态，设置对应图片
     */
    public void setImageSelected(boolean hasClick){
        if (hasClick) {
            if (click1 != 0)
                childView1.setBackgroundResource(click1);
            if (click2 != 0)
                childView2.setBackgroundResource(click2);
        } else {
            if (normal1 != 0)
                childView1.setBackgroundResource(normal1);
            if (normal2 != 0)
                childView2.setBackgroundResource(normal2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                changeWhenMove(x, y);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                restorePosition();
                break;
            }
        }
        return true;
    }

    /**
     * 移动时候，改变位置
     * @param x 横坐标相对位置
     * @param y 纵坐标相对位置
     */
    private void changeWhenMove(float x, float y) {
        //这个值可以调节来达到最理想的效果
        if (y + centerY < -12 * centerY) {
            y = -12 * centerY - centerY;
        } else if (y - centerY > 12 * centerY) {
            y = 12 * centerY + centerY;
        }
        if (x + centerX < -12 * centerX) {
            x = -12 * centerX - centerX;
        } else if (x - centerX > 12 * centerX) {
            x = 12 * centerX + centerX;
        }
        childView1.setX(childView1X + (x - centerX) / 50); // 外部变化幅度小一点
        childView1.setY(childView1Y + (y - centerY) / 50);
        if (childView2 != null) {
            childView2.setX(childView2X + (x - centerX) / 21); // 内部变化幅度可以大一点
            childView2.setY(childView2Y + (y - centerY) / 15);
        }
    }

    /**
     * 还原控件位置
     */
    private void restorePosition() {
        childView1.setX(childView1X);
        childView1.setY(childView1Y);
        childView2.setX(childView2X);
        childView2.setY(childView2Y);
    }
}
