package com.h.android.utils

import android.text.TextUtils
import android.util.Log
import com.h.android.BuildConfig

/**
 *2020/11/20
 *@author zhangxiaohui
 *@describe
 */
object HLog {
    private val TAG = "com.bkex"

    fun e(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.e(TAG, text)
    }

    fun w(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.w(TAG, text)
    }

    fun d(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.d(TAG, text)
    }

    fun i(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.i(TAG, text)
    }
}