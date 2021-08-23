package com.xh.android.workermanager

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import com.h.android.activity.HActivity
import com.h.android.utils.HLog
import com.xh.android.databinding.WorkerManagerLayoutBinding
import java.util.concurrent.TimeUnit


/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe
 */
class WorkerManagerActivity : HActivity() {
    private var binding: WorkerManagerLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WorkerManagerLayoutBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.workManager.setOnClickListener {
            startLogTask()
        }
        binding!!.workManagerCancel.setOnClickListener {
            //只能取消未开始的任务
            WorkManager.getInstance(this)
                .cancelAllWorkByTag("LogTask")
        }
    }

    private fun startLogTask() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(true)//设置是否充电的条件,默认false
            .setRequiresDeviceIdle(false)// 设置手机是否空闲的条件,默认false
            .setRequiredNetworkType(NetworkType.CONNECTED)//设置需要的网络条件，默认NETWORK_TYPE_NONE
            .setRequiresBatteryNotLow(true)
            .build()
        //且数据最大不能超过10kb
        val logData = Data.Builder()
            .putString("key", "logData")
            .build()
        val logWorkRequest = OneTimeWorkRequest.Builder(LogManager::class.java)
            .addTag("LogTask")
            .setInputData(logData)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)//设置重试策略
            .setConstraints(constraints)
            .build()
        //周期性任务的间隔时间不能小于15分钟。
        val logWorkRequest1 = PeriodicWorkRequest.Builder(LogManager::class.java, 15, TimeUnit.MINUTES)
            .addTag("LogTask")
            .setInputData(logData)
            .setConstraints(constraints)
            .build()

        val updateData = Data.Builder()
            .putString("key", "updateData")
            .build()
        val updateWorkRequest = OneTimeWorkRequest.Builder(UpdateManager::class.java)
            .addTag("UpdateTask")
            .setInputData(updateData)
            .setConstraints(constraints)
            .build()

        //定时执行相关任务
//        WorkManager.getInstance(this)
//            .enqueue(logWorkRequest1)
        //单个任务执行
//        WorkManager.getInstance(this)
//            .enqueue(updateWorkRequest)
        //串行执行
        WorkManager.getInstance(this)
            .beginWith(logWorkRequest)
            .then(updateWorkRequest)
            .enqueue()
        //多任务合并执行
//        val taskList: MutableList<WorkContinuation> = ArrayList()
//        val logWork = WorkManager.getInstance(this).beginWith(logWorkRequest)
//        val updateWork = WorkManager.getInstance(this).beginWith(updateWorkRequest)
//        taskList.add(logWork)
//        taskList.add(updateWork)
//        WorkContinuation.combine(taskList).enqueue()

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(logWorkRequest.id)
            .observe(this, Observer { data ->
                val result = data.outputData.getString("resultKey") ?: "-"
                HLog.d("-->work.log:$result")
            })
        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(updateWorkRequest.id)
            .observe(this, Observer { data ->
                val result = data.outputData.getString("resultKey") ?: "-"
                HLog.d("-->work.update:$result")
            })
    }
}