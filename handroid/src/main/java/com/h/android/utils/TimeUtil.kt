package com.h.android.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 *2020/11/16
 *@author zhangxiaohui
 *@describe
 */
class TimeUtil {

    /**
     * 时间戳转格式时间
     *
     * @param timeStamp
     * @param format
     * @return
     */
    fun getTimeStr(timeStamp: Long, format: String): String {
        var date: String? = null
        date = try {
            val sf = SimpleDateFormat(format)
            sf.format(Date(timeStamp))
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return date
    }
}