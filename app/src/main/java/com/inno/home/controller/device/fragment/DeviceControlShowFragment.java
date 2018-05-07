package com.inno.home.controller.device.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inno.home.R;
import com.inno.home.adapter.BaseRecycleAdapter;
import com.inno.home.adapter.DeviceControlContactAdapter;
import com.inno.home.adapter.DeviceControlReportAdapter;
import com.inno.home.base.BaseFragment;

import butterknife.BindView;

public class DeviceControlShowFragment extends BaseFragment {

    public static final String CONTROL_SHOW_TYPE = "CONTROL_SHOW_TYPE";
    public static final int CONTROL_SHOW_CONTACT = 0;
    public static final int CONTROL_SHOW_REPORT = 1;

    @BindView(R.id.rv_contact_list)
    RecyclerView rv_contact_list;

    private int controlShowType;
    private BaseRecycleAdapter controlShowAdapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_device_contact;
    }

    @Override
    protected void initValue() {
        controlShowType = getArguments().getInt(CONTROL_SHOW_TYPE);
    }

    @Override
    protected void initView() {
        rv_contact_list.setLayoutManager(new LinearLayoutManager(getContext()));
        if (controlShowType == CONTROL_SHOW_CONTACT) {
            controlShowAdapter = new DeviceControlContactAdapter();
        } else {
            controlShowAdapter = new DeviceControlReportAdapter();
        }
        rv_contact_list.setAdapter(controlShowAdapter);

    }

    @Override
    protected void initData() {

    }
}
