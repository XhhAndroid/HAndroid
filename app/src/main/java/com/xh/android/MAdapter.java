package com.xh.android;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.h.android.adapter.HRecyclerViewAdapter;
import com.h.android.adapter.HViewHolder;
import com.xh.android.databinding.AdapterLayoutBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 2020/11/27
 *
 * @author zhangxiaohui
 * @describe
 */
public class MAdapter extends HRecyclerViewAdapter<AdapterLayoutBinding, String> {

    @Override
    public void bindItemViewHolder(AdapterLayoutBinding holder, int position, String entity) {
        holder.textTv.setText(entity);
        holder.textImage.setText("------->" + entity);
    }

    @Override
    public void bindViewListener(@Nullable AdapterLayoutBinding holder, int pos, String entity) {
        bindListener(holder, holder.textTv, entity, pos);
        bindListener(holder, holder.textImage, entity, pos);
    }

    @NotNull
    @Override
    public AdapterLayoutBinding createView(@NotNull ViewGroup parent, int viewType) {
        return AdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }
}
