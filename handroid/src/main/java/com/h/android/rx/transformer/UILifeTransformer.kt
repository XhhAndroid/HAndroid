package com.h.android.rx.transformer

import androidx.annotation.UiThread

/**
 *2021/3/21
 *@author zhangxiaohui
 *@describe
 */
interface UILifeTransformer<T> {

    /**
     * 开始执行
     */
    @UiThread
    fun onSubscribe()

    /**
     * 接收到数据(可能多次)执行
     *
     * @param t
     */
    @UiThread
    fun onNext(t: T?)

    /**
     * 执行结束
     */
    @UiThread
    fun onComplete()

    /**
     * 执行失败
     *
     * @param throwable
     */
    @UiThread
    fun onError(throwable: Throwable?)

    /**
     * 执行取消
     */
    @UiThread
    fun onCancel()
}