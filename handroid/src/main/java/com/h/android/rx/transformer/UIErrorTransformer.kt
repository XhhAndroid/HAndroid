package com.h.android.rx.transformer

import com.h.android.rx.UILifeTransformerImpl
import io.reactivex.functions.Consumer
import java.lang.Exception

/**
 *2021/9/16
 *@author zhangxiaohui
 *@describe
 */
class UIErrorTransformer<T>(val consumer: Consumer<in Throwable>) : UILifeTransformerImpl<T>() {

    override fun onSubscribe() {

    }

    override fun onNext(t: T?) {
    }

    override fun onComplete() {
    }

    override fun onError(throwable: Throwable?) {
        try {
            consumer.accept(throwable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCancel() {
    }
}