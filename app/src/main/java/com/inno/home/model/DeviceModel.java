package com.inno.home.model;

public class DeviceModel extends DelegateModel {
    public String deviceName;
    public boolean isAddView;

    public DeviceModel() {
    }

    public DeviceModel(boolean isAddView) {
        this.isAddView = isAddView;
    }
}
