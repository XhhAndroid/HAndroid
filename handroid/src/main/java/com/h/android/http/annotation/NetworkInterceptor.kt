package com.h.android.http.annotation

import okhttp3.Interceptor
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 *2021/1/22
 *@author zhangxiaohui
 *@describe
 */
@kotlin.annotation.Target
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class NetworkInterceptor(val value : Array<KClass<out Interceptor>>) {
}