package com.xh.android;

import com.h.android.adapter.HRecyclerViewAdapter;
import com.xh.android.databinding.AdapterLayoutBinding;

import org.jetbrains.annotations.Nullable;

/**
 * 2020/11/27
 *
 * @author zhangxiaohui
 * @describe
 */
public class MAdapter extends HRecyclerViewAdapter<AdapterLayoutBinding, String> {


    @Override
    public int getLayoutId() {
        return R.layout.adapter_layout;
    }


    @Override
    public void bindItemViewHolder(@Nullable AdapterLayoutBinding holder, int position, String entity) {
        holder.textTv.setText(entity);
        holder.textImage.setText("------->" + entity);
    }

    @Override
    public void bindViewListener(@Nullable AdapterLayoutBinding holder, int pos, String entity) {
        bindListener(holder, holder.textTv, entity, pos);
        bindListener(holder, holder.textImage, entity, pos);
    }
}
