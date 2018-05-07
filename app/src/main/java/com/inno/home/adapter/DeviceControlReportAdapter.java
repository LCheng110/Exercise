package com.inno.home.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;

public class DeviceControlReportAdapter extends BaseRecycleAdapter {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceControlViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device_control_report, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class DeviceControlViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_report)
        TextView tv_report;

        public DeviceControlViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(int position) {

        }
    }
}
