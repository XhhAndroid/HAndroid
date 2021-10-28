package com.xh.android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.h.android.HAndroid;
import com.h.android.loadding.ProgressHUDFactory;
import com.h.android.loadding.ProgressListener;
import com.xh.android.loading.LoadingDialog;

import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Consumer;
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

        HAndroid.INSTANCE.init(new HAndroid.Builder(this)
                .addErrorConvertFunction(new Function<Throwable, String>() {
                    @Override
                    public String apply(@NonNull Throwable throwable) throws Exception {
                        //全局统一处理业务中rx流function错误
                        return "";
                    }
                })
                .addErrorHandler(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //全局统一处理rx流的error
                    }
                })
//                .addToast()
                .addProgressProvider(new ProgressHUDFactory.ProgressHUDProvider() {
                    @Nullable
                    @Override
                    public ProgressListener onCreateProgressHUD(@Nullable LifecycleOwner lifecycleOwner) {
                        if(lifecycleOwner instanceof AppCompatActivity){
                            AppCompatActivity appCompatActivity = ((AppCompatActivity) lifecycleOwner);
                            return new LoadingDialog(appCompatActivity);
                        }
                        return null;
                    }
                }).setDebug(true));
    }
}
