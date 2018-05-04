package com.inno.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.adapter.viewholder.SlideEditViewHolder;
import com.inno.home.listen.click.OnItemClickListener;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder> {

    private OnItemClickListener onItemClickListener;

    @Override
    public DeviceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device_list, parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class DeviceListViewHolder extends SlideEditViewHolder {
        TextView tv_device_name;

        public DeviceListViewHolder(View itemView) {
            super(itemView);
            tv_device_name = itemView.findViewById(R.id.tv_device_name);
        }

        public void bind(final int position) {
//            tv_device_name.setText(String.valueOf(position));
            tv_device_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }
    }
}
