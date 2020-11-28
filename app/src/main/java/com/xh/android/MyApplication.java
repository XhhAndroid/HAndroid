package com.xh.android;

import android.app.Application;

import com.h.android.HAndroid;

/**
 * 2020/11/28
 *
 * @author zhangxiaohui
 * @describe
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HAndroid.init(new HAndroid.Builder(this));
    }
}
