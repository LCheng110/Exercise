package com.inno.home.config;

/**
 * Created by liucheng on 2018/1/23.
 * 接口地址管理类
 */

public interface ServiceInterface {

    String USER_VERIFY_CODE = "passport/verification";
    String USER_VERIFY_CODE_ENSURE = "passport/verification/verify";
    String USER_REGISTER = "passport/account";
    String USER_PASSWORD_RESET = "passport/account/password/reset";
    String USER_LOGIN = "auth/oauth/token";
    String USER_INFO = "passport/account/me";
    String FILE_UPLOAD = "file";

}
