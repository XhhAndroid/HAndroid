package com.ql.miye.net

import retrofit2.Call

/**
 *2020/12/5
 *@author zhangxiaohui
 *@describe
 */
interface RxJavaCallAdapterInterceptor {
    /**
     * @param call
     * @param args
     * @param rxJavaObservable 返回的rxJava Observable,Flowable...
     * @return 返回对应的 Object rxJavaObservable
     */
    fun adapt(
        call: Call<*>?,
        args: Array<Any?>?,
        rxJavaObservable: Any?
    ): Any?
}