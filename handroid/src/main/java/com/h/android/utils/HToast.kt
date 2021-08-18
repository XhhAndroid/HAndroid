package com.h.android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
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
import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * 2020/11/21
 *
 * @author zhangxiaohui
 * @describe
 */
object HToast {
    private var sField_TN: Field? = null
    private var sField_TN_Handler: Field? = null
    private var iNotificationManagerObj: Any? = null
    private var noticeString: CharSequence? = null
    fun hook(toast: Toast?) {
        try {
            val tn = sField_TN!![toast]
            val preHandler = sField_TN_Handler!![tn] as Handler
            sField_TN_Handler!![tn] = SafelyHandlerWrapper(preHandler)
        } catch (e: Exception) {
        }
    }

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
        if (HAndroid.getActivityStackProvider().isBackground) {
            return null
        }
        /**
         * 全局單個toast賦值
         */
        noticeString = notice
        val toast = createToast(notice, type)
        //fix bug #65709 BadTokenException from BugTags
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            hook(toast)
        }
        if (isNotificationEnabled) {
            toast.show()
        } else {
            return null
        }
        return toast
    }

    /**
     * 强制显示系统Toast
     */
    @Throws(Throwable::class)
    private fun showSystemToast(toast: Toast, notice: CharSequence, type: ToastType) {
        @SuppressLint("SoonBlockedPrivateApi") val getServiceMethod = Toast::class.java.getDeclaredMethod("getService")
        getServiceMethod.isAccessible = true
        //hook INotificationManager
        if (iNotificationManagerObj == null) {
            iNotificationManagerObj = getServiceMethod.invoke(null)
            val iNotificationManagerCls = Class.forName("android.app.INotificationManager")
            val iNotificationManagerProxy = Proxy.newProxyInstance(
                toast.javaClass.classLoader,
                arrayOf(iNotificationManagerCls),
                InvocationHandler { proxy, method, args ->
                    try {

                        //强制使用系统Toast
                        if ("enqueueToast" == method.name || "enqueueToastEx" == method.name) {  //华为p20 pro上为enqueueToastEx
                            args[0] = "android"
                        }
                        return@InvocationHandler method.invoke(iNotificationManagerObj, *args)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                    proxy
                })
            val sServiceFiled = Toast::class.java.getDeclaredField("sService")
            sServiceFiled.isAccessible = true
            sServiceFiled[null] = iNotificationManagerProxy
        }
        toast.show()
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
        toast.setGravity(Gravity.CENTER, 0, 0)
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

    private class SafelyHandlerWrapper(private val impl: Handler) : Handler() {
        override fun dispatchMessage(msg: Message) {
            try {
                super.dispatchMessage(msg)
            } catch (e: Exception) {
            }
        }

        override fun handleMessage(msg: Message) {
            impl.handleMessage(msg) //需要委托给原Handler执行
        }
    }

    /**
     * 通过反射封装 Toast 类中TN Binder内部类中的handler,
     * 捕获BadTokenException, 解决Android API 25 引入的
     * Bug
     */
    init {
        try {
            sField_TN = Toast::class.java.getDeclaredField("mTN")
            sField_TN!!.isAccessible = true
            sField_TN_Handler = sField_TN!!.type.getDeclaredField("mHandler")
            sField_TN_Handler!!.isAccessible = true
        } catch (e: Exception) {
        }
    }
}