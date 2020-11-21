package com.h.android.stack;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

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
public class AndroidActivityStackProvider extends SimpleActivityLifecycleCallbacks
        implements ActivityStackProvider {

    final Stack<Activity> activityStack = new Stack<>();
    /**
     * 并不是所有activity都是FragmentActivity,可能不是LifecyclerOwner的子类,比如sdk里面的页面
     */
    final Map<Activity, Lifecycle.Event> activityLifecycle = new LinkedHashMap<>();

    public AndroidActivityStackProvider(Application application) {
        this.register(application);
    }


    @Nullable
    @Override
    public Activity getTopActivity() {
        try {
            return activityStack.lastElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public Activity getRootActivity() {
        try {
            return activityStack.firstElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public Activity[] getAllActivity() {
        return activityStack.toArray(new Activity[activityStack.size()]);
    }

    @Override
    public Lifecycle.Event getActivityLifecycle(@NonNull Activity activity) {
        Lifecycle.Event event = activityLifecycle.get(activity);
        if (event == null) {
            return Lifecycle.Event.ON_DESTROY;
        }
        return event;
    }

    @Override
    public boolean empty() {
        return activityStack.isEmpty();
    }


    @Override
    public final void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        super.onActivityCreated(activity, savedInstanceState);
        activityLifecycle.put(activity, Lifecycle.Event.ON_CREATE);
        activityStack.push(activity);
    }

    @Override
    public final void onActivityStarted(Activity activity) {
        super.onActivityStarted(activity);
        activityLifecycle.put(activity, Lifecycle.Event.ON_START);
    }

    @Override
    public final void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        activityLifecycle.put(activity, Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        super.onActivityPaused(activity);
        activityLifecycle.put(activity, Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public final void onActivityStopped(Activity activity) {
        super.onActivityStopped(activity);
        activityLifecycle.put(activity, Lifecycle.Event.ON_STOP);
    }

    @Override
    public final void onActivityDestroyed(Activity activity) {
        super.onActivityDestroyed(activity);
        activityLifecycle.remove(activity);
        activityStack.remove(activity);
    }

    /**
     * 是否在后台
     * 所有Activity都是onStop
     *
     * @return
     */
    public final boolean isBackground() {
        for (Map.Entry<Activity, Lifecycle.Event> entry : activityLifecycle.entrySet()) {
            Lifecycle.Event event = entry.getValue();
            if (event != Lifecycle.Event.ON_STOP) {
                return false;
            }
        }
        return true;
    }

    /**
     * 重启app
     */
    public void restartApp() {
        try {
            ArrayList<Activity> activities = new ArrayList<>(activityStack);
            for (Activity activity : activities) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PackageManager packageManager = HAndroid.getApplication().getPackageManager();
            if (null == packageManager) {
                return;
            }
            final Intent intent = packageManager.getLaunchIntentForPackage(HAndroid.getApplication().getPackageName());
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                HAndroid.getApplication().startActivity(intent);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
