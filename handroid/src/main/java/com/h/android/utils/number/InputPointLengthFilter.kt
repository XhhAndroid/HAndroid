package com.h.android.utils.number

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils

/**
 * @author zhangxiaohui
 * @describe 数字小数位输入限制
 * @date 2019/8/14
 */
class InputPointLengthFilter(private val pointLength: Int) : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int,dest: Spanned, dstart: Int, dend: Int): CharSequence {
        // 删除等特殊字符，直接返回
        if ("" == source.toString()) {
            return ""
        }
        if ("." == source.toString() && dstart == 0) {
            return ""
        }
        if (pointLength == 0 && source.toString().contains(".")) {
            return source.subSequence(start, end - 1)
        }
        val dValue = dest.toString()
        val pointPos = dValue.indexOf(".")
        if (dstart <= pointPos) {
            return source
        }
        val splitArray = dValue.split("\\.").toTypedArray()
        if (splitArray.size > 1) {
            val dotValue = splitArray[1]
            val diff = if (TextUtils.isEmpty(dotValue)) 0 else dotValue.length + 1 - pointLength
            if (diff > 0) {
                try {
                    return source.subSequence(start, end - diff)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return ""
    }
}