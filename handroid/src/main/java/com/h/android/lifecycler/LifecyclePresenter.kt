package com.h.android.lifecycler

import android.app.Application
import android.content.Context
import androidx.annotation.CheckResult

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
interface LifecyclePresenter<V> : LifecycleOwnerProvider{
    fun <T : Application?> getApplication(): T


    fun <T : Context?> getContext(): T?

    /**
     * 获取视图层
     *
     * @return
     */
    @CheckResult
    fun getView(): V?
}