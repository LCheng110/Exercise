package com.inno.home.model;

public class HomeDeviceModel extends DelegateModel {
    public String deviceName;
    public boolean isAddView;

    public HomeDeviceModel() {
    }

    public HomeDeviceModel(String deviceName) {
        this.deviceName = deviceName;
    }

    public HomeDeviceModel(boolean isAddView) {
        this.isAddView = isAddView;
    }
}
