package com.h.android.adapter

import android.view.View
import androidx.viewbinding.ViewBinding

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
interface AdapterViewListener<V : ViewBinding, T> {
    fun viewListener(holder: V, view: View, t: T, pos: Int)
}