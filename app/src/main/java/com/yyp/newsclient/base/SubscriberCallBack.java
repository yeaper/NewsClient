package com.yyp.newsclient.base;

import android.text.TextUtils;

import rx.Subscriber;

/**
 * Subscriber的请求结果回调
 * @param <T> List<model></>
 */
public abstract class SubscriberCallBack<T> extends Subscriber<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse<T> response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
                || !TextUtils.isEmpty(response.success) && response.success.equals("true");
        if (isSuccess) {
            onMSuccess(response.data);
        } else {
            onError(new Throwable(response.message));
        }
    }

    @Override
    public void onError(final Throwable e) {
        e.printStackTrace();
        onMError(e.getMessage());
    }

    /**
     * 处理正常返回结果
     * @param response
     */
    protected abstract void onMSuccess(T response);

    /**
     * 处理错误
     * @param error 错误信息
     */
    protected abstract void onMError(String error);
}
