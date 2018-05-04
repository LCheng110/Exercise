package com.inno.home.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by qhm on 2017/3/22
 */

public class ToastUtil {

    private static String oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private static void showToast(Context context, String s, int duration) {

        if (s == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(String msg) {
        showToast(AppUtil.getContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(@StringRes int msg) {
        showToast(AppUtil.getContext(), AppUtil.getContext().getString(msg), Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int duration) {
        showToast(AppUtil.getContext(), msg, duration);
    }
}
