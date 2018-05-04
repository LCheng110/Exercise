package com.inno.home.controller.device;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.inno.home.R;
import com.inno.home.adapter.DeviceTypeAdapter;
import com.inno.home.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class DeviceTypeActivity extends BaseActivity {

    @BindView(R.id.sv_device_search)
    SearchView sv_device_search;
    @BindView(R.id.rv_type_list)
    RecyclerView rv_type_list;
    List<String> typeList;
    DeviceTypeAdapter deviceTypeAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_device_type;
    }

    @Override
    protected void initValue() {
        typeList = Arrays.asList(getResources().getStringArray(R.array.device_type));
    }

    @Override
    protected void initView() {
        if (titleBar != null) {
            titleBar.setTitleText(R.string.main_text_device_add).setBackTextEvent(android.R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        rv_type_list.setLayoutManager(new LinearLayoutManager(context));
        rv_type_list.setAdapter(deviceTypeAdapter = new DeviceTypeAdapter(typeList));
    }

    @Override
    protected void initData() {
        sv_device_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                deviceTypeAdapter.filterType(newText);
                return false;
            }
        });
    }
}
