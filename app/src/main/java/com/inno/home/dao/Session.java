package com.inno.home.dao;

import com.inno.home.utils.SPUtil;

import java.util.Arrays;
import java.util.List;

public class Session {
    public static final int LANGUAGE_ENDLISH = 0;
    public static final int LANGUAGE_CHINESE = 1;
    private static final String SP_KEY_LANGUAGE = "language";                                 //SP key 记录当前语言
    private static final String SP_KEY_HOME_DEVICE = "home_list";                                 //SP key 记录当前语言

    public static int getLanguage() {
        return SPUtil.getInt(SP_KEY_LANGUAGE, LANGUAGE_ENDLISH);
    }

    public static void seLanguage(int flag) {
        SPUtil.setInt(SP_KEY_LANGUAGE, flag);
    }

    public static List<String> getHomeDevice() {
        return Arrays.asList(SPUtil.getString(SP_KEY_HOME_DEVICE).split(","));
    }

    public static void seHomeDevice(List<String> homeDeviceList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < homeDeviceList.size(); i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(homeDeviceList.get(i));
        }
        SPUtil.setString(SP_KEY_HOME_DEVICE, builder.toString());
    }

}
