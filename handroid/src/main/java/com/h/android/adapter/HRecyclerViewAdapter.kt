package com.h.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
abstract class HRecyclerViewAdapter<V : ViewDataBinding, T> : RecyclerView.Adapter<HViewHolder<V>>() {

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
        val viewDataBinding: ViewDataBinding = createView(getLayoutId(), parent)
        hViewHolder = HViewHolder(viewDataBinding.root)
        hViewHolder!!.setV(viewDataBinding as V)
        return hViewHolder as HViewHolder<V>
    }

    private fun createView(@LayoutRes layout: Int, viewGroup: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), layout, viewGroup, false)
    }

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
        view.setOnClickListener { v ->
            adapterViewListener?.viewListener(holder, v, entity, pos);
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun bindItemViewHolder(holder: V?, pos: Int, entity: T)

    abstract fun bindViewListener(holder: V?, pos: Int, entity: T)
}