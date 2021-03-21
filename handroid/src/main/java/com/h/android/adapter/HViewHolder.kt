package com.h.android.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
class HViewHolder<V : ViewBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var v: V? = null

    fun getV(): V? {
        return v
    }

    fun setV(v : V){
        this.v = v
    }
}