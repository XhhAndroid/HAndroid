package com.h.android.lifecycler

import androidx.lifecycle.LifecycleOwner

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
interface LifecycleOwnerProvider {
    fun getLifecycleOwner(): LifecycleOwner
}