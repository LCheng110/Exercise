package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        return itemModelList != null ? itemModelList.size() + 1 : 1;
    }

    public class HomeItemViewHolder extends SlideEditViewHolder {
        TextView home_name;
        ImageView iv_shared;

        public HomeItemViewHolder(View itemView) {
            super(itemView);
            home_name = itemView.findViewById(R.id.home_name);
            iv_shared = itemView.findViewById(R.id.iv_shared);
            hideDeleteItem();
        }

        public void bind(int position) {
            if (itemModelList == null || itemModelList.size() == position) {
                home_name.setText(R.string.main_text_home_add);
                iv_shared.setImageResource(R.drawable.ic_nv_home_add);
            } else {
                iv_shared.setImageResource(R.drawable.ic_nv_shared);
                home_name.setText(itemModelList.get(position).homeName);
            }
        }
    }
}
