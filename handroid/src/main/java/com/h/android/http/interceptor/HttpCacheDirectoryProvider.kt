package com.h.android.http.interceptor

/**
 *2020/12/5
 *@author zhangxiaohui
 *@describe
 */
interface HttpCacheDirectoryProvider {

    /**
     * 缓存目录 唯一标签 最好区分uid 或者token
     *
     * @return
     */
    fun getDirectory(): String

    /**
     * 最大缓存空间
     * @return
     */
    fun maxSize(): Long
}