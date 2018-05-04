package com.inno.home.controller.device;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.inno.home.DeviceListAdapter;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.listen.click.OnItemClickListener;
import com.inno.home.widget.SlideEditRecycleView;

import butterknife.BindView;

public class DeviceListActivity extends BaseActivity {

    @BindView(R.id.rv_device_list)
    SlideEditRecycleView rv_device_list;
    LinearLayoutManager layoutManager;
    DeviceListAdapter adapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_device_list;
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initView() {
        rv_device_list.setLayoutManager(layoutManager = new LinearLayoutManager(context));
        rv_device_list.setAdapter(adapter = new DeviceListAdapter());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
