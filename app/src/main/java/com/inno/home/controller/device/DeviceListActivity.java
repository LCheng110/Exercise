package com.inno.home.controller.device;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.inno.home.Navigation;
import com.inno.home.adapter.DeviceListAdapter;
import com.inno.home.R;
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
        deviceModelList.add(new DeviceModel());
        deviceModelList.add(new DeviceModel());
        deviceModelList.add(new DeviceModel());
        deviceModelList.add(new DeviceModel());
        deviceModelList.add(new DeviceModel());
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
        rv_device_list.setOnItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
