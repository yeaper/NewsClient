package com.yyp.newsclient.widget.colortrackview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yyp.newsclient.R;


public class ColorTrackView extends View {
    // 渐变的起始位置
    private int mTextStartX;
    private int mTextStartY;

    // 颜色渐变的方向
    public enum Direction {
        LEFT, RIGHT, TOP, BOTTOM;
    }

    private int mDirection = DIRECTION_LEFT; // 默认从左边开始渐变

    private static final int DIRECTION_LEFT = 0;
    private static final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_TOP = 2;
    private static final int DIRECTION_BOTTOM = 3;

    public void setDirection(int direction) {
        mDirection = direction;
    }

    private String mText = "";
    private Paint mPaint;
    private int mTextSize = sp2px(30);

    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;

    private Rect mTextBound = new Rect();
    private int mTextWidth;
    private int mTextHeight;

    private float mProgress; // 渐变的进度

    public ColorTrackView(Context context) {
        this(context, null);
    }

    public ColorTrackView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 去锯齿的画笔

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ColorTrackView);
        mText = ta.getString(R.styleable.ColorTrackView_ctvText);
        mTextSize = ta.getDimensionPixelSize(
                R.styleable.ColorTrackView_ctvText_size, mTextSize);
        mTextOriginColor = ta.getColor(
                R.styleable.ColorTrackView_ctvText_origin_color, mTextOriginColor);
        mTextChangeColor = ta.getColor(
                R.styleable.ColorTrackView_ctvText_change_color, mTextChangeColor);
        mProgress = ta.getFloat(R.styleable.ColorTrackView_ctvProgress, 0);

        mDirection = ta.getInt(
                R.styleable.ColorTrackView_ctvDirection, mDirection);

        ta.recycle();

        mPaint.setTextSize(mTextSize);

    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureText();

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mTextStartX = getMeasuredWidth() / 2 - mTextWidth / 2; // 实际文字起始x位置
        mTextStartY = getMeasuredHeight() / 2 - mTextHeight / 2; // 实际文字起始y位置
    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec); // 获取模式
        int val = MeasureSpec.getSize(measureSpec); // 获取值
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY: // 精确测量
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextBound.height();
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextWidth;
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    public int getTextWidth() {
        return mTextWidth;
    }

    /**
     * 测量文字的宽高
     */
    private void measureText() {
        // 文字宽度
        mTextWidth = (int) mPaint.measureText(mText);
        // 文字高度
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        mTextHeight = mTextBound.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int r = (int) (mProgress * mTextWidth + mTextStartX);
        int t = (int) (mProgress * mTextHeight + mTextStartY);

        if (mDirection == DIRECTION_LEFT) {
            drawChangeLeft(canvas, r);
            drawOriginLeft(canvas, r);
        } else if (mDirection == DIRECTION_RIGHT) {
            drawOriginRight(canvas, r);
            drawChangeRight(canvas, r);
        } else if (mDirection == DIRECTION_TOP) {
            drawOriginTop(canvas, t);
            drawChangeTop(canvas, t);
        } else {
            drawOriginBottom(canvas, t);
            drawChangeBottom(canvas, t);
        }

    }

    private boolean debug = false;

    /**
     * 横向绘制
     * @param canvas
     * @param color
     * @param startX
     * @param endX
     */
    private void drawText_h(Canvas canvas, int color, int startX, int endX) {
        mPaint.setColor(color);
        if (debug) {
            mPaint.setStyle(Style.STROKE);
            canvas.drawRect(startX, 0, endX, getMeasuredHeight(), mPaint);
        }
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        // 设置剪裁区，即只对剪裁区显示、操作
        canvas.clipRect(startX, 0, endX, getMeasuredHeight()); // left, top, right, bottom
        canvas.drawText(mText, mTextStartX,
                getMeasuredHeight() / 2
                        - ((mPaint.descent() + mPaint.ascent()) / 2), mPaint);
        canvas.restore();
    }

    /**
     * 纵向绘制
     * @param canvas
     * @param color
     * @param startY
     * @param endY
     */
    private void drawText_v(Canvas canvas, int color, int startY, int endY) {
        mPaint.setColor(color);
        if (debug) {
            mPaint.setStyle(Style.STROKE);
            canvas.drawRect(0, startY, getMeasuredWidth(), endY, mPaint);
        }

        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(0, startY, getMeasuredWidth(), endY);// left, top,
        canvas.drawText(mText, mTextStartX,
                getMeasuredHeight() / 2
                        - ((mPaint.descent() + mPaint.ascent()) / 2), mPaint);
        canvas.restore();
    }

    /**
     * 从左边开始变选中色
     * @param canvas
     * @param r
     */
    private void drawChangeLeft(Canvas canvas, int r) {
        drawText_h(canvas, mTextChangeColor, mTextStartX,
                (int) (mTextStartX + mProgress * mTextWidth));
    }

    /**
     * 从左边开始变未选中色
     * @param canvas
     * @param r
     */
    private void drawOriginLeft(Canvas canvas, int r) {
        drawText_h(canvas, mTextOriginColor, (int) (mTextStartX + mProgress
                * mTextWidth), mTextStartX + mTextWidth);
    }

    private void drawChangeRight(Canvas canvas, int r) {
        drawText_h(canvas, mTextChangeColor,
                (int) (mTextStartX + (1 - mProgress) * mTextWidth), mTextStartX
                        + mTextWidth);
    }

    private void drawOriginRight(Canvas canvas, int r) {
        drawText_h(canvas, mTextOriginColor, mTextStartX,
                (int) (mTextStartX + (1 - mProgress) * mTextWidth));
    }

    private void drawChangeTop(Canvas canvas, int r) {
        drawText_v(canvas, mTextChangeColor, mTextStartY,
                (int) (mTextStartY + mProgress * mTextHeight));
    }

    private void drawOriginTop(Canvas canvas, int r) {
        drawText_v(canvas, mTextOriginColor, (int) (mTextStartY + mProgress
                * mTextHeight), mTextStartY + mTextHeight);
    }

    private void drawChangeBottom(Canvas canvas, int t) {
        drawText_v(canvas, mTextChangeColor,
                (int) (mTextStartY + (1 - mProgress) * mTextHeight),
                mTextStartY + mTextHeight);
    }

    private void drawOriginBottom(Canvas canvas, int t) {
        drawText_v(canvas, mTextOriginColor, mTextStartY,
                (int) (mTextStartY + (1 - mProgress) * mTextHeight));
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        mPaint.setTextSize(mTextSize);
        requestLayout();
        invalidate();
    }

    public void setText(String text) {
        this.mText = text;
        requestLayout();
        invalidate();
    }

    public int getTextOriginColor() {
        return mTextOriginColor;
    }

    public void setTextOriginColor(int mTextOriginColor) {
        this.mTextOriginColor = mTextOriginColor;
        invalidate();
    }

    public int getTextChangeColor() {
        return mTextChangeColor;
    }

    public void setTextChangeColor(int mTextChangeColor) {
        this.mTextChangeColor = mTextChangeColor;
        invalidate();
    }

    private int sp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getResources().getDisplayMetrics());
    }

    private static final String KEY_STATE_PROGRESS = "key_progress";
    private static final String KEY_DEFAULT_STATE = "key_default_state";


    /*--------------保存、恢复绘制的进度值--------------*/

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putFloat(KEY_STATE_PROGRESS, mProgress);
        bundle.putParcelable(KEY_DEFAULT_STATE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mProgress = bundle.getFloat(KEY_STATE_PROGRESS);
            super.onRestoreInstanceState(bundle
                    .getParcelable(KEY_DEFAULT_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
