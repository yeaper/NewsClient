package com.yyp.newsclient.base;

import android.text.TextUtils;

public abstract class SubscriberCallBack<T> extends BaseCallBack<ResultResponse<T>> {

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

    /**
     * 处理正常返回结果
     * @param response
     */
    protected abstract void onMSuccess(T response);
}
