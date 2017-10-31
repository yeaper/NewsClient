package com.yyp.newsclient.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * 字体处理工具
 * Created by yyp on 2017/10/24.
 */
public class MTextUtils {

    /**
     * 设置字体大小
     * @param txt 字符串
     * @param size 要设置的大小
     * @param startPos 起始位置
     * @param endPos 结束位置
     * @return
     */
    public static SpannableString setTextSize(String txt, float size, int startPos, int endPos){
        SpannableString spannableString = new SpannableString(txt);
        spannableString.setSpan(new RelativeSizeSpan(size), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置字体颜色
     * @param txt 字符串
     * @param color 要设置的颜色
     * @param startPos 起始位置
     * @param endPos 结束位置
     * @return
     */
    public static SpannableString setTextForegroundColor(String txt, int color, int startPos, int endPos){
        SpannableString spannableString = new SpannableString(txt);
        spannableString.setSpan(new ForegroundColorSpan(color), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
