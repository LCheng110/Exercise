package com.inno.home.model.navigate;

import com.inno.home.model.DelegateModel;

public class ShareModel extends DelegateModel {
    public static final int ITEM_TYPE_SHARE = 2;

    public String shareName;

    public ShareModel() {
        ITEM_TYPE = ITEM_TYPE_SHARE;
    }
}
