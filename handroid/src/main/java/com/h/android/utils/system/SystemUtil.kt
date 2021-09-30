package com.h.android.utils.system

import android.content.Context
import android.net.Proxy
import android.os.Build
import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*

/**
 * @author zhangxiaohui
 * @describe
 * @date 2019/8/9
 */
object SystemUtil {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    val systemLanguage: String
        get() = try {
            Locale.getDefault().language
        } catch (e: Exception) {
            "default"
        }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    val systemVersion: String
        get() = try {
            Build.VERSION.RELEASE
        } catch (e: Exception) {
            "default"
        }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    val systemModel: String
        get() = try {
            Build.MODEL
        } catch (e: Exception) {
            "default"
        }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    val deviceBrand: String
        get() = try {
            Build.BRAND
        } catch (e: Exception) {
            "default"
        }

    /*
     * 判断设备 是否使用代理上网
     * */
    fun isWifiProxy(context: Context?): Boolean {
        try {
            val IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
            val proxyAddress: String?
            val proxyPort: Int
            if (IS_ICS_OR_LATER) {
                proxyAddress = System.getProperty("http.proxyHost")
                val portStr = System.getProperty("http.proxyPort")
                proxyPort = (portStr ?: "-1").toInt()
            } else {
                proxyAddress = Proxy.getHost(context)
                proxyPort = Proxy.getPort(context)
            }
            return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}