package com.inno.home.adapter;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.BaseViewHolder;
import com.inno.home.dao.Session;
import com.inno.home.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class DeviceControlContactAdapter extends BaseRecycleAdapter {

    List<String> contactList;

    public DeviceControlContactAdapter(List<String> contactList) {
        this.contactList = contactList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceControlViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device_control_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 2 : contactList.size() + 2;
    }

    public class DeviceControlViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_contact_name)
        TextView tv_contact_name;
        @BindView(R.id.iv_contact_remove)
        ImageView iv_contact_remove;
        @BindView(R.id.ll_add_view)
        LinearLayout ll_add_view;
        @BindView(R.id.et_add_name)
        EditText et_add_name;
        @BindView(R.id.et_add_email)
        EditText et_add_email;
        @BindView(R.id.iv_add_icon)
        ImageView iv_add_icon;

        public DeviceControlViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(final int position) {
            ll_add_view.setVisibility(View.GONE);
            if (position == 0) {
                tv_contact_name.setText(Session.getEmail());
                iv_contact_remove.setVisibility(View.GONE);
            } else if (position == getItemCount() - 1) {
                ll_add_view.setVisibility(View.VISIBLE);
                iv_add_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(et_add_name.getText()) && !TextUtils.isEmpty(et_add_email.getText())) {
                            contactList.add(0, String.format(v.getContext().getString(R.string.device_contact_format),
                                    et_add_email.getText().toString(), et_add_name.getText().toString()));
                            notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast("请填写完整姓名和邮箱信息");
                        }
                    }
                });
            } else {
                tv_contact_name.setText(contactList.get(position - 1));
                iv_contact_remove.setVisibility(View.VISIBLE);
                iv_contact_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contactList.remove(position - 1);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
