package com.h.android.loadding

/**
 * 2021/3/21
 *
 * @author zhangxiaohui
 * @describe
 */
interface ProgressProvider<T : ProgressListener?> {
    fun progressView(): T
}