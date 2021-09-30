package com.h.android.http.annotation

import com.h.android.http.UrlProvider
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * 2020/12/5
 *绑定域名/主要路由,优先级高
 * @author zhangxiaohui
 * @describe
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class BaseUrlProvider(val value: KClass<out UrlProvider>)