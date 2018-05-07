package com.inno.home.controller.device;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.CategoryFragmentAdapter;
import com.inno.home.base.BaseActivity;
import com.inno.home.base.BaseFragment;
import com.inno.home.controller.device.fragment.DeviceControlShowFragment;
import com.inno.home.utils.UiUtil;
import com.inno.home.widget.xtablayout.XTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class DeviceControlActivity extends BaseActivity {

    public static final String DEVICE_TITLE = "DEVICE_TITLE";
    public static final String DEVICE_STATUS = "DEVICE_STATUS";
    public static final int DEVICE_STATUS_ONLINE = 0;
    public static final int DEVICE_STATUS_WARN = 1;
    public static final int DEVICE_STATUS_OFFLINE = 2;

    @BindView(R.id.control_status_bg)
    ConstraintLayout control_status_bg;
    @BindView(R.id.tv_control_prompt)
    TextView tv_control_prompt;
    @BindView(R.id.iv_control_icon)
    ImageView iv_control_icon;
    @BindView(R.id.tv_control_status)
    TextView tv_control_status;
    @BindView(R.id.tab_layout)
    XTabLayout tab_layout;
    @BindView(R.id.vp)
    ViewPager viewPager;
    List<BaseFragment> fragmentList = new ArrayList<>();
    List<String> tabList = Arrays.asList("Contacts", "Reports");
    String deviceTitle;
    int deviceStatus;
    private DeviceControlShowFragment contactFragment;
    private DeviceControlShowFragment reportFragment;
    private CategoryFragmentAdapter categoryFragmentAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_device_control;
    }

    @Override
    protected void initValue() {
        deviceTitle = getIntent().getStringExtra(DEVICE_TITLE);
        deviceStatus = getIntent().getIntExtra(DEVICE_STATUS, DEVICE_STATUS_ONLINE);
    }

    @Override
    protected void initView() {
        contactFragment = new DeviceControlShowFragment();
        Bundle contactBundle = new Bundle();
        contactBundle.putInt(DeviceControlShowFragment.CONTROL_SHOW_TYPE, DeviceControlShowFragment.CONTROL_SHOW_CONTACT);
        contactFragment.setArguments(contactBundle);
        fragmentList.add(contactFragment);

        reportFragment = new DeviceControlShowFragment();
        Bundle reportBundle = new Bundle();
        reportBundle.putInt(DeviceControlShowFragment.CONTROL_SHOW_TYPE, DeviceControlShowFragment.CONTROL_SHOW_REPORT);
        reportFragment.setArguments(reportBundle);
        fragmentList.add(reportFragment);

        categoryFragmentAdapter = new CategoryFragmentAdapter(getSupportFragmentManager(),
                fragmentList, tabList);
        viewPager.setAdapter(categoryFragmentAdapter);
        for (String tabTile : tabList) {
            tab_layout.addTab(tab_layout.newTab().setText(tabTile));
        }
        tab_layout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        if (titleBar != null) {
            int titleColor;
            int showBgColor;
            titleBar.setTitleText(deviceTitle);
            if (deviceStatus == DEVICE_STATUS_ONLINE) {
                titleColor = ContextCompat.getColor(context, R.color.device_control_online);
                showBgColor = ContextCompat.getColor(context, R.color.device_control_online_bg);
                tv_control_status.setText(R.string.device_status_online);
            } else if (deviceStatus == DEVICE_STATUS_WARN) {
                titleColor = ContextCompat.getColor(context, R.color.device_control_warn);
                showBgColor = ContextCompat.getColor(context, R.color.device_control_warn_bg);
                tv_control_status.setText(R.string.device_status_alarm);
            } else {
                titleColor = ContextCompat.getColor(context, R.color.device_control_offline);
                showBgColor = ContextCompat.getColor(context, R.color.device_control_offline_bg);
                tv_control_status.setText(R.string.device_status_offline);
            }
            control_status_bg.setBackgroundColor(showBgColor);
            titleBar.setBarBackground(titleColor);
            tab_layout.setBackgroundColor(titleColor);
            UiUtil.setStatusBarColor(context, titleColor);
        }
    }
}
