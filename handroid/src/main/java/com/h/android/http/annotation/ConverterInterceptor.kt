package com.h.android.http.annotation

import com.h.android.http.interceptor.ResponseConverterInterceptor
import okhttp3.Interceptor
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 *2021/9/23
 *@author zhangxiaohui
 *@describe
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class ConverterInterceptor(val value: KClass<out ResponseConverterInterceptor>) {
}