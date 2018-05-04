package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;

import com.inno.home.adapter.viewholder.BaseViewHolder;
import com.inno.home.listen.click.OnItemClickListener;

public abstract class BaseRecycleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
