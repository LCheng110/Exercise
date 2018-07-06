package com.inno.home.listen.net;


/**
 * Created by liucheng on 2018/2/4.
 * 网络请求回调
 */

public interface NetRequestListener {
    void onSuccess(String response);

    void onError(Throwable throwable);
}
