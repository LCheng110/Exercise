package com.inno.home.model.navigate;

import com.inno.home.model.DelegateModel;

import java.util.ArrayList;
import java.util.List;

public class HomeModel extends DelegateModel {
    public static final int ITEM_TYPE_HOME = 1;
    public List<HomeItemModel> homeItemModels;

    public HomeModel() {
        ITEM_TYPE = ITEM_TYPE_HOME;
        homeItemModels = new ArrayList<>();
    }

    public static class HomeItemModel {
        public String homeName;

        public HomeItemModel(String homeName) {
            this.homeName = homeName;
        }
    }
}
