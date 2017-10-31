package com.yyp.newsclient.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ImageLoaderUtils {

    public static RequestManager getImageLoader(Context ctx) {
        return Glide.with(ctx);
    }

    /**
     * 加载普通图片
     * @param ctx
     * @param url
     * @param view
     */
    public static void loadCommonImage(Context ctx, String url, ImageView view) {
        getImageLoader(ctx).load(url)
                .thumbnail(0.5f)
                .into(view);
    }

    /**
     * 加载大图
     * @param ctx
     * @param url
     * @param view
     */
    public static void loadBigImage(Context ctx, String url, ImageView view) {
        getImageLoader(ctx).load(url)
                .thumbnail(0.5f)
                .into(view);
    }

    /**
     * 加载圆形头像
     * @param ctx
     * @param url
     * @param view
     */
    public static void loadCircleAvatar(Context ctx, String url, ImageView view) {
        getImageLoader(ctx).load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(view);
    }

    /**
     * 加载高斯模糊后的图片
     * @param ctx
     * @param url
     * @param view
     */
    public static void loadBlurImage(Context ctx, String url, ImageView view) {
        getImageLoader(ctx).load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                .into(view);
    }
}
