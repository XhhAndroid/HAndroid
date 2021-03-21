package com.xh.android;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.h.android.HAndroid;
import com.h.android.loadding.ProgressHUDFactory;
import com.h.android.loadding.ProgressListener;
import com.xh.android.loading.LoadingDialog;

import org.jetbrains.annotations.Nullable;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

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

        HAndroid.init(new HAndroid.Builder(this)
                .setProgressProvider(new ProgressHUDFactory.ProgressHUDProvider() {
                    @Nullable
                    @Override
                    public ProgressListener onCreateProgressHUD(@Nullable LifecycleOwner lifecycleOwner) {
                        if(lifecycleOwner instanceof AppCompatActivity){
                            AppCompatActivity appCompatActivity = ((AppCompatActivity) lifecycleOwner);
                            return new LoadingDialog(appCompatActivity);
                        }
                        return null;
                    }
                })
                .setErrorConvertFunction(new Function<Throwable, String>() {
                    @Override
                    public String apply(@NonNull Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .setDebug(true));
    }
}
