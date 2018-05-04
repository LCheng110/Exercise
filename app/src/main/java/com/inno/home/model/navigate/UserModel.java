package com.inno.home.model.navigate;

import com.inno.home.model.DelegateModel;

public class UserModel extends DelegateModel {
    public static final int ITEM_TYPE_USER = 0;
    public String userName;
    public String userAvatar;

    public UserModel() {
        ITEM_TYPE = ITEM_TYPE_USER;
    }
}
