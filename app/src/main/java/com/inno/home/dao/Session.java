package com.inno.home.dao;

import com.inno.home.utils.SPUtil;

public class Session {
    public static final int LANGUAGE_ENDLISH = 0;
    public static final int LANGUAGE_CHINESE = 1;
    private static final String SP_KEY_LANGUAGE = "language";                                 //SP key 记录是否已经展示引导页

    public static int getLanguage() {
        return SPUtil.getInt(SP_KEY_LANGUAGE, LANGUAGE_ENDLISH);
    }

    public static void seLanguage(int flag) {
        SPUtil.setInt(SP_KEY_LANGUAGE, flag);
    }

}
