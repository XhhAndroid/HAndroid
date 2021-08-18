package com.h.android.utils

import java.util.*

/**
 *2021/5/7
 *@author zhangxiaohui
 * 解析链接里面的参数
 *@describe
 */
object HttpLinkParamUtil {
    private const val MIN_ARRAY_LEN = 2
    private const val DIVIDE_INTO_PAIRS = 2

    fun parseRequestParam(url: String): Map<String, String>? {
        val map: MutableMap<String, String> = HashMap()
        if (!url.contains("?")) {
            return null
        }
        val parts = url.split("\\?".toRegex(), DIVIDE_INTO_PAIRS.coerceAtLeast(0)).toTypedArray()
        if (parts.size < MIN_ARRAY_LEN) {
            return null
        }
        val parsedStr = parts[1]
        if (parsedStr.contains("&")) {
            val multiParamObj = parsedStr.split("&".toRegex()).toTypedArray()
            for (obj in multiParamObj) {
                parseBasicParam(map, obj)
            }
            return map
        }
        parseBasicParam(map, parsedStr)
        return map
    }

    private fun parseBasicParam(map: MutableMap<String, String>, str: String) {
        val paramObj = str.split("=".toRegex()).toTypedArray()
        if (paramObj.size < MIN_ARRAY_LEN) {
            return
        }
        map[paramObj[0]] = paramObj[1]
    }
}