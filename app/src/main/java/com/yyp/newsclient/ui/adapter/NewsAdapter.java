package com.yyp.newsclient.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyp.newsclient.R;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.theme.util.ColorUiUtil;
import com.yyp.newsclient.util.ConstanceValue;
import com.yyp.newsclient.util.DateUtils;
import com.yyp.newsclient.util.ImageLoaderUtils;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<News> {

    private Context ctx;

    public NewsAdapter(Context context,List<News> data) {
        super(R.layout.item_news, data);
        this.ctx = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {
        //防止复用View没有改变主题，重新设置
        ColorUiUtil.changeTheme(baseViewHolder.convertView, mContext.getTheme());
        // 隐藏所有视图
        setGone(baseViewHolder);

        // 根据类型适配新闻
        switch (news.article_genre) {
            case ConstanceValue.ARTICLE_GENRE_ARTICLE: // 文章类型
                if (news.image_list == null || news.image_list.size() == 0) {
                    if (!TextUtils.isEmpty(news.image_url)) {
                        //单图片文章
                        ImageLoaderUtils.loadCommonImage(ctx, news.image_url, (ImageView) baseViewHolder.getView(R.id.ivRightImg1));
                        baseViewHolder.setVisible(R.id.rlRightImg, true)
                                .setVisible(R.id.viewFill, true);
                    }
                } else {
                    //3张图片
                    baseViewHolder.setVisible(R.id.llCenterImg, true);
                    try {
                        ImageLoaderUtils.loadCommonImage(ctx, news.image_list.get(0).url, (ImageView) baseViewHolder.getView(R.id.ivCenterImg1));
                        ImageLoaderUtils.loadCommonImage(ctx, news.image_list.get(1).url, (ImageView) baseViewHolder.getView(R.id.ivCenterImg2));
                        ImageLoaderUtils.loadCommonImage(ctx, news.image_list.get(2).url, (ImageView) baseViewHolder.getView(R.id.ivCenterImg3));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ConstanceValue.ARTICLE_GENRE_GALLERY: // 画廊类型
                if (news.image_list == null || news.image_list.size() == 0) {
                    ImageLoaderUtils.loadCommonImage(ctx, news.image_url, (ImageView) baseViewHolder.getView(R.id.ivRightImg1));
                    baseViewHolder.setVisible(R.id.rlRightImg, true)
                            .setVisible(R.id.viewFill, true);
                } else {
                    ImageLoaderUtils.loadCommonImage(ctx, news.image_list.get(0).url, (ImageView) baseViewHolder.getView(R.id.ivBigImg));
                    baseViewHolder.setVisible(R.id.rlBigImg, true)
                            .setText(R.id.tvImgCount, news.image_list.size() + "图");
                }
                break;
            case ConstanceValue.ARTICLE_GENRE_VIDEO: // 视频类型
                ImageLoaderUtils.loadCommonImage(ctx, news.image_url, (ImageView) baseViewHolder.getView(R.id.ivRightImg1));
                baseViewHolder.setVisible(R.id.rlRightImg, true)
                        .setVisible(R.id.viewFill, true)
                        .setVisible(R.id.llVideo, true)
                        .setText(R.id.tvDuration, news.video_duration_str);
                break;
        }

        baseViewHolder.setText(R.id.tvTitle, news.title)
                .setText(R.id.tvAuthorName, news.source)
                .setText(R.id.tvCommentCount, news.comments_count + "评论")
                .setText(R.id.tvTime, DateUtils.getTimeDown(news.behot_time));
    }

    private void setGone(BaseViewHolder baseViewHolder) {
        baseViewHolder.setVisible(R.id.viewFill, false)
                .setVisible(R.id.llCenterImg, false)
                .setVisible(R.id.rlBigImg, false)
                .setVisible(R.id.llVideo, false)
                .setVisible(R.id.rlRightImg, false);
    }
}
