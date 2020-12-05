package com.h.android.http.interceptor

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 *2020/12/5
 *@author zhangxiaohui
 *@describe
 */
abstract class HttpExceptionFeedInterceptor : Interceptor {
    val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val startNs = System.nanoTime()
        val request = chain.request()
        val response: Response
        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            onFeedHttpException(request.url().toString(), request.method(), e)
            throw e
        }
        val tookMs =
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        //汇报异常
        if (response != null && !response.isSuccessful) {
            onFeedResponseException(
                request.url().toString(),
                request.method(),
                getLoggerRequestBody(request.body()),
                response.code(),
                response.message(),
                tookMs,
                getLoggerResponseBody(response.body())
            )
        }
        return response
    }

    /**
     * 异常汇报
     *
     * @param url
     * @param method
     * @param e
     */
    protected abstract fun onFeedHttpException(
        url: String?,
        method: String?,
        e: Throwable?
    )

    /**
     * 响应异常汇报
     *
     * @param url
     * @param method
     * @param reqBody
     * @param code
     * @param message
     * @param tookMs  消耗时间
     * @param resBody
     */
    protected abstract fun onFeedResponseException(
        url: String?,
        method: String?,
        reqBody: String?,
        code: Int,
        message: String?,
        tookMs: Long,
        resBody: String?
    )

    /**
     * 获取 响应体
     *
     * @param responseBody
     * @return
     */
    fun getLoggerResponseBody(responseBody: ResponseBody?): String? {
        if (responseBody != null) {
            try {
                val contentLength = responseBody.contentLength()
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (!isPlaintext(buffer)) {
                    return "binary " + buffer.size() + "-byte body omitted"
                }
                if (contentLength != 0L) {
                    return buffer.clone().readString(charset)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 获取请求体
     *
     * @param requestBody
     * @return
     */
    fun getLoggerRequestBody(requestBody: RequestBody?): String? {
        if (requestBody != null) {
            try {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                return if (isPlaintext(buffer)) {
                    buffer.readString(charset)
                } else {
                    "binary " + requestBody.contentLength() + "-byte body omitted"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    open fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(
                        codePoint
                    )
                ) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }
}