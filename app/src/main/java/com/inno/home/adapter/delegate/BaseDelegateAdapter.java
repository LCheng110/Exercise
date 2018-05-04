package com.inno.home.adapter.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inno.home.model.DelegateModel;

import java.util.List;

public abstract class BaseDelegateAdapter implements AdapterDelegate<DelegateModel,
        BaseDelegateAdapter.BaseViewHolder> {

    public AdapterDelegateManager delegateManager;

    @Override
    public boolean isForViewType(@NonNull List<DelegateModel> items, int position) {
        return items.get(position).ITEM_TYPE == getItemViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull List<DelegateModel> items, int position, @NonNull BaseViewHolder holder) {
        holder.bind(items.get(position));
    }

    @Override
    public void setManager(AdapterDelegateManager manager) {
        delegateManager = manager;
    }

    public abstract class BaseViewHolder<M extends DelegateModel> extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(M model);
    }
}
