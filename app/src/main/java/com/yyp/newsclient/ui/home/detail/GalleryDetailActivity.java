package com.yyp.newsclient.ui.home.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseActivity;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.ui.adapter.GalleryPagerAdapter;
import com.yyp.newsclient.util.ImageLoaderUtils;
import com.yyp.newsclient.util.MTextUtils;
import com.yyp.newsclient.widget.SwipeBackLayout;
import com.yyp.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class GalleryDetailActivity extends BaseActivity implements View.OnClickListener{

    private News news;
    ImageView back;
    ImageView favor;

    ViewPager vp;
    List<View> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_gallery_detail);
        if(getIntent() != null){
            news = (News) getIntent().getSerializableExtra("news");
        }
    }

    @Override
    protected void bindViews() {
        back = (ImageView) findViewById(R.id.back_btn);
        favor = (ImageView) findViewById(R.id.action_favor);
        vp = (ViewPager) findViewById(R.id.gallery_detail_vp);
        mDatas = new ArrayList<>();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 使用
        getSwipeBackLayout().setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        setEnableSwipe(true);

        vp.setOffscreenPageLimit(news.image_list.size());
        vp.setAdapter(new GalleryPagerAdapter(getDatas()));
    }

    @Override
    protected void setListener() {
        back.setOnClickListener(this);
        favor.setOnClickListener(this);
    }

    /**
     * 获取图集数据View集合
     * @return
     */
    private List<View> getDatas(){
        mDatas.clear();
        for(int i=0;i<news.image_list.size();i++){
            View item = LayoutInflater.from(this).inflate(R.layout.gallery_item, null);
            PhotoView img = item.findViewById(R.id.gallery_img);
            TextView index = item.findViewById(R.id.gallery_index);
            TextView content = item.findViewById(R.id.gallery_content);
            // 加载图片
            ImageLoaderUtils.loadCommonImage(this, news.image_list.get(i).url, img);
            // 开启图片旋转、缩放
            img.enable();
            img.enableRotate();
            // 设置最大缩放倍数
            img.setMaxScale(2.0f);

            index.setText(MTextUtils.setTextSize((i+1)+"/"+news.image_list.size(),
                    1.4f, 0, 1));
            content.setText(news.image_list.get(i).description);

            mDatas.add(item);
        }

        return mDatas;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.action_favor:
                if(favor.isSelected()){
                    favor.setSelected(false);
                }else{
                    favor.setSelected(true);
                }
                break;
        }
    }
}
