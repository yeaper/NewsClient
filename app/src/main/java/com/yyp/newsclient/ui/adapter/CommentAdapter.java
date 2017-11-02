package com.yyp.newsclient.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyp.newsclient.R;
import com.yyp.newsclient.interfaces.OnCommentItemClickListener;
import com.yyp.newsclient.model.Comment;
import com.yyp.newsclient.theme.util.ColorUiUtil;
import com.yyp.newsclient.util.DateUtils;
import com.yyp.newsclient.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by yyp on 2017/10/30.
 */
public class CommentAdapter extends BaseQuickAdapter<Comment> {
    OnCommentItemClickListener listener;
    private Context ctx;

    public CommentAdapter(Context context, List<Comment> data){
        super(R.layout.news_detail_comment_item, data);
        this.ctx = context;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final Comment comment) {
        //防止复用View没有改变主题，重新设置
        ColorUiUtil.changeTheme(baseViewHolder.convertView, mContext.getTheme());

        ImageLoaderUtils.loadCircleAvatar(ctx, comment.avatar,
                (ImageView) baseViewHolder.getView(R.id.comment_avatar));
        baseViewHolder.setText(R.id.comment_username, comment.name);
        if(comment.thumbsUpCount > 0){
            baseViewHolder.setText(R.id.thumbs_up_count, comment.thumbsUpCount+"");
        }else{
            baseViewHolder.setText(R.id.thumbs_up_count, "赞");
        }
        if(comment.commentList.size() > 0){
            baseViewHolder.getView(R.id.comment_reply_btn).setBackgroundResource(R.drawable.bg_reply);
            baseViewHolder.setText(R.id.comment_reply_btn, comment.commentList.size()+"回复");
        }

        baseViewHolder.setText(R.id.comment_content, comment.content);
        baseViewHolder.setText(R.id.comment_date, DateUtils.getTimeDown(comment.date));

        baseViewHolder.setOnClickListener(R.id.comment_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onCommentItemClick(baseViewHolder.getAdapterPosition());
                }
            }
        });
        baseViewHolder.setOnClickListener(R.id.comment_reply_btn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onCommentItemClick(baseViewHolder.getAdapterPosition());
                }
            }
        });
        baseViewHolder.setOnClickListener(R.id.thumbs_up_ll, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(baseViewHolder.getView(R.id.thumbs_up_btn).isSelected()){ // 取消点赞
                    baseViewHolder.getView(R.id.thumbs_up_btn).setSelected(false);
                    ((TextView)baseViewHolder.getView(R.id.thumbs_up_count))
                            .setTextColor(ctx.getResources().getColor(R.color.bray));
                    if(comment.thumbsUpCount-1 <= 0){
                        baseViewHolder.setText(R.id.thumbs_up_count, "赞");
                    }else{
                        baseViewHolder.setText(R.id.thumbs_up_count, comment.thumbsUpCount+"");
                    }
                }else{ // 点赞
                    baseViewHolder.getView(R.id.thumbs_up_btn).setSelected(true);
                    ((TextView)baseViewHolder.getView(R.id.thumbs_up_count))
                            .setTextColor(ctx.getResources().getColor(R.color.colorPrimary));
                    baseViewHolder.setText(R.id.thumbs_up_count, (comment.thumbsUpCount+1)+"");
                }
            }
        });
    }

    public void setOnCommentItemClickListener(OnCommentItemClickListener l){
        this.listener = l;
    }
}
