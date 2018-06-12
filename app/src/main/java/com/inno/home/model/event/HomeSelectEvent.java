package com.inno.home.model.event;

import com.inno.home.model.navigate.HomeModel;

public class HomeSelectEvent {
    public HomeModel.HomeItemModel homeItemModel;

    public HomeSelectEvent(HomeModel.HomeItemModel homeItemModel) {
        this.homeItemModel = homeItemModel;
    }
}
