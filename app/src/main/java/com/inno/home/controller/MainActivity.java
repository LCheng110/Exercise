package com.inno.home.controller;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.adapter.HomeDeviceAdapter;
import com.inno.home.adapter.NavigateHomeAdapter;
import com.inno.home.base.BaseActivity;
import com.inno.home.dao.Session;
import com.inno.home.model.HomeDeviceModel;
import com.inno.home.model.navigate.HomeModel;
import com.inno.home.widget.SlideEditRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lcheng on 2018/4/9.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_navigation)
    public DrawerLayout drawerDevice;
    @BindView(R.id.rv_device_list)
    public RecyclerView rv_device_list;
    @BindView(R.id.nv_bottom_logout)
    public TextView nv_bottom_logout;
    @BindView(R.id.nv_bottom_setting)
    public ImageView nv_bottom_setting;

    // 导航栏user布局
    @BindView(R.id.user_view)
    public ConstraintLayout user_view;
    @BindView(R.id.user_avatar)
    public ImageView user_avatar;
    @BindView(R.id.user_message)
    public ImageView user_message;
    @BindView(R.id.user_name)
    public TextView user_name;

    // 导航栏用home局
    @BindView(R.id.home_title_view)
    public ConstraintLayout home_title_view;
    @BindView(R.id.iv_nv_flag)
    public ImageView iv_nv_flag;
    @BindView(R.id.rv_home_list)
    public SlideEditRecycleView rv_home_list;

    // 导航栏share布局
    @BindView(R.id.share_view)
    public ConstraintLayout share_view;
    @BindView(R.id.share_source)
    public TextView share_source;

    // 导航栏contact布局
    @BindView(R.id.contact_view)
    public ConstraintLayout contact_view;


    List<HomeDeviceModel> homeDeviceModelList = new ArrayList<>();

    private HomeDeviceAdapter homeDeviceAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initValue() {
        if (homeDeviceModelList.size() == 0) {
            homeDeviceModelList.add(new HomeDeviceModel("Everything"));
            homeDeviceModelList.add(new HomeDeviceModel(true));
        }
        List<String> deviceNameList = new ArrayList<>();
        for (HomeDeviceModel homeDeviceModel : homeDeviceModelList) {
            if (!TextUtils.isEmpty(homeDeviceModel.deviceName)) {
                deviceNameList.add(homeDeviceModel.deviceName);
            }
        }
        Session.seHomeDevice(deviceNameList);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerDevice, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
//                clickVibrator();
//                drawerAdapter.refreshHeadImage();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerDevice.addDrawerListener(mDrawerToggle);
        //初始化设备列表
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_device_list.setLayoutManager(layoutManager);
//        rv_device_list.addItemDecoration(new GridItemDecoration(context, R.color.white, UiUtil.dip2px(20)));
        rv_device_list.setAdapter(homeDeviceAdapter = new HomeDeviceAdapter(homeDeviceModelList));
    }

    @Override
    protected void initData() {
        HomeModel homeModel = new HomeModel();
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("sss"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("aaa"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("bbb"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("bbb"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("bbb"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("bbb"));

        // 用户布局内容初始化
//        ImageLoader.loadCircleImage(user_avatar.getContext(), model.userAvatar, user_avatar);
//        user_name.setText(model.userName);
        user_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.showPerson(v.getContext());
            }
        });

        // home布局内容初始化
        home_title_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                rv_home_list.setVisibility(v.isSelected() ? View.VISIBLE : View.GONE);
                iv_nv_flag.setRotation(v.isSelected() ? 90f : 0);
            }
        });
        rv_home_list.setVisibility(View.VISIBLE);
        rv_home_list.setLayoutManager(new LinearLayoutManager(rv_home_list.getContext()));
        rv_home_list.setAdapter(new NavigateHomeAdapter(homeModel.homeItemModels));
        rv_home_list.setNestedScrollingEnabled(false);
        rv_home_list.setSingle(true);

        // share布局内容初始化
//        share_source.setText(model.shareName);
        share_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.showSharedDetail(v.getContext());
            }
        });

        // contact布局内容初始化
        contact_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.showContactUs(v.getContext());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Navigation.showProductType(context);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
