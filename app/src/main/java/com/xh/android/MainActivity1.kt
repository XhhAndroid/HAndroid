package com.xh.android

import android.content.Intent
import android.os.Bundle
import com.h.android.HAndroid
import com.h.android.activity.HActivity
import com.h.android.utils.HLog
import com.h.android.utils.HToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *2021/8/12
 *@author zhangxiaohui
 *@describe
 */
class MainActivity1 : HActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val intent = Intent()
//        intent.putExtra("key", "返回成功")
//        setResult(RESULT_OK, intent)
//        finish()

        start()
    }

    private fun start() {
        val job = GlobalScope.launch {
            flowOf(1, 2, 3, 4, 5)
                .map { m ->
                    if (m == 3) {
                        val f = m / 0
                        HLog.d("kotlin--->3")
                    }
                    return@map m
                }.catch { e->
                    HAndroid.bindErrorToast(e)
                    emit(-1)
                }.map { n ->
                    HLog.d("kotlin--->$n")

                    return@map n
                }.collect{r->
                    HLog.d("kotlin--->result:$r")
                }
        }
        job.start()
    }
}