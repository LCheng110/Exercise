package com.inno.home.controller;

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
import com.inno.home.adapter.DelegateAdapter;
import com.inno.home.adapter.HomeDeviceAdapter;
import com.inno.home.adapter.delegate.AdapterDelegateManager;
import com.inno.home.adapter.delegate.navigation.NavigateContactDelegate;
import com.inno.home.adapter.delegate.navigation.NavigateHomeDelegate;
import com.inno.home.adapter.delegate.navigation.NavigateShareDelegate;
import com.inno.home.adapter.delegate.navigation.NavigateUserDelegate;
import com.inno.home.adapter.divide.GridItemDecoration;
import com.inno.home.base.BaseActivity;
import com.inno.home.dao.Session;
import com.inno.home.model.DelegateModel;
import com.inno.home.model.HomeDeviceModel;
import com.inno.home.model.navigate.ContactModel;
import com.inno.home.model.navigate.HomeModel;
import com.inno.home.model.navigate.ShareModel;
import com.inno.home.model.navigate.UserModel;

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
    @BindView(R.id.nv_menu)
    public RecyclerView nv_menu;
    @BindView(R.id.nv_bottom_logout)
    public TextView nv_bottom_logout;
    @BindView(R.id.nv_bottom_setting)
    public ImageView nv_bottom_setting;

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
        rv_device_list.addItemDecoration(new GridItemDecoration(context, R.color.white, getResources()
                .getDimensionPixelSize(R.dimen.item_divide_space)));
        rv_device_list.setAdapter(homeDeviceAdapter = new HomeDeviceAdapter(homeDeviceModelList));
    }

    @Override
    protected void initData() {
        List<DelegateModel> menuList = new ArrayList<>();
        menuList.add(new UserModel());
        HomeModel homeModel = new HomeModel();
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("sss"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("aaa"));
        homeModel.homeItemModels.add(new HomeModel.HomeItemModel("bbb"));
        menuList.add(homeModel);
        menuList.add(new ShareModel());
        menuList.add(new ContactModel());

        nv_menu.setLayoutManager(new LinearLayoutManager(this));
        AdapterDelegateManager delegateManager = new AdapterDelegateManager();
        delegateManager.addDelegate(new NavigateUserDelegate());
        delegateManager.addDelegate(new NavigateHomeDelegate());
        delegateManager.addDelegate(new NavigateShareDelegate());
        delegateManager.addDelegate(new NavigateContactDelegate());
        DelegateAdapter menuAdapter = new DelegateAdapter();
        menuAdapter.setDelegateManager(delegateManager);
        menuAdapter.setDataList(menuList);
        nv_menu.setAdapter(menuAdapter);
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
