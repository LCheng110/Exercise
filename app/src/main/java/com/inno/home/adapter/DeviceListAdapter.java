package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.SlideEditViewHolder;
import com.inno.home.controller.dialog.DialogHelper;
import com.inno.home.dao.Session;
import com.inno.home.model.DeviceModel;
import com.inno.home.widget.WheelView;

import java.util.List;

import butterknife.BindView;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder> {

    private String[] onlineStatus = new String[]{
            "Online",
            "Offline"
    };
    private List<DeviceModel> deviceModelList;

    public DeviceListAdapter(List<DeviceModel> deviceModelList) {
        this.deviceModelList = deviceModelList;
    }

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
        return deviceModelList.size();
    }

    public class DeviceListViewHolder extends SlideEditViewHolder {
        @BindView(R.id.iv_device_icon)
        ImageView iv_device_icon;
        @BindView(R.id.tv_device_name)
        TextView tv_device_name;
        @BindView(R.id.tv_device_online)
        TextView tv_device_online;

        public DeviceListViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(final int position) {
            tv_device_online.setText(onlineStatus[deviceModelList.get(position).deviceStatus ? 0 : 1]);
            setEditText("Pin");
            setOnEditListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = View.inflate(v.getContext(), R.layout.dialog_wheel, null);
                    WheelView wheel_view = view.findViewById(R.id.wheel_view);
                    wheel_view.setItems(Session.getHomeDevice());
                    new DialogHelper().init(v.getContext()).setContentView(view).show();
                }
            });
        }
    }
}
