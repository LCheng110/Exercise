package com.inno.home.listen.net;


import com.inno.home.model.BaseModel;

/**
 * Created by liucheng on 2018/2/4.
 * 网络请求回调
 */

public interface NetRequestListener {
    void onSuccess(BaseModel baseModel);

    void onError(Throwable throwable);
}
