package com.xh.android.workermanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.h.android.utils.HLog
import com.h.android.utils.HToast

/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe
 */
class UpdateManager(context: Context, private val workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val value = workerParams.inputData.getString("key")
        for (i in 0..15) {
//            Thread.sleep(900)

            HLog.d("-->work.upLoad:$i$value")
            HToast.showToastSuccess("-->work.upLoad:$i$value")
        }
        val resultData = Data.Builder()
            .putString("resultKey","upLoad-FINIGHS")
            .build()
        return Result.success(resultData)
    }
}