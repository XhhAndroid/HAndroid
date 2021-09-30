package com.h.android.http.annotation

import com.h.android.http.interceptor.HttpCacheDirectoryProvider
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 *2020/12/5
 *@author zhangxiaohui
 *@describe
 */
@kotlin.annotation.Target
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class RxHttpCacheProvider(val value: KClass<out HttpCacheDirectoryProvider>) {

}