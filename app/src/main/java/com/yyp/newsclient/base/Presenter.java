package com.yyp.newsclient.base;

public interface Presenter<V> {

    /**
     * 绑定 View
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑 View
     */
    void detachView();

}
