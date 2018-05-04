package com.inno.home.model;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by qhm on 2017/10/18
 * <p>
 * Base model
 */

public class BaseModel<T> implements Serializable {

    public static final int STATE_SUCCESS = 1;          //接口获取到数据
    public static final int STATE_FAILED = 0;           //接口没有获取到数据

    public int Success;
    public int code;
    public T data;
    public String msg;
    public String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        Gson gson = new Gson();
        if (null == data) {
            return null;
        }
        return gson.toJson(data);
    }

    public T getContent() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
