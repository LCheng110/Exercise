package com.inno.home.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by liucheng on 2017/11/7.
 * 数据存储相关方法
 */

public class StorageUtils {

    public static File getCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
