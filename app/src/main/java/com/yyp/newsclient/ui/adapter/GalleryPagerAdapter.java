package com.yyp.newsclient.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 图集适配器
 * Created by yyp on 2017/10/23.
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private List<View> mDatas;

    public GalleryPagerAdapter(List<View> imageUrls) {
        this.mDatas = imageUrls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mDatas.get(position));
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDatas.get(position));
    }
}