package com.inno.home.utils.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.inno.home.controller.dialog.DialogHelper;


/**
 * Created by qhm on 2017/8/22
 * <p>
 * permission Dialog
 */

public class PermissionDialog {

    public static final String REQUEST_PERMISSION = "申请权限，请在设置页面点击同意";
    public static final String PERMISSION = "权限";
    public static final String APPLY = "申请";
    public static final String FAIL = "失败";

    public static void showPermissionDialog(final Context context, final String permission) {
        showPermissionDialog(context, permission, null);
    }

    public static void showPermissionDialog(final Context context, final String permission, final DialogInterface
            .OnClickListener negativeCallback) {
        new DialogHelper().init(context).setMessage(REQUEST_PERMISSION + permission + PERMISSION).setPositiveListener
                (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initPositiveListener(context);
            }
        }).setNegativeListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initNegativeListener(context, permission, negativeCallback);
            }
        }).show();
    }

    private static DialogInterface.OnClickListener initPositiveListener(final Context context) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionUtils.GoToSetting(context);
            }
        };
        return listener;
    }

    private static DialogInterface.OnClickListener initNegativeListener(final Context context, final String
            permission, final DialogInterface.OnClickListener callback) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onClick(dialog, which);
                }
                toastFail(context, permission);
            }
        };
        return listener;
    }

    private static void toastFail(Context context, String permission) {
        Toast.makeText(context, APPLY + permission + PERMISSION + FAIL, Toast.LENGTH_SHORT).show();
    }
}
