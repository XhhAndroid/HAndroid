package com.h.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;

import com.h.android.R;
import com.h.android.utils.ScreenUtils;


/**
 * @author zhangxh
 */
public class BaseAlertDialog extends Dialog {
    private BaseAlertDialog mBaseAlertDialog;
    private TextView titleTextTv;
    private TextView messageTv;
    private LinearLayout viewContain;
    private LinearLayout buttonLayout;
    protected Context mContext;

    /**
     * 添加按钮及监听
     *
     * @param text
     * @param color
     * @param size
     * @param actionBarListener
     * @return
     */
    int countDownTime;
    private Runnable getPhoneCodeLockTimer;

    public BaseAlertDialog(Context context) {
        super(context, R.style.BaseDialog);
        mContext = context;

        setContentView(R.layout.basedialog);
        mBaseAlertDialog = this;
        titleTextTv = findViewById(R.id.titleTextTv);
        messageTv = findViewById(R.id.messageTv);
        viewContain = findViewById(R.id.viewContain);
        buttonLayout = findViewById(R.id.buttonLayout);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseAlertDialog = this;
    }

    /**
     * 添加其它类型view
     * 业务层设置边距等属性
     *
     * @param chilView
     * @return
     */
    public BaseAlertDialog addOtherView(View chilView) {
        viewContain.removeAllViews();
        viewContain.addView(chilView);
        return mBaseAlertDialog;
    }

    /**
     * 空白处取消
     *
     * @return
     */
    public BaseAlertDialog cancelable(boolean cancelable) {
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        return mBaseAlertDialog;
    }

    /**
     * 是否支持夜间模式自动切换
     *
     * @param supportNightSkin
     * @return
     */
    public BaseAlertDialog setSupportNightSkin(boolean supportNightSkin) {
        return mBaseAlertDialog;
    }

    /**
     * @param text
     * @return
     * @Description 设置标题文字
     */
    public BaseAlertDialog setDialogTitle(CharSequence text, int textSize, int color) {
        if (TextUtils.isEmpty(text)) {
            titleTextTv.setVisibility(View.GONE);
            return mBaseAlertDialog;
        }
        titleTextTv.setVisibility(View.VISIBLE);
        titleTextTv.setText(text);

        titleTextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(textSize == 0 ? R.dimen.text_size_16 : textSize));
        titleTextTv.setTextColor(ContextCompat.getColor(mContext, color == 0 ? R.color.C1 : color));
        return mBaseAlertDialog;
    }

    /**
     * 设置标题文字
     *
     * @param text
     * @return
     */
    public BaseAlertDialog setDialogTitle(CharSequence text) {
        return setDialogTitle(text, 0, 0);
    }

    private Handler timeHandler = new Handler();

    /**
     * 设置消息文字
     *
     * @param text
     * @return
     */
    public BaseAlertDialog setDialogMessage(CharSequence text, int textSize, int color, boolean asHTML, View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            messageTv.setVisibility(View.GONE);
            return mBaseAlertDialog;
        }
        messageTv.setVisibility(View.VISIBLE);
        if (asHTML && text instanceof String) {
            messageTv.setText(Html.fromHtml((String) text));
        } else {
            messageTv.setText(text);
        }

        messageTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(textSize == 0 ? R.dimen.text_size_14 : textSize));
        messageTv.setTextColor(ContextCompat.getColor(mContext, color == 0 ? R.color.C1 : color));
        return mBaseAlertDialog;
    }

    /**
     * 设置消息文字
     *
     * @param text
     * @return
     */
    public BaseAlertDialog setDialogMessage(CharSequence text, View.OnClickListener listener) {
        return setDialogMessage(text, 0, 0, false, listener);
    }

    public BaseAlertDialog setDialogMessage(CharSequence text) {
        return setDialogMessage(text, 0, 0, false, null);
    }

    public BaseAlertDialog setDialogMessage(CharSequence text, boolean asHTML) {
        return setDialogMessage(text, 0, 0, asHTML, null);
    }

    public BaseAlertDialog setMessageOnclick(final ActionBarListener actionBarListener) {
        messageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionBarListener != null) {
                    actionBarListener.viewOnclickListener(messageTv);
                }
            }
        });
        return mBaseAlertDialog;
    }

    /**
     * 橙红色button
     *
     * @param text
     * @param actionBarListener
     * @return
     */
    public BaseAlertDialog addOkButton(final CharSequence text, ActionBarListener actionBarListener) {
        return this.addButton(text, R.color.C1, R.dimen.text_size_16, actionBarListener);
    }

    public BaseAlertDialog addOkButton(final String text, int downTime, ActionBarListener actionBarListener) {
        return this.addButton(text, R.color.C1, R.dimen.text_size_16, downTime, actionBarListener);
    }

    /**
     * 灰色按钮
     *
     * @param text
     * @param actionBarListener
     * @return
     */
    public BaseAlertDialog addCancelButton(final String text, ActionBarListener actionBarListener) {
        return this.addButton(text, R.color.gray, R.dimen.text_size_16, actionBarListener);
    }

    public BaseAlertDialog addCancelButtonAndColor(final String text, @ColorRes int textColorRes, ActionBarListener actionBarListener) {
        return this.addButton(text, textColorRes, R.dimen.text_size_16, actionBarListener);
    }

    public BaseAlertDialog addCancelButton(final String text, int downTime, ActionBarListener actionBarListener) {
        return this.addButton(text, R.color.gray, R.dimen.text_size_16, downTime, actionBarListener);
    }

    public BaseAlertDialog addButton(final CharSequence text, final int textColor, @DimenRes int size, int downTime, final ActionBarListener actionBarListener) {
        final TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = ScreenUtils.dip2px(32);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(size == 0 ? R.dimen.text_size_16 : size));
        textView.setTextColor(ContextCompat.getColor(mContext, textColor == 0 ? R.color.C1 : textColor));
        textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        if (downTime > 0) {
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            textView.setEnabled(false);
            countDownTime = downTime + 1;
            getPhoneCodeLockTimer = new Runnable() {
                @Override
                public void run() {
                    countDownTime--;
                    if (countDownTime <= 0) {
                        timeHandler.removeCallbacks(getPhoneCodeLockTimer);
                        textView.setText(text);
                        textView.setEnabled(true);
                        textView.setTextColor(ContextCompat.getColor(mContext, textColor == 0 ? R.color.C1 : textColor));
                        return;
                    }
                    textView.setText(text + "(" + countDownTime + ")");
                    timeHandler.postDelayed(getPhoneCodeLockTimer, 1000);
                }
            };
            timeHandler.post(getPhoneCodeLockTimer);
        } else {
            textView.setText(TextUtils.isEmpty(text) ? "" : text);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (actionBarListener != null) {
                    actionBarListener.viewOnclickListener(textView);
                }
            }
        });
        buttonLayout.setVisibility(View.VISIBLE);
        buttonLayout.addView(textView);
        return mBaseAlertDialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (timeHandler != null && getPhoneCodeLockTimer != null) {
            timeHandler.removeCallbacks(getPhoneCodeLockTimer);
        }
    }

    public BaseAlertDialog addButton(CharSequence text, int color, @DimenRes int size, ActionBarListener actionBarListener) {
        return addButton(text, color, size, 0, actionBarListener);
    }

    public interface ActionBarListener {

        void viewOnclickListener(TextView textView);
    }
}
