package com.yyp.newsclient.base;

import android.app.Application;

import com.yyp.newsclient.theme.util.SharedPreferencesMgr;

public class BaseApplication extends Application {
    //private UserInfo userInfo;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedPreferencesMgr.init(this, "yyp");
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
