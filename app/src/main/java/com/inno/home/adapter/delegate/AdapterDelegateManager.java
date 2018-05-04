package com.inno.home.adapter.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.inno.home.adapter.DelegateAdapter;
import com.inno.home.model.DelegateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liucheng on 2017/11/6.
 * AdapterDelegate所有adapter调度类的管理者
 */

public class AdapterDelegateManager<T extends DelegateModel, H extends RecyclerView.ViewHolder> {
    private SparseArray<AdapterDelegate<T, H>> delegateSparseArray = new SparseArray<>();
    private RecyclerView.Adapter adapter;

    private List<Integer> newsTypeList = new ArrayList<>();

    public AdapterDelegateManager() {
    }

    public AdapterDelegateManager<T, H> addDelegate(@NonNull AdapterDelegate<T, H> delegate) {
        delegate.setManager(this);
        delegateSparseArray.put(delegate.getItemViewType(), delegate);
        newsTypeList.add(delegate.getItemViewType());
        return this;
    }

    public int getItemViewType(@NonNull List<T> items, int position) {
        for (int i = 0; i < delegateSparseArray.size(); i++) {
            AdapterDelegate<T, H> adapterDelegate = delegateSparseArray.valueAt(i);
            if (adapterDelegate.isForViewType(items, position)) {
                return adapterDelegate.getItemViewType();
            }
        }
        return DelegateAdapter.ITEM_TYPE_CONTENT;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate<T, H> adapter = delegateSparseArray.get(viewType);
        return delegateSparseArray.get(viewType).onCreateViewHolder(parent);
    }

    public void onBindViewHolder(@NonNull List<T> items, int position, @NonNull H viewHolder) {
        for (int i = 0; i < delegateSparseArray.size(); i++) {
            AdapterDelegate<T, H> adapterDelegate = delegateSparseArray.valueAt(i);
            if (adapterDelegate.isForViewType(items, position)) {
                adapterDelegate.onBindViewHolder(items, position, viewHolder);
                break;
            }
        }
    }

    public List<Integer> getNewsTypeList() {
        return newsTypeList;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public SparseArray<AdapterDelegate<T, H>> getDelegateSparseArray() {
        return delegateSparseArray;
    }

    public void notifyItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    public void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        adapter.notifyItemMoved(fromPosition, toPosition);
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void finish() {
        for (int i = 0; i < delegateSparseArray.size(); i++) {
            AdapterDelegate<T, H> adapterDelegate = delegateSparseArray.valueAt(i);
            adapterDelegate.setManager(null);
        }
        delegateSparseArray.clear();
        adapter = null;
    }
}
