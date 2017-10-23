package com.yyp.newsclient.base;

import rx.Subscriber;

public abstract class BaseCallBack<T> extends Subscriber<T> {

    @Override
    public void onError(final Throwable e) {
        e.printStackTrace();
        onMError(e);
    }

    /**
     * 处理错误
     * @param e
     */
    protected abstract void onMError(Throwable e);
}
