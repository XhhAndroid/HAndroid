package com.h.android.lifecycler

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
class FullLifecycleObserverAdapter(private val mObserver: FullLifecycleObserver) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> mObserver.onCreate()
            Lifecycle.Event.ON_START -> mObserver.onStart()
            Lifecycle.Event.ON_RESUME -> mObserver.onResume()
            Lifecycle.Event.ON_PAUSE -> mObserver.onPause()
            Lifecycle.Event.ON_STOP -> mObserver.onStop()
            Lifecycle.Event.ON_DESTROY -> mObserver.onDestroy()
            Lifecycle.Event.ON_ANY -> throw IllegalArgumentException("ON_ANY must not been send by anybody")
        }
    }
}