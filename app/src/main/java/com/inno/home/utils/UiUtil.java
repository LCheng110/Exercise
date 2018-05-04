package com.inno.home.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yuety on 15/10/13.
 */
public class UiUtil {

    /*当前view的缩放比例*/
    public static float scale = 0;


    //为View添加相对Padding
    public static void addPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(view.getPaddingLeft() + left,
                view.getPaddingTop() + top,
                view.getPaddingRight() + right,
                view.getPaddingBottom() + bottom);
    }

    /*dip 转为  px*/
    public static int dip2px(float dpValue) {
        if (0 == scale) {
            scale = AppUtil.getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /*px 转为 dip*/
    public static int px2dip(float pxValue) {
        if (0 == scale) {
            scale = AppUtil.getResources().getDisplayMetrics().density;
        }

        return (int) (pxValue / scale + 0.5f);
    }

    public static int columns(int widthDip, int minColumns) {
        int cols = AppUtil.getDisplayMetrics().widthPixels / UiUtil.dip2px(widthDip);
        if (cols < minColumns) {
            return minColumns;
        } else {
            return cols;
        }

    }

//    public static int color(int color) {
//        return App.getCurrent().getResources().getColor(color);
//    }暂时


    public static void showCnt(TextView tvCnt, int cnt) {

        if (cnt > 99) {
            tvCnt.setText("...");
            tvCnt.setVisibility(View.VISIBLE);
        } else if (cnt == 0) {
            tvCnt.setVisibility(View.GONE);
        } else {
            tvCnt.setText(String.valueOf(cnt));
            tvCnt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置窗口全屏
     */
    public static void setLayoutFull(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 38;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = 0;
        resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        result = context.getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    /**
     * 判断底部navigator是否已经显示
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getRealScreenHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        return realHeight;
    }

    /**
     * 获取view 在屏幕中的位置 保存在Bundle中 并返回
     *
     * @param view
     * @return
     */
    public static Bundle captureValues(View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt("left", screenLocation[0]);
        b.putInt("top", screenLocation[1]);
        b.putInt("width", view.getWidth());
        b.putInt("height", view.getHeight());
        return b;
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int statusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (miuisetstatusbarlightmode(activity.getWindow(), true)) {
                result = 1;
            } else if (flymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
        return result;
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int statusBarDarktMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (miuisetstatusbarlightmode(activity.getWindow(), false)) {
                result = 1;
            } else if (flymeSetStatusBarLightMode(activity.getWindow(), false)) {
                result = 2;
            }
        }
        return result;
    }

    /**
     * 设置小米手机状态栏字体图标颜色模式，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean miuisetstatusbarlightmode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) { //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else { //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置魅族手机状态栏图标颜色风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context
     * @return
     */
    public static Point getScreenDisplay(Context context) {
        Point display = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(display);
        return display;
    }

    /**
     * 设置透明的状态栏  及全屏
     *
     * @param context
     */
    public static void setTransparentStatusBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) context).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置适配全屏的控件
     *
     * @param context
     */
    public static void setViewTransparentPadding(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + UiUtil.getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

//    /**
//     * 获取一个随机颜色值
//     *
//     * @return
//     */
//    public static String getRandomColor() {
//        Random random = new Random();
//        String r = Integer.toHexString(random.nextInt(256)).toUpperCase();
//        String g = Integer.toHexString(random.nextInt(256)).toUpperCase();
//        String b = Integer.toHexString(random.nextInt(256)).toUpperCase();
//
//        r = r.length() == 1 ? "0" + r : r;
//        g = g.length() == 1 ? "0" + g : g;
//        b = b.length() == 1 ? "0" + b : b;
//
//        return new StringBuilder().append("#").append(r).append(g).append(b).toString();
//    }

    /**
     * 修改状态栏颜色
     *
     * @param context
     */
    public static void setStatusBarColor(Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) context).getWindow();
            window.setStatusBarColor(color);
        }
    }

    /**
     * 设置页面背景透明度
     *
     * @param activity
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, int n) {

        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);

    }
}
