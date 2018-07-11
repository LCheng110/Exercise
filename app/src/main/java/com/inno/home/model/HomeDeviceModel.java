package com.inno.home.model;

import java.util.ArrayList;
import java.util.List;

public class HomeDeviceModel extends DelegateModel {
    public String deviceGroupName;
    public boolean isAddView;
    public int deviceNum;
    public List<HomeDeviceItemModel> deviceItemModelList = new ArrayList<>();

    public HomeDeviceModel() {
    }

    public HomeDeviceModel(String deviceGroupName) {
        this.deviceGroupName = deviceGroupName;
        this.deviceNum = 0;
    }

    public HomeDeviceModel(String deviceGroupName, int deviceNum) {
        this.deviceGroupName = deviceGroupName;
        this.deviceNum = deviceNum;
    }

    public HomeDeviceModel(boolean isAddView) {
        this.isAddView = isAddView;
    }

    public static class HomeDeviceItemModel {

        public String deviceName;
    }
}
