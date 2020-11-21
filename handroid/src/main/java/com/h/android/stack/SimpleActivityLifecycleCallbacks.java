package com.h.android.stack;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Objects;

/**
 * 2020/11/21
 *
 * @author zhangxiaohui
 * @describe
 */
public class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks{

    public SimpleActivityLifecycleCallbacks() {
    }

    public void register(Application application) {
        Objects.requireNonNull(application);
        application.unregisterActivityLifecycleCallbacks(this);
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
