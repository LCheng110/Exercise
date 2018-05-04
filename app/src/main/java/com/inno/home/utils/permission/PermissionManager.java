package com.inno.home.utils.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by qhm on 2017/8/21
 * <p>
 * 权限工具类
 */
public class PermissionManager {

    //存储权限
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //读取权限
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    //相机权限
    public static final String CAMERA = Manifest.permission.CAMERA;
    //定位权限
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    //录音权限
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    //电话权限
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;

    private static PermissionManager sSingleton;
    private static WeakReference<Context> weakReference;
    private GrantedPermissionReceiver mReceiver;

    private PermissionManager(Context context) {
        weakReference = new WeakReference<>(context);
    }

    public static PermissionManager getInstance(Context context) {

        if (sSingleton == null || (weakReference != null && weakReference.get() == null)) {
            sSingleton = new PermissionManager(context);
        }
        return sSingleton;
    }

    /**
     * 请求各种权限
     *
     * @param permissions
     */
    public void requestPermissions(String... permissions) {

        //只针对Android 6.0的动态权限进行检查
        if (Build.VERSION.SDK_INT < 23) {
            handlePermissions(permissions);
            return;
        }

        boolean hasPermission = true;

        //检查是否已经拥有该权限，避免重复申请
        for (String name : permissions) {
            if (!isGranted(name)) {
                hasPermission = false;
                break;
            }
        }

        if (hasPermission) {
            handlePermissions(permissions);
        } else {
            requestPermissionsByActivity(permissions);
        }
    }

    /**
     * 接受权限请求结果, 给PermissionShadowActivity调用
     *
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(String permissions[], int[] grantResults) {

        ArrayList<String> permissionList = new ArrayList<>();

        if (permissions == null || grantResults == null) {
            handlePermissions(permissionList);
            return;
        }

        for (int i = 0, size = permissions.length; i < size; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }

        handlePermissions(permissionList);
    }

    /**
     * 申请权限 申请权限失败 默认跳转应用信息页面
     *
     * @param context
     * @param permissionMsg     需要的权限的名称 如 拍摄、录音和读取文件存储
     * @param callback          申请权限成功的回调
     * @param requestPermission 申请的权限
     */
    public void requestPermission(final Context context, final String permissionMsg,
                                  final OnPermissionSuccessListener callback, final String... requestPermission) {
        getInstance(context.getApplicationContext()).setPermissionReceiver(new GrantedPermissionReceiver() {
            @Override
            public void onReceive(String... permissions) {
                List<String> requestPer = convertToStringList(requestPermission);
                List<String> receivePer = convertToStringList(permissions);
                boolean containsAllPermission = true;
                for (int i = 0; i < requestPer.size(); i++) {
                    if (!receivePer.contains(requestPer.get(i))) {
                        containsAllPermission = false;
                    }
                }
                if (receivePer.isEmpty()) {
                    containsAllPermission = false;
                }
                if (containsAllPermission) {
                    if (callback != null) {
                        callback.onPermissionGrantedSuccess();
                    }
                } else {
                    PermissionDialog.showPermissionDialog(context, permissionMsg);
                }
            }
        });
        requestPermissions(requestPermission);
    }

    /**
     * @param context
     * @param permissionMsg     需要的权限的名称 如 拍摄、录音和读取文件存储
     * @param negativeCallback  拒绝的回调
     * @param callback          申请权限成功的回调
     * @param requestPermission 申请的权限
     */
    public void requestPermission(final Context context, final String permissionMsg, final DialogInterface.OnClickListener negativeCallback,
                                  final OnPermissionSuccessListener callback, final String... requestPermission) {
        getInstance(context.getApplicationContext()).setPermissionReceiver(new GrantedPermissionReceiver() {
            @Override
            public void onReceive(String... permissions) {
                List<String> requestPer = convertToStringList(requestPermission);
                List<String> receivePer = convertToStringList(permissions);
                boolean containsAllPermission = true;
                for (int i = 0; i < requestPer.size(); i++) {
                    if (!receivePer.contains(requestPer.get(i))) {
                        containsAllPermission = false;
                    }
                }
                if (receivePer.isEmpty()) {
                    containsAllPermission = false;
                }
                if (containsAllPermission) {
                    if (callback != null) {
                        callback.onPermissionGrantedSuccess();
                    }
                } else {
                    PermissionDialog.showPermissionDialog(context,
                            permissionMsg, negativeCallback);
                }
            }
        });
        requestPermissions(requestPermission);
    }

    /**
     * 已授权权限的接收器,
     * <p>
     * 权限若通过申请,则在onReceive中返回
     * 权限若被拒绝,则不在onReceive中返回
     */
    public interface GrantedPermissionReceiver {
        void onReceive(String... permissions);
    }

    /**
     * 设置权限监听
     *
     * @param receiver
     */
    public void setPermissionReceiver(GrantedPermissionReceiver receiver) {
        mReceiver = receiver;
    }

    /**
     * 处理permission申请结果
     *
     * @param permissions
     * @param <T>
     */
    private <T> void handlePermissions(T permissions) {
        String[] results = convertToStringArray(permissions);
        if (mReceiver != null) {
            mReceiver.onReceive(results);
        }
    }

    /**
     * 转换为String数组
     *
     * @param permissions
     * @param <T>
     * @return
     */
    public static <T> String[] convertToStringArray(T permissions) {

        String[] results = new String[]{};

        if (permissions == null) {
            return results;
        }

        if (permissions instanceof ArrayList<?>) {
            ArrayList<String> permissionList = (ArrayList<String>) permissions;
            results = permissionList.toArray(new String[permissionList.size()]);
        } else if (permissions instanceof String[]) {
            results = (String[]) permissions;
        }

        return results;

    }

    /**
     * 转换为List
     *
     * @param permissions
     * @param <T>
     * @return
     */
    public static <T> List<String> convertToStringList(T permissions) {
        List<String> result = new ArrayList<>();

        if (permissions == null) {
            return result;
        }
        if (permissions instanceof ArrayList<?>) {
            result = (List<String>) permissions;
        } else if (permissions instanceof String[]) {
            result = Arrays.asList((String[]) permissions);
        }
        return result;
    }

    /**
     * 启动影子Activity申请权限
     *
     * @param permissions
     */
    private void requestPermissionsByActivity(String[] permissions) {
        Intent intent = new Intent(weakReference.get(), PermissionShadowActivity.class);
        intent.putExtra("permissions", permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        weakReference.get().startActivity(intent);
    }

    /**
     * 判断权限是否通过申请
     *
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGranted(String permission) {
        return weakReference.get().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public interface OnPermissionSuccessListener{
        void onPermissionGrantedSuccess();
    }
}
