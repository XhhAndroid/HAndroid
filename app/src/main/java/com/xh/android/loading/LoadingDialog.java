package com.xh.android.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.h.android.loadding.ProgressListener;
import com.h.android.utils.ScreenUtils;
import com.xh.android.R;

public class LoadingDialog extends Dialog implements ProgressListener {
    private int MAX_SECOND = 10;//默认10秒自动取消,时间太长影响UI阻塞,时间太短影响返回结果提示

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if (getWindow() != null) {
            getWindow().setDimAmount(0f);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.6f;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(lp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void showLoadingDialog(@Nullable String notice) {
        this.show();
    }

    @Override
    public void dismissLoadingDialog() {
        this.dismiss();
    }

    @Override
    public void dismissLoadingDialogWithSuccess(String notice) {
        this.dismiss();
    }

    @Override
    public void dismissLoadingDialogWithFail(String notice) {
        this.dismiss();
    }

    @Override
    public boolean isShowLoading() {
        return this.isShowing();
    }
}
