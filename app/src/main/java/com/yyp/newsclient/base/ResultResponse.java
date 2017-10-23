package com.yyp.newsclient.base;

/**
 * 响应结果
 * @param <T>
 */
public class ResultResponse<T> {

    public String message;
    public String success;
    public T data;

    public ResultResponse(String _message, T result) {
        message = _message;
        data = result;
    }
}
