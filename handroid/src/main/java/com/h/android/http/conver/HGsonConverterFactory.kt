package com.h.android.http.conver

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.h.android.http.interceptor.ResponseConverterInterceptor
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *2021/9/23
 *@author zhangxiaohui
 *@describe
 */
class HGsonConverterFactory(val responseInterceptor: ResponseConverterInterceptor?) : Converter.Factory() {
    private val gson by lazy {
        Gson()
    }

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody,*> {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return HGsonResponseBodyConverter(gson, adapter,retrofit,responseInterceptor)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation?>?, methodAnnotations: Array<Annotation?>?, retrofit: Retrofit?
    ): Converter<*, RequestBody> {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return HGsonRequestBodyConverter(gson, adapter)
    }

    override fun stringConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<*, String>? {
        return super.stringConverter(type, annotations, retrofit)
    }

}