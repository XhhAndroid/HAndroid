package com.h.android.stack;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.h.android.HAndroid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

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
