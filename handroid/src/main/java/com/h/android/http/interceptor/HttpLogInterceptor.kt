package com.h.android.http.interceptor

import com.h.android.utils.HLog
import okhttp3.*
import okhttp3.Interceptor
import okio.Buffer
import java.io.IOException
import java.lang.String
import java.nio.charset.Charset


/**
 *2020/12/5
 * http日志打印
 *@author zhangxiaohui
 *@describe
 */
class HttpLogInterceptor : Interceptor {
    private val UTF8 = "utf-8"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)

        HLog.i(
            String.format(
                "...\n请求链接：%s\n请求头：%s\n请求参数：%s\n请求响应%s",
                original.url(),
                getRequestHeaders(original),
                getRequestInfo(original),
                getResponseInfo(response)
            )
        )
        return response
    }

    /**
     * 打印请求头
     *
     * @param request 请求的对象
     */
    private fun getRequestHeaders(request: Request): kotlin.String? {
        val str = ""
        if (request == null) {
            return str
        }
        val headers: Headers = request.headers() ?: return str
        return headers.toString()
    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private fun getRequestInfo(request: Request): kotlin.String? {
        var str = ""
        val requestBody: RequestBody = request.body() ?: return str
        try {
            val bufferedSink = Buffer()
            requestBody.writeTo(bufferedSink)
            val charset: Charset = Charset.forName(UTF8)
            str = bufferedSink.readString(charset)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return str
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private fun getResponseInfo(response: Response?): kotlin.String? {
        var str = ""
        if (response == null || !response.isSuccessful) {
            return str
        }
        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()
        val source = responseBody?.source()
        try {
            source?.request(Long.MAX_VALUE) // Buffer the entire body.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val buffer: Buffer = source!!.buffer()
        val charset: Charset = Charset.forName(UTF8)
        if (contentLength != 0L) {
            str = buffer.clone().readString(charset)
        }
        return str
    }
}