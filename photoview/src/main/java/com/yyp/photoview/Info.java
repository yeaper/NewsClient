package com.yyp.photoview;

import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageView;

public class Info {

    // 内部图片在整个手机界面的位置
    public RectF mRect = new RectF();

    // 控件在窗口的位置，包括窗口外
    public RectF mImgRect = new RectF();

    // 控件在窗口的位置，不包括窗口外
    public RectF mWidgetRect = new RectF();

    public RectF mBaseRect = new RectF();

    public PointF mScreenCenter = new PointF();

    public float mScale; // 缩放值

    public float mDegrees; // 角度

    public ImageView.ScaleType mScaleType;

    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }
}