package com.yyp.newsclient.view;

import com.yyp.newsclient.model.News;

import java.util.List;

/**
 * 新闻数据接口
 */
public interface INewsListView {
    void onGetNewsListSuccess(List<News> response);

    void onError(Throwable e);
}
