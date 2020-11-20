package com.h.android.http;

import java.lang.annotation.ElementType;

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
@java.lang.annotation.Target({ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
@java.lang.annotation.Inherited
public @interface BaseUrlProvider {

    Class<? extends UrlProvider> value();
}
