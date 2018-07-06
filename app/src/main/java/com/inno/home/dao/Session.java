package com.inno.home.dao;

import com.inno.home.utils.SPUtil;

import java.util.Arrays;
import java.util.List;

public class Session {
    public static final int LANGUAGE_ENDLISH = 0;
    public static final int LANGUAGE_CHINESE = 1;
    public static final String SP_KEY_ACCESS_TOKEN = "access_token";                                 //SP key 与服务端通信端token
    public static final String SP_KEY_REFRESH_TOKEN = "refresh_token";                                 //SP key 刷新服务端token
    private static final String SP_KEY_LANGUAGE = "language";                                 //SP key 记录当前语言
    private static final String SP_KEY_HOME_DEVICE = "home_list";                                 //SP key 记录当前语言
    private static final String SP_KEY_HOME_NAME = "home_name";                                 //SP key 记录Home的名称
    private static final String SP_KEY_USER_ID = "id";                  //SP key 记录userId
    private static final String SP_KEY_AVATAR = "avatar";                               //SP key 记录头像
    private static final String SP_KEY_NICK_NAME = "nickName";                          //SP key 记录昵称

    public static String getAccessToken() {
        return SPUtil.getString(SP_KEY_ACCESS_TOKEN);
    }

    public static void seAccessToken(String token) {
        SPUtil.setString(SP_KEY_ACCESS_TOKEN, token);
    }

    public static String getRefreshToken() {
        return SPUtil.getString(SP_KEY_REFRESH_TOKEN);
    }

    public static void seRefreshToken(String token) {
        SPUtil.setString(SP_KEY_REFRESH_TOKEN, token);
    }

    public static long getUserId() {
        try {
            return SPUtil.getLong(SP_KEY_USER_ID);
        } catch (Exception e) {
            return (long) SPUtil.getInt(SP_KEY_USER_ID);
        }
    }

    public static void setUserId(long userId) {
        SPUtil.setLong(SP_KEY_USER_ID, userId);
    }

    public static String getAvatar() {
        return SPUtil.getString(SP_KEY_AVATAR);
    }

    public static void setAvatar(String avatar) {
        SPUtil.setString(SP_KEY_AVATAR, avatar);
    }

    public static String getNickName() {
        return SPUtil.getString(SP_KEY_NICK_NAME);
    }

    public static void setNickName(String nickName) {
        SPUtil.setString(SP_KEY_NICK_NAME, nickName);
    }

    public static int getLanguage() {
        return SPUtil.getInt(SP_KEY_LANGUAGE, LANGUAGE_ENDLISH);
    }

    public static void seLanguage(int flag) {
        SPUtil.setInt(SP_KEY_LANGUAGE, flag);
    }

    public static String getHomeName() {
        return SPUtil.getString(SP_KEY_HOME_NAME);
    }

    public static void seHomeName(String name) {
        SPUtil.setString(SP_KEY_HOME_NAME, name);
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
