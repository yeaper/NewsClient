package com.yyp.newsclient.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyp.newsclient.model.News;

import java.util.List;

/**
 * Created by yyp on 2017/10/30.
 */
public class NewsDetailAdapter extends BaseQuickAdapter<News> {
    private Context ctx;

    public NewsDetailAdapter(Context context, List<News> data){
        super(data);
        this.ctx = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {

    }
}
