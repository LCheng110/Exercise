package com.inno.home.utils.permission;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.appcompat.BuildConfig;

/**
 * Created by qhm on 2017/8/22
 * <p>
 * 根据不同的机型跳转到权限设置页面
 */

public class PermissionUtils {

    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "Huawei";
    private static final String MANUFACTURER_MEIZU = "Meizu";
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    private static final String MANUFACTURER_SONY = "Sony";
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";
    private static final String MANUFACTURER_LETV = "Letv";
    private static final String MANUFACTURER_ZTE = "ZTE";
    private static final String MANUFACTURER_YULONG = "YuLong";
    private static final String MANUFACTURER_LENOVO = "LENOVO";

    /**
     * This function can define your own
     *
     * @param context
     */
    public static void GoToSetting(Context context) {
        SystemConfig(context);
//        ApplicationInfo(context);
//        switch (Build.MANUFACTURER) {
//            case MANUFACTURER_HUAWEI:
//                Huawei(context);
//                break;
//            case MANUFACTURER_MEIZU:
//                Meizu(context);
//                break;
//            case MANUFACTURER_XIAOMI:
//                SystemConfig(context);
//                break;
//            case MANUFACTURER_SONY:
//                Sony(context);
//                break;
//            case MANUFACTURER_OPPO:
//                OPPO(context);
//                break;
//            case MANUFACTURER_LG:
//                LG(context);
//                break;
//            case MANUFACTURER_LETV:
//                Letv(context);
//                break;
//            default:
//                SystemConfig(context);
//                break;
//        }
    }

    /**
     * 华为 P10 测试可用
     *
     * @param context
     */
    private static void Huawei(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        context.startActivity(intent);
    }

    private static void Meizu(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        context.startActivity(intent);
    }

    private static void Xiaomi(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        try {
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Sony(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        context.startActivity(intent);
    }

    private static void OPPO(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        context.startActivity(intent);
    }

    private static void LG(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        context.startActivity(intent);
    }

    private static void Letv(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        context.startActivity(intent);
    }

    /**
     * Open only to bring their own security software
     *
     * @param context
     */
    private static void _360(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        context.startActivity(intent);
    }

    /**
     * Application information interface
     *
     * @param context
     */
    private static void ApplicationInfo(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * System Settings interface
     *
     * @param context
     */
    private static void SystemConfig(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

}
