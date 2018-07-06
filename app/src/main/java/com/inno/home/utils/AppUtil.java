package com.inno.home.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.DisplayMetrics;


/**
 * Created by yuety on 16/1/18.
 */
public class AppUtil {

    static Application mCurrent;

    public static void tryInit(Application app) {
        mCurrent = app;
    }

    public static Application getCurrent() {
        return mCurrent;
    }

    public static Context getContext() {
        return mCurrent.getApplicationContext();
    }


    public static DisplayMetrics getDisplayMetrics() {
        return getContext().getResources().getDisplayMetrics();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取版本号
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionNum(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获取包名
     */
    public static String getPackName(Context context) {
        return context.getPackageName();
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static String getDeviceId() {
        return Settings.System.getString(AppUtil.getContext().getContentResolver(),
                Settings.System.ANDROID_ID);
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        SPUtil.clear();
    }
}
