package com.inno.home.model.event;

import com.inno.home.model.navigate.HomeModel;

public class HomeSelectEvent {
    public HomeModel.HomeItemModel homeItemModel;
    public int position;

    public HomeSelectEvent(HomeModel.HomeItemModel homeItemModel,int position) {
        this.homeItemModel = homeItemModel;
        this.position = position;
    }
}
