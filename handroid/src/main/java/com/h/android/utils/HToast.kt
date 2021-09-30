package com.h.android.utils

import android.content.Context
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.core.app.NotificationManagerCompat
import com.h.android.HAndroid
import com.h.android.R

/**
 * 2020/11/21
 *
 * @author zhangxiaohui
 * @describe
 */
object HToast {
    private var noticeString: CharSequence? = null

    private val context: Context
        private get() = HAndroid.getApplication()

    /**
     * toast是否可用
     *
     * @return
     */
    val isNotificationEnabled: Boolean
        get() {
            try {
                val notificationManagerCompat = NotificationManagerCompat.from(context)
                return notificationManagerCompat.areNotificationsEnabled()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

    /**
     * 校验线程
     * 否则:Can't create handler inside thread that has not called Looper.prepare()
     *
     * @param notice
     * @return
     */
    @UiThread
    fun showToast(@StringRes notice: Int, type: ToastType): Toast? {
        return showToast(HAndroid.getApplication().getString(notice), type)
    }

    /**
     * 成功样式
     */
    fun showToastSuccess(notice: CharSequence): Toast? {
        return showToast(notice, ToastType.SUCCESS)
    }

    /**
     * 失败样式
     */
    fun showToastFail(notice: CharSequence): Toast? {
        return showToast(notice, ToastType.ERROR)
    }

    /**
     * 不带符号
     */
    fun showToastNormal(notice: CharSequence): Toast? {
        return showToast(notice, ToastType.NORMAL)
    }

    /**
     * 校验线程
     * 否则:Can't create handler inside thread that has not called Looper.prepare()
     *
     * @param notice
     * @return
     */
    @UiThread
    fun showToast(notice: CharSequence, type: ToastType): Toast? {
        if (!isMainThread || TextUtils.isEmpty(notice)) {
            return null
        }
        //app 后台不允许toast
        if (HAndroid.activityStackProvider?.isBackground == true) {
            return null
        }
        /**
         * 全局單個toast賦值
         */
        noticeString = notice
        val toast = createToast(notice, type)

        if (isNotificationEnabled) {
            toast.show()
        } else {
            return null
        }
        return toast
    }

    fun createToast(msg: CharSequence?, type: ToastType?): Toast {
        val inflater = LayoutInflater.from(HAndroid.getApplication())
        val view = inflater.inflate(R.layout.toast_layout, null)
        val text = view.findViewById<TextView>(android.R.id.message)
        val dp19 = ScreenUtils.dip2px(19f)
        when (type) {
            ToastType.ERROR -> {
                val errorDrawable = HAndroid.getApplication().getDrawable(R.drawable.xxf_ic_toast_error)
                errorDrawable!!.setBounds(0, 0, dp19, dp19)
                text.setCompoundDrawables(errorDrawable, null, null, null)
            }
            ToastType.NORMAL -> text.setCompoundDrawables(null, null, null, null)
            ToastType.SUCCESS -> {
                val successDrawable = HAndroid.getApplication().getDrawable(R.drawable.xxf_ic_toast_success)
                successDrawable!!.setBounds(0, 0, dp19, dp19)
                text.setCompoundDrawables(successDrawable, null, null, null)
            }
        }
        text.text = msg
        val toast = Toast(HAndroid.getApplication())
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        return toast
    }

    /**
     * 检查是否在主线程showToast
     *
     * @return
     */
    private val isMainThread: Boolean
        private get() = Looper.myLooper() == Looper.getMainLooper()

    enum class ToastType {
        NORMAL, ERROR, SUCCESS
    }
}