package com.inno.home.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    public static boolean isKeyboardVisible(Context context) {
        return getInputMethodManager(context).isActive();
    }

    public static boolean isKeyboardVisible(Context context, View view) {
        return getInputMethodManager(context).isActive(view);
    }

    public static void closeKeyboardIfShown(Activity activity) {
        if (isKeyboardVisible(activity)) {
            hideKeyboard(activity);
        }
    }

    public static void showKeyboardIfHide(Activity activity) {
        getInputMethodManager(activity).toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }


    public static void hideKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            getInputMethodManager(activity).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static void showKeyboard(Activity activity, View view) {
        getInputMethodManager(activity).showSoftInput(view, 0);
    }
}
