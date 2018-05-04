package com.inno.home.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.inno.home.adapter.delegate.AdapterDelegateManager;
import com.inno.home.model.DelegateModel;

import java.util.List;

/**
 * Created by liucheng on 2017/11/6.
 * Adapter适配基类
 */

public class DelegateAdapter<T extends DelegateModel> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_DIVIDER = 0x00;
    public static final int ITEM_TYPE_HEAD = 0x0F;
    public static final int ITEM_TYPE_FOOT = 0xF0;
    public static final int ITEM_TYPE_CONTENT = 0xFF;

    private AdapterDelegateManager<T, RecyclerView.ViewHolder> delegateManager;
    private List<T> dataList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (delegateManager == null) {
            throw new IllegalArgumentException("delegateManager not allow null! ");
        }
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (delegateManager == null) {
            throw new IllegalArgumentException("delegateManager not allow null! ");
        }
        delegateManager.onBindViewHolder(dataList, position, holder);
    }

    @Override
    public int getItemViewType(int position) {
        if (delegateManager == null) {
            throw new IllegalArgumentException("delegateManager not allow null! ");
        }
        return delegateManager.getItemViewType(dataList, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDelegateManager(AdapterDelegateManager<T, RecyclerView.ViewHolder> delegateManager) {
        this.delegateManager = delegateManager;
        delegateManager.setAdapter(this);
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == ITEM_TYPE_HEAD || viewType == ITEM_TYPE_FOOT || viewType == ITEM_TYPE_DIVIDER) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int viewType = getItemViewType(holder.getLayoutPosition());
        if (viewType == ITEM_TYPE_HEAD || viewType == ITEM_TYPE_FOOT || viewType == ITEM_TYPE_DIVIDER) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) params;
                layoutParams.setFullSpan(true);
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        delegateManager.finish();
        delegateManager = null;
    }
}
