package com.yyp.newsclient.presenter;

import com.yyp.newsclient.base.AppClient;
import com.yyp.newsclient.base.BasePresenter;
import com.yyp.newsclient.base.SubscriberCallBack;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.view.INewsListView;

import java.util.List;

public class NewsListPresenter extends BasePresenter<INewsListView> {
    public NewsListPresenter(INewsListView mvpView) {
        super(mvpView);
    }

    public void getNewsList(String titleCode) {
        addSubscription(AppClient.getApiService().getNews(titleCode), new SubscriberCallBack<List<News>>() {
            @Override
            protected void onMSuccess(List<News> response) {
                mvpView.onGetNewsListSuccess(response);
            }

            @Override
            protected void onMError(String error) {
                mvpView.onError(error);
            }

            @Override
            public void onCompleted() {
            }
        });
    }
}
