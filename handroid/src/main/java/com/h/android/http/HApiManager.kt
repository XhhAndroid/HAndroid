package com.h.android.http

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
class HApiManager private constructor() {

    fun <T> getApiService(apiClazz: Class<T>?): T {
        return Hhttp.getApiService(apiClazz)
    }

    companion object {
        private var apiManager: HApiManager? = null
        fun get(): HApiManager {
            if (apiManager == null) {
                apiManager = HApiManager()
            }
            return apiManager!!
        }
    }

    /**
     * 简单的同步请求
     */
    fun getRequestSync(url: String): ResponseBody? {
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        val response = okHttpClient.newCall(request).execute()
        return (if (!response.isSuccessful || response.body() == null) {
            null
        } else {
            return response.body()!!
        })
    }

    /**
     * 简单异步请求
     */
    fun getRequestAsync(url: String, callback: Callback) {
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(callback)
    }
}