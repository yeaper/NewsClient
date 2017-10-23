package com.yyp.newsclient.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yyp.newsclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 今日头条的数据加载控件
 */
public class LoadingFlashView extends FrameLayout {

    private ImageView mLoad1;
    private ImageView mLoad2;
    private ImageView mLoad3;
    private ImageView mLoad4;
    private AnimatorSet mAnimatorSet;

    public LoadingFlashView(Context context) {
        this(context, null);
    }

    public LoadingFlashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingFlashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.loading_flash_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoad1 = findViewById(R.id.load1);
        mLoad2 = findViewById(R.id.load2);
        mLoad3 = findViewById(R.id.load3);
        mLoad4 = findViewById(R.id.load4);
    }

    /**
     * 显示加载控件
     */
    public void showLoading() {
        if (getVisibility() != View.VISIBLE)
            return;
        if (mAnimatorSet == null)
            initAnimation();
        if (mAnimatorSet.isRunning() || mAnimatorSet.isStarted())
            return;
        // 开启动画
        mAnimatorSet.start();
    }

    /**
     * 隐藏加载控件
     */
    public void hideLoading() {
        if (mAnimatorSet == null)
            return;
        if ((!mAnimatorSet.isRunning()) && (!mAnimatorSet.isStarted()))
            return;
        // 结束动画
        mAnimatorSet.removeAllListeners();
        mAnimatorSet.cancel();
        mAnimatorSet.end();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        mAnimatorSet = new AnimatorSet();

        // 为 今日头条 四个字分别设置动画
        List<ImageView> imageViewList = Arrays.asList(mLoad1, mLoad2, mLoad3, mLoad4);
        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            // 生成透明度动画
            ObjectAnimator loadAnimator = ObjectAnimator.ofFloat(imageViewList.get(i), "alpha", new float[]{1.0F, 0.5F}).setDuration(500L);
            // 设置开始的时间
            loadAnimator.setStartDelay(100 * i);
            // 设置重复模式：逆向重复
            loadAnimator.setRepeatMode(ObjectAnimator.REVERSE);
            // 无限循环
            loadAnimator.setRepeatCount(-1);
            animatorList.add(loadAnimator);
        }

        // 同时进行动画
        mAnimatorSet.playTogether(animatorList);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != VISIBLE)
            hideLoading();
    }
}
