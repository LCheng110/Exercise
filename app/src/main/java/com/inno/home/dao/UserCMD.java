package com.inno.home.dao;

import com.inno.home.config.Config;
import com.inno.home.config.ServiceInterface;
import com.inno.home.listen.net.NetRequestListener;
import com.inno.home.utils.EncryptUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserCMD {

    public static final String REGISTER = "registration";

    public static void getVerifyCode(String json, NetRequestListener requestListener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        ServerUtil.uploadFile(ServiceInterface.USER_VERIFY_CODE, requestBody, requestListener);
    }

    public static void verifyCodeEnsure(String json, NetRequestListener requestListener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        ServerUtil.uploadFile(ServiceInterface.USER_VERIFY_CODE_ENSURE, requestBody, requestListener);
    }

    public static void register(String json, NetRequestListener requestListener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        ServerUtil.uploadFile(ServiceInterface.USER_REGISTER, requestBody, requestListener);
    }

    public static void login(Map<String, String> map, NetRequestListener requestListener) {
        Map<String, String> head = new HashMap<>();
        head.put("Authorization", "Basic " + EncryptUtil.getBase64("app:qweR0505").trim());
        head.put("content-type", "application/x-www-form-urlencoded;charset=utf-8"); // ;charset=utf-8
        ServerUtil.post(ServiceInterface.USER_LOGIN, head, map, requestListener);
    }

    public static void getUserInfo(Map<String, String> map, NetRequestListener requestListener) {
        ServerUtil.get(ServiceInterface.USER_INFO, map, requestListener);
    }

    public static void uploadUserInfo(String json, NetRequestListener requestListener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        ServerUtil.patch(ServiceInterface.USER_REGISTER, requestBody, requestListener);
    }

    public static void uploadFile(File file, NetRequestListener requestListener) {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart
                ("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        ServerUtil.uploadFile(Config.SERVICE_FILE_ADDRESS + ServiceInterface.FILE_UPLOAD, requestBody, requestListener);
    }
}