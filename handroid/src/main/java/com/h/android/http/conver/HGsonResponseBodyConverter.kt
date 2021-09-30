package com.h.android.http.conver

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.h.android.http.interceptor.ResponseConverterInterceptor
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException

/**
 *2021/9/23
 *@author zhangxiaohui
 *@describe
 */
class HGsonResponseBodyConverter<T>(
    val gson: Gson, val adapter: TypeAdapter<T>,val retrofit: Retrofit?,
    val responseInterceptor: ResponseConverterInterceptor?
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        if(responseInterceptor != null){
            return responseInterceptor.converter(gson,adapter,value,retrofit)
        }

        return try {
            val jsonReader = gson.newJsonReader(value.charStream())
            adapter.read(jsonReader)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            value.close()
        }
    }
}