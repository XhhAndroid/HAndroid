package com.xh.android.workermanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.h.android.utils.HLog
import com.h.android.utils.HToast
import kotlinx.coroutines.delay

/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe
 */
class LogManager(context: Context, private val workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val value = workerParams.inputData.getString("key")
        for (i in 0..10) {
//            Thread.sleep(800)

            HLog.d("-->work.log:$i$value")
            HToast.showToastSuccess("-->work.log:$i$value")
        }
        val resultData = Data.Builder()
            .putString("resultKey","log->FINIGHS")
            .build()
        return Result.retry()
    }
}