package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.SlideEditViewHolder;
import com.inno.home.model.navigate.HomeModel;

import java.util.List;

public class NavigateHomeAdapter extends RecyclerView.Adapter<NavigateHomeAdapter.HomeItemViewHolder> {

    private List<HomeModel.HomeItemModel> itemModelList;

    public NavigateHomeAdapter(List<HomeModel.HomeItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    @Override
    public HomeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nv_home, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return itemModelList != null ? itemModelList.size() : 0;
    }

    public class HomeItemViewHolder extends SlideEditViewHolder {
        TextView home_name;

        public HomeItemViewHolder(View itemView) {
            super(itemView);
            home_name = itemView.findViewById(R.id.home_name);
            hideDeleteItem();
        }

        public void bind(int position) {
            home_name.setText(itemModelList.get(position).homeName);
        }
    }
}
