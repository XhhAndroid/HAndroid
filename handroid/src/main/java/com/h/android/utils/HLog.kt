package com.h.android.utils

import android.text.TextUtils
import android.util.Log
import com.h.android.BuildConfig
import com.h.android.HAndroid

/**
 *2020/11/20
 *@author zhangxiaohui
 *@describe
 */
object HLog {
    private val TAG = "Hlog->"

    fun e(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!HAndroid.isDebug()) {
            return
        }
        Log.e(TAG, text)
    }

    fun w(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!HAndroid.isDebug()) {
            return
        }
        Log.w(TAG, text)
    }

    fun d(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!HAndroid.isDebug()) {
            return
        }
        Log.d(TAG, text)
    }

    fun i(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!HAndroid.isDebug()) {
            return
        }
        Log.i(TAG, text)
    }
}