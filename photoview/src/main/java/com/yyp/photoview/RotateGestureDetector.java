package com.yyp.photoview;

import android.view.MotionEvent;

public class RotateGestureDetector {

    private static final int MAX_DEGREES_STEP = 120;

    private OnRotateListener mListener;

    private float mPrevSlope; // 之前的斜度值
    private float mCurrSlope; // 当前的斜度值

    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public RotateGestureDetector(OnRotateListener l) {
        mListener = l;
    }

    public void onTouchEvent(MotionEvent event) {

        final int Action = event.getActionMasked();

        switch (Action) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP: // 有一个点离开屏幕时调用
                if (event.getPointerCount() == 2) mPrevSlope = calculateSlope(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) { // 超过一个点在移动时调用
                    mCurrSlope = calculateSlope(event);

                    double currDegrees = Math.toDegrees(Math.atan(mCurrSlope));
                    double prevDegrees = Math.toDegrees(Math.atan(mPrevSlope));

                    // 斜度之差
                    double deltaSlope = currDegrees - prevDegrees;
                    // 如果差值小于等于120度，进行旋转回调
                    if (Math.abs(deltaSlope) <= MAX_DEGREES_STEP) {
                        mListener.onRotate((float) deltaSlope, (x2 + x1) / 2, (y2 + y1) / 2);
                    }
                    mPrevSlope = mCurrSlope;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 计算斜率
     * @param event
     * @return
     */
    private float calculateSlope(MotionEvent event) {
        x1 = event.getX(0);
        y1 = event.getY(0);
        x2 = event.getX(1);
        y2 = event.getY(1);
        return (y2 - y1) / (x2 - x1);
    }

    public interface OnRotateListener {
        /**
         * 旋转的回调
         * @param degrees 旋转过的角度
         * @param focusX 焦点X坐标
         * @param focusY 焦点Y坐标
         */
        void onRotate(float degrees, float focusX, float focusY);
    }
}