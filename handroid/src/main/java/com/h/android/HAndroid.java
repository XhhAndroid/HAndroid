package com.h.android;

import android.app.Application;

import androidx.annotation.NonNull;

import com.h.android.stack.AndroidActivityStackProvider;

import java.util.Objects;

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
public class HAndroid {

    private static Application application;
    private static AndroidActivityStackProvider activityStackProvider;

    public static void init(Builder builder) {
        if (application == null) {
            synchronized (HAndroid.class) {
                if (application == null) {
                    application = builder.application;
                    activityStackProvider = new AndroidActivityStackProvider(application);
                }
            }
        }
    }

    public static class Builder {

        Application application;

        public Builder(@NonNull Application application) {
            this.application = Objects.requireNonNull(application);
        }
    }

    public static Application getApplication() {
        if (application == null) {
            throw new NullPointerException("you need call XXF.init function");
        }
        return application;
    }

    public static AndroidActivityStackProvider getActivityStackProvider() {
        return activityStackProvider;
    }
}
