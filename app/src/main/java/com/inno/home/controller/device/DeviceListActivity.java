package com.inno.home.controller.device;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.inno.home.Navigation;
import com.inno.home.adapter.DeviceListAdapter;
import com.inno.home.R;
import com.inno.home.adapter.divide.MarginStartItemDecoration;
import com.inno.home.base.BaseActivity;
import com.inno.home.listen.click.OnItemClickListener;
import com.inno.home.model.DeviceModel;
import com.inno.home.widget.SlideEditRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DeviceListActivity extends BaseActivity {

    public static final String HOME_TITLE = "HOME_TITLE";

    @BindView(R.id.rv_device_list)
    SlideEditRecycleView rv_device_list;
    LinearLayoutManager layoutManager;
    DeviceListAdapter adapter;
    private String mHomeTile;
    private List<DeviceModel> deviceModelList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_device_list;
    }

    @Override
    protected void initValue() {
        mHomeTile = getIntent().getStringExtra(HOME_TITLE);
        if (getString(R.string.main_text_device_name_default).equals(mHomeTile)) {
            deviceModelList.add(new DeviceModel());
            deviceModelList.add(new DeviceModel());
            deviceModelList.get(0).deviceStatus = DeviceControlActivity.DEVICE_STATUS_ONLINE;
            deviceModelList.get(1).deviceStatus = DeviceControlActivity.DEVICE_STATUS_OFFLINE;
//        deviceModelList.get(2).deviceStatus = DeviceControlActivity.DEVICE_STATUS_WARN;
        }
    }

    @Override
    protected void initView() {
        if (titleBar != null) {
            titleBar.setTitleText(mHomeTile).setRightEvent(R.drawable.ic_nv_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.showProductType(context);
                }
            });
        }
        rv_device_list.setLayoutManager(layoutManager = new LinearLayoutManager(context));
        rv_device_list.setAdapter(adapter = new DeviceListAdapter(deviceModelList));
        MarginStartItemDecoration itemDecoration = new MarginStartItemDecoration(context, R.color.colorLine,
                getResources().getDimensionPixelSize(R.dimen.line_width));
        itemDecoration.setmMarginStart(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        rv_device_list.addItemDecoration(itemDecoration);
        rv_device_list.setOnItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Navigation.showDeviceControl(context, deviceModelList.get(position).deviceName,
                        deviceModelList.get(position).deviceStatus);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
