package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.listen.click.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceTypeAdapter extends RecyclerView.Adapter<DeviceTypeAdapter.DeviceTypeViewHolder> {

    private List<String> typeList;
    private List<String> filterList;
    private OnItemClickListener itemClickListener;

    public DeviceTypeAdapter(List<String> typeList) {
        this.typeList = typeList;
        filterList = new ArrayList<>(typeList);
    }

    @Override
    public DeviceTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceTypeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device_type, parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceTypeViewHolder holder, int position) {
        holder.bind(filterList.get(position));
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return filterList != null ? filterList.size() : 0;
    }

    public void filterType(String name) {
        filterList.clear();
        for (int i = 0; i < typeList.size(); i++) {
            if (typeList.get(i).toLowerCase().contains(name.toLowerCase())) {
                filterList.add(typeList.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public class DeviceTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type_name;

        public DeviceTypeViewHolder(View itemView) {
            super(itemView);
            tv_type_name = itemView.findViewById(R.id.tv_type_name);
        }

        public void bind(final String name) {
            tv_type_name.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, typeList.indexOf(name));
                    }
                }
            });
        }
    }
}
