package com.h.android.http.interceptor

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Retrofit

/**
 *2021/9/25
 *@author zhangxiaohui
 *@describe
 */
interface ResponseConverterInterceptor {

    fun <T> converter(gson: Gson, adapter: TypeAdapter<T>,
                      value: ResponseBody,retrofit: Retrofit?): T?
}