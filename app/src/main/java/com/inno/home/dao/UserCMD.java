package com.inno.home.dao;

import com.inno.home.config.ServiceInterface;
import com.inno.home.listen.net.NetRequestListener;

import java.util.Map;

public class UserCMD {
    public static void getVerifyCode(Map<String, String> map, NetRequestListener requestListener) {
        ServerUtil.post(ServiceInterface.USER_VERIFY_CODE, map, requestListener);
    }
}
