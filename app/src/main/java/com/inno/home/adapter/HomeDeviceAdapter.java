package com.inno.home.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.model.DeviceModel;

import java.util.List;

/**
 * Created by lcheng on 2018/4/16.
 */

public class HomeDeviceAdapter extends RecyclerView.Adapter<HomeDeviceAdapter.DeviceViewHolder> {

    private List<DeviceModel> modelList;

    public HomeDeviceAdapter(List<DeviceModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_device, parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout cl_device_show_view;
        private ConstraintLayout cl_device_edit_view;
        private TextView tv_device_group, tv_device_num;
        private ImageView iv_device_edit, iv_device_delete, iv_device_add;

        DeviceViewHolder(View itemView) {
            super(itemView);
            cl_device_show_view = itemView.findViewById(R.id.cl_device_show_view);
            cl_device_edit_view = itemView.findViewById(R.id.cl_device_edit_view);
            tv_device_group = itemView.findViewById(R.id.tv_device_group);
            tv_device_num = itemView.findViewById(R.id.tv_device_num);
            iv_device_edit = itemView.findViewById(R.id.iv_device_edit);
            iv_device_delete = itemView.findViewById(R.id.iv_device_delete);
            iv_device_add = itemView.findViewById(R.id.iv_device_add);
        }

        public void bind(DeviceModel model) {
            if (model.isAddView) {
                cl_device_show_view.setVisibility(View.GONE);
                iv_device_add.setVisibility(View.VISIBLE);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.showDeviceType(view.getContext());
                    }
                });
                return;
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.showDeviceList(v.getContext());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (cl_device_edit_view.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition((ViewGroup) itemView);
                        cl_device_edit_view.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
            cl_device_edit_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition((ViewGroup) itemView);
                    cl_device_edit_view.setVisibility(View.GONE);
                }
            });
        }
    }
}
