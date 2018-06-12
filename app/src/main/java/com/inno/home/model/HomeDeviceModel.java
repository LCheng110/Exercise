package com.inno.home.model;

import java.util.ArrayList;
import java.util.List;

public class HomeDeviceModel extends DelegateModel {
    public String deviceGroupName;
    public boolean isAddView;
    public List<HomeDeviceItemModel> deviceItemModelList = new ArrayList<>();

    public HomeDeviceModel() {
    }

    public HomeDeviceModel(String deviceGroupName) {
        this.deviceGroupName = deviceGroupName;
    }

    public HomeDeviceModel(boolean isAddView) {
        this.isAddView = isAddView;
    }

    public static class HomeDeviceItemModel {

        public String deviceName;
    }
}
