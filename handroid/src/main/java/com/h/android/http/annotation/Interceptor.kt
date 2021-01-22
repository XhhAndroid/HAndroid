package com.h.android.http.annotation

import okhttp3.Interceptor
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * 请求拦截器
 *2020/12/5
 *@author zhangxiaohui
 *@describe
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class Interceptor(val value: Array<KClass<out Interceptor>>) {

}