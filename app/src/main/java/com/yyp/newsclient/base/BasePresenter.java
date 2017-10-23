package com.yyp.newsclient.base;

import com.yyp.newsclient.model.Notice;
import com.yyp.newsclient.util.RxBus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class BasePresenter<V> implements Presenter<V> {

    public V mvpView;
    private CompositeSubscription mCompositeSubscription;

    public BasePresenter(V mvpView) {
        attachView(mvpView);
    }

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
        unMSubscribe();
    }

    /**
     * 订阅
     * @param observable 订阅事件
     * @param subscriber 订阅者
     */
    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /**
     * 取消订阅
     */
    public void unMSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 发送消息
     */
    public void post(Notice msg) {
        RxBus.getDefault().post(msg);
    }
}
