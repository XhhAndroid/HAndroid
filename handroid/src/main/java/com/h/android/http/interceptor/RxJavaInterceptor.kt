package com.ql.miye.net

import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 *2020/12/5
 *@author zhangxiaohui
 *@describe
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class RxJavaInterceptor(val value: Array<KClass<out RxJavaCallAdapterInterceptor>>) {

}