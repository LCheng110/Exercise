package com.inno.home.controller.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.inno.home.R;
import com.inno.home.utils.AppUtil;


/**
 * Created by liucheng on 2018/2/3.
 */

public class DialogHelper {
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private boolean isExitButton;

    public DialogHelper init(Context context) {
        builder = new AlertDialog.Builder(context);
        dialog = builder
                .setPositiveButton(R.string.text_sure, null)
                .setNegativeButton(R.string.text_cancel, null).create();
        return this;
    }

    public DialogHelper init(Context context, boolean isExitButton) {
        builder = new AlertDialog.Builder(context);
        if (isExitButton) {
            dialog = builder.create();
        } else {
            init(context);
        }
        this.isExitButton = isExitButton;
        builder.setCancelable(!isExitButton);
        return this;
    }


    public DialogHelper setTitle(String title) {
        builder.setTitle(title);
        return this;
    }

    public DialogHelper setContentView(View view) {
        builder.setView(view);
        return this;
    }

    public DialogHelper setTitle(@StringRes int resId) {
        builder.setTitle(resId);
        return this;
    }

    public DialogHelper setMessage(String resId) {
        builder.setMessage(resId);
        return this;
    }

    public DialogHelper setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    public DialogHelper setMessage(@StringRes int resId) {
        String message = AppUtil.getContext().getString(resId);
        builder.setMessage(message);
        return this;
    }

    public DialogHelper setPositiveListener(DialogInterface.OnClickListener sureCallback) {
        builder.setPositiveButton(R.string.text_sure, sureCallback);
        return this;
    }

    public DialogHelper setNegativeListener(DialogInterface.OnClickListener cancelCallback) {
        builder.setNegativeButton(R.string.text_cancel, cancelCallback);
        return this;
    }

    public DialogHelper setItems(int resId, DialogInterface.OnClickListener cancelCallback) {
        builder.setItems(resId, cancelCallback);
        return this;
    }

    public AlertDialog show() {
        dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
        return dialog;
    }

    public AlertDialog cancel() {
        dialog.cancel();
        return dialog;
    }
}
