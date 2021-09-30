package com.h.android.utils

import android.content.Context
import androidx.annotation.StringRes
import com.h.android.HAndroid

/**
 *2021/9/16
 *@author zhangxiaohui
 *@describe
 */
object ResourceUtil {

    private fun getContext(): Context {
        return HAndroid.getApplication()
    }

    fun getString(@StringRes resId: Int): String {
        return getContext().getString(resId)
    }
}