package com.inno.home;

import android.app.Application;

import com.inno.home.utils.AppUtil;
import com.inno.home.utils.SPUtil;

/**
 * Created by lcheng on 2018/4/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.tryInit(this);
        SPUtil.tryInit(this);
    }
}
