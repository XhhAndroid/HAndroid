package com.h.android.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.h.android.R
import com.h.android.utils.ScreenUtils

/**
 * @author zhangxh
 */
class BaseAlertDialog(protected var mContext: Context) : Dialog(mContext, R.style.BaseDialog) {
    private var mBaseAlertDialog: BaseAlertDialog
    private val titleTextTv: TextView
    private val messageTv: TextView
    private val viewContain: LinearLayout
    private val buttonLayout: LinearLayout

    /**
     * 添加按钮及监听
     *
     * @param text
     * @param color
     * @param size
     * @param actionBarListener
     * @return
     */
    var countDownTime = 0
    private var getPhoneCodeLockTimer: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        mBaseAlertDialog = this
    }

    /**
     * 添加其它类型view
     * 业务层设置边距等属性
     *
     * @param chilView
     * @return
     */
    fun addOtherView(chilView: View?): BaseAlertDialog {
        viewContain.removeAllViews()
        viewContain.addView(chilView)
        return mBaseAlertDialog
    }

    /**
     * 空白处取消
     *
     * @return
     */
    fun cancelable(cancelable: Boolean): BaseAlertDialog {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
        return mBaseAlertDialog
    }

    /**
     * 是否支持夜间模式自动切换
     *
     * @param supportNightSkin
     * @return
     */
    fun setSupportNightSkin(supportNightSkin: Boolean): BaseAlertDialog {
        return mBaseAlertDialog
    }

    /**
     * @param text
     * @return
     * @Description 设置标题文字
     */
    fun setDialogTitle(text: CharSequence?, textSize: Int, color: Int): BaseAlertDialog {
        if (TextUtils.isEmpty(text)) {
            titleTextTv.visibility = View.GONE
            return mBaseAlertDialog
        }
        titleTextTv.visibility = View.VISIBLE
        titleTextTv.text = text
        titleTextTv.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            mContext.resources.getDimension(if (textSize == 0) R.dimen.text_size_16 else textSize)
        )
        titleTextTv.setTextColor(ContextCompat.getColor(mContext, if (color == 0) R.color.C1 else color))
        return mBaseAlertDialog
    }

    /**
     * 设置标题文字
     *
     * @param text
     * @return
     */
    fun setDialogTitle(text: CharSequence?): BaseAlertDialog {
        return setDialogTitle(text, 0, 0)
    }

    private val timeHandler: Handler? = Handler()

    /**
     * 设置消息文字
     *
     * @param text
     * @return
     */
    fun setDialogMessage(
        text: CharSequence?,
        textSize: Int,
        color: Int,
        asHTML: Boolean,
        listener: View.OnClickListener?
    ): BaseAlertDialog {
        if (TextUtils.isEmpty(text)) {
            messageTv.visibility = View.GONE
            return mBaseAlertDialog
        }
        messageTv.visibility = View.VISIBLE
        if (asHTML && text is String) {
            messageTv.text = Html.fromHtml(text as String?)
        } else {
            messageTv.text = text
        }
        messageTv.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            mContext.resources.getDimension(if (textSize == 0) R.dimen.text_size_14 else textSize)
        )
        messageTv.setTextColor(ContextCompat.getColor(mContext, if (color == 0) R.color.C1 else color))
        return mBaseAlertDialog
    }

    /**
     * 设置消息文字
     *
     * @param text
     * @return
     */
    fun setDialogMessage(text: CharSequence?, listener: View.OnClickListener?): BaseAlertDialog {
        return setDialogMessage(text, 0, 0, false, listener)
    }

    fun setDialogMessage(text: CharSequence?): BaseAlertDialog {
        return setDialogMessage(text, 0, 0, false, null)
    }

    fun setDialogMessage(text: CharSequence?, asHTML: Boolean): BaseAlertDialog {
        return setDialogMessage(text, 0, 0, asHTML, null)
    }

    fun setMessageOnclick(actionBarListener: ActionBarListener?): BaseAlertDialog {
        messageTv.setOnClickListener { actionBarListener?.viewOnclickListener(messageTv) }
        return mBaseAlertDialog
    }

    /**
     * 橙红色button
     *
     * @param text
     * @param actionBarListener
     * @return
     */
    fun addOkButton(text: CharSequence, actionBarListener: ActionBarListener?): BaseAlertDialog {
        return this.addButton(text, R.color.C1, R.dimen.text_size_16, actionBarListener)
    }

    fun addOkButton(text: String, downTime: Int, actionBarListener: ActionBarListener?): BaseAlertDialog {
        return this.addButton(text, R.color.C1, R.dimen.text_size_16, downTime, actionBarListener)
    }

    /**
     * 灰色按钮
     *
     * @param text
     * @param actionBarListener
     * @return
     */
    fun addCancelButton(text: String, actionBarListener: ActionBarListener?): BaseAlertDialog {
        return this.addButton(text, R.color.gray, R.dimen.text_size_16, actionBarListener)
    }

    fun addCancelButtonAndColor(
        text: String,
        @ColorRes textColorRes: Int,
        actionBarListener: ActionBarListener?
    ): BaseAlertDialog {
        return this.addButton(text, textColorRes, R.dimen.text_size_16, actionBarListener)
    }

    fun addCancelButton(text: String, downTime: Int, actionBarListener: ActionBarListener?): BaseAlertDialog {
        return this.addButton(text, R.color.gray, R.dimen.text_size_16, downTime, actionBarListener)
    }

    fun addButton(
        text: CharSequence,
        textColor: Int,
        @DimenRes size: Int,
        downTime: Int,
        actionBarListener: ActionBarListener?
    ): BaseAlertDialog {
        val textView = TextView(context)
        val layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = ScreenUtils.dip2px(32f)
        textView.layoutParams = layoutParams
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            mContext.resources.getDimension(if (size == 0) R.dimen.text_size_16 else size)
        )
        textView.setTextColor(ContextCompat.getColor(mContext, if (textColor == 0) R.color.C1 else textColor))
        textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        if (downTime > 0) {
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
            textView.isEnabled = false
            countDownTime = downTime + 1
            getPhoneCodeLockTimer = Runnable {
                countDownTime--
                if (countDownTime <= 0) {
                    timeHandler!!.removeCallbacks(getPhoneCodeLockTimer)
                    textView.text = text
                    textView.isEnabled = true
                    textView.setTextColor(ContextCompat.getColor(mContext, if (textColor == 0) R.color.C1 else textColor))
                    return@Runnable
                }
                textView.text = "$text($countDownTime)"
                timeHandler!!.postDelayed(getPhoneCodeLockTimer, 1000)
            }
            timeHandler!!.post(getPhoneCodeLockTimer)
        } else {
            textView.text = if (TextUtils.isEmpty(text)) "" else text
        }
        textView.setOnClickListener {
            dismiss()
            actionBarListener?.viewOnclickListener(textView)
        }
        buttonLayout.visibility = View.VISIBLE
        buttonLayout.addView(textView)
        return mBaseAlertDialog
    }

    override fun dismiss() {
        super.dismiss()
        if (timeHandler != null && getPhoneCodeLockTimer != null) {
            timeHandler.removeCallbacks(getPhoneCodeLockTimer)
        }
    }

    fun addButton(text: CharSequence, color: Int, @DimenRes size: Int, actionBarListener: ActionBarListener?): BaseAlertDialog {
        return addButton(text, color, size, 0, actionBarListener)
    }

    interface ActionBarListener {
        fun viewOnclickListener(textView: TextView?)
    }

    init {
        setContentView(R.layout.basedialog)
        mBaseAlertDialog = this
        titleTextTv = findViewById(R.id.titleTextTv)
        messageTv = findViewById(R.id.messageTv)
        viewContain = findViewById(R.id.viewContain)
        buttonLayout = findViewById(R.id.buttonLayout)
        val layoutParams = window!!.attributes
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        window!!.attributes = layoutParams
    }
}