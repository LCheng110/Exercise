package com.inno.home.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Random;

import butterknife.BindView;

public class DeviceControlReportAdapter extends BaseRecycleAdapter {

    String[] strings = new String[]{
            "Low battery",
            "A leak is detected",
            "Reconnected"
    };

    Random random;

    public DeviceControlReportAdapter() {
        random = new Random();
    }

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
        return 3;
    }

    public class DeviceControlViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_report)
        TextView tv_report;

        public DeviceControlViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(int position) {
            String deviceStatus;
            if (position == getItemCount() - 1) {
                deviceStatus = "Connected";
            } else {
                deviceStatus = strings[random.nextInt(2)];
            }
            tv_report.setText(String.format(tv_report.getContext().getString(R.string.device_report_format),
                    getTimeFormat(System.currentTimeMillis() - 200_000_000 * (position + 1)), deviceStatus));
        }

        public String getTimeFormat(long time) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm,MM/dd/yyyy");
            return simpleDateFormat.format(time);
        }
    }
}
