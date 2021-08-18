package com.h.android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.jetbrains.annotations.NotNull

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe 针对kotlin使用recycler的适配器
 */
abstract class HRecyclerViewAdapter<V : ViewBinding, T> : RecyclerView.Adapter<HViewHolder<V>>() {

    private var mutableList: MutableList<T> = mutableListOf()
    private var hViewHolder: HViewHolder<V>? = null
    private var adapterViewListener: AdapterViewListener<V, T>? = null

    fun setAdapterViewListener(@NotNull listener: AdapterViewListener<V, T>) {
        this.adapterViewListener = listener
    }

    fun getAdapterViewListener(): AdapterViewListener<V, T>? {
        return adapterViewListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HViewHolder<V> {
        val view: V = createView(parent, viewType)
        val viewHolder: HViewHolder<V> = HViewHolder(view.root)
        viewHolder.setV(view)
        return viewHolder
    }

    abstract fun createView(parent: ViewGroup, viewType: Int): V

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: HViewHolder<V>, position: Int) {
        val entity = mutableList[position]
        bindItemViewHolder(holder.getV(), position, entity)
        bindViewListener(holder.getV(), position, entity)
    }

    /**
     * 绑定数据
     */
    fun bindData(refresh: Boolean, dataList: MutableList<T>) {
        if (refresh) {
            mutableList.clear()
        }
        mutableList.addAll(dataList)
        notifyDataSetChanged()
    }

    /**
     * 绑定监听事件
     */
    fun bindListener(holder: V, view: View, entity: T, pos: Int) {
        view.setOnClickListener {
            adapterViewListener?.viewListener(holder, view, entity, pos)
        }
    }

    abstract fun bindItemViewHolder(holder: V?, pos: Int, entity: T)

    abstract fun bindViewListener(holder: V?, pos: Int, entity: T)
}