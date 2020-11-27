package com.h.android.lifecycler

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
interface FullLifecycleObserver {
    fun onCreate()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()
}