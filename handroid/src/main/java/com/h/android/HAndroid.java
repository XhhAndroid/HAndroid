package com.h.android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.h.android.loadding.ProgressHUDFactory;
import com.h.android.loadding.ProgressListener;
import com.h.android.rx.transformer.ProgressHUDTransformerImpl;
import com.h.android.stack.AndroidActivityStackProvider;

import java.util.Objects;

import io.reactivex.functions.Function;

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
public class HAndroid {

    private static Application application;
    private static AndroidActivityStackProvider activityStackProvider;
    private static boolean logDebug = false;

    public static void init(Builder builder) {
        if (application == null) {
            synchronized (HAndroid.class) {
                if (application == null) {
                    application = builder.application;
                    activityStackProvider = new AndroidActivityStackProvider(application);

                    ProgressHUDFactory.Companion.get().setProgressHUDProvider(builder.progressHUDProvider);
                }
            }
        }
    }

    public static boolean isDebug(){
        return logDebug;
    }

    public static class Builder {

        Application application;
        ProgressHUDFactory.ProgressHUDProvider progressHUDProvider;

        Function<Throwable, String> errorConvertFunction = new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return throwable.getMessage();
            }
        };

        public Builder(@NonNull Application application) {
            this.application = Objects.requireNonNull(application);
        }

        public Builder setProgressProvider(ProgressHUDFactory.ProgressHUDProvider progressHUDProvider){
            this.progressHUDProvider = progressHUDProvider;
            return this;
        }

        public Builder setErrorConvertFunction(@NonNull Function<Throwable, String> errorConvertFunction) {
            this.errorConvertFunction = Objects.requireNonNull(errorConvertFunction);
            return this;
        }

        public Builder setDebug(boolean debug) {
            HAndroid.logDebug = debug;
            return this;
        }
    }

    public static Application getApplication() {
        if (application == null) {
            throw new NullPointerException("you need call HAndroid.init function in application's onCreate");
        }
        return application;
    }

    /**
     * 绑定loading
     *
     * @param lifecycleOwner
     * @param <T>
     * @return
     */
    public static <T> ProgressHUDTransformerImpl<T> bindToProgressHud(LifecycleOwner lifecycleOwner) {
        ProgressListener progressHUD = ProgressHUDFactory.Companion.get().getProgressHUD(lifecycleOwner);
        return new ProgressHUDTransformerImpl.Builder(progressHUD).build();
    }


    public static AndroidActivityStackProvider getActivityStackProvider() {
        return activityStackProvider;
    }
}
