package com.h.android.presenter

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.h.android.HAndroid
import com.h.android.lifecycler.FullLifecycleObserver
import com.h.android.lifecycler.FullLifecycleObserverAdapter
import com.h.android.lifecycler.LifecyclePresenter
import java.util.*

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
open class HPresenter<V>(private val lifecycleOwner: LifecycleOwner, private val view: V?) : LifecyclePresenter<V>,
    FullLifecycleObserver {
    private var lifecycleObserverAdapter : FullLifecycleObserverAdapter? = null

   init {
       if (lifecycleObserverAdapter != null) {
           lifecycleOwner.lifecycle.removeObserver(lifecycleObserverAdapter!!)
       } else {
           lifecycleObserverAdapter = FullLifecycleObserverAdapter(this)
           //默认注册观察者
           lifecycleOwner.lifecycle.addObserver(lifecycleObserverAdapter!!)
       }
   }

    override fun <T : Application?> getApplication(): T {
        return HAndroid.getApplication() as T
    }

    override fun <T : Context?> getContext(): T? {
        if (lifecycleOwner is Activity) {
            return lifecycleOwner as T
        } else if (lifecycleOwner is Fragment) {
            return lifecycleOwner.context as T
        }
        return null
    }

    override fun getView(): V? {
        return view
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return lifecycleOwner
    }

    override fun onCreate() {
        
    }

    override fun onStart() {
        
    }

    override fun onResume() {
        
    }

    override fun onPause() {
        
    }

    override fun onStop() {
        
    }

    override fun onDestroy() {
        
    }

}