package com.h.android.stack;

import android.app.Activity;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

/**
 * 2020/11/21
 *
 * @author zhangxiaohui
 * @describe
 */
public interface ActivityStackProvider {

    @Nullable
    @CheckResult
    Activity getTopActivity();

    @Nullable
    @CheckResult
    Activity getRootActivity();

    @NonNull
    Activity[] getAllActivity();

    /**
     * 或activity的状态
     *
     * @param activity
     * @return
     */
    Lifecycle.Event getActivityLifecycle(@NonNull Activity activity);

    boolean empty();
}
