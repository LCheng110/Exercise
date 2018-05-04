package com.inno.home;

import android.content.Context;
import android.content.Intent;

import com.inno.home.controller.MainActivity;
import com.inno.home.controller.device.DeviceListActivity;
import com.inno.home.controller.device.DeviceTypeActivity;
import com.inno.home.controller.device.ProductConnectActivity;
import com.inno.home.controller.device.ProductTypeActivity;
import com.inno.home.controller.login.LoginActivity;
import com.inno.home.controller.login.LoginGuideActivity;
import com.inno.home.controller.person.AboutActivity;
import com.inno.home.controller.person.ContactUsActivity;
import com.inno.home.controller.person.EmailActivity;
import com.inno.home.controller.person.PersonActivity;
import com.inno.home.controller.person.SharedUserActivity;
import com.inno.home.controller.person.UserInfoActivity;

/**
 * Created by lcheng on 2018/4/8.
 * 所有activity的统一导航管理类
 */

public class Navigation {

    public static void showLoginGuide(Context context) {
        context.startActivity(new Intent(context, LoginGuideActivity.class));
    }

    public static void showLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void showPerson(Context context) {
        context.startActivity(new Intent(context, PersonActivity.class));
    }

    public static void showAccount(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    public static void showAbout(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    public static void showEmail(Context context) {
        context.startActivity(new Intent(context, EmailActivity.class));
    }

    public static void showSharedDetail(Context context) {
        context.startActivity(new Intent(context, SharedUserActivity.class));
    }

    public static void showContactUs(Context context) {
        context.startActivity(new Intent(context, ContactUsActivity.class));
    }

    public static void showMain(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void showDeviceList(Context context) {
        context.startActivity(new Intent(context, DeviceListActivity.class));
    }

    public static void showDeviceType(Context context) {
        context.startActivity(new Intent(context, DeviceTypeActivity.class));
    }

    public static void showProductType(Context context) {
        context.startActivity(new Intent(context, ProductTypeActivity.class));
    }

    public static void showProductConnect(Context context, int productType) {
        Intent intent = new Intent(context, ProductConnectActivity.class);
        intent.putExtra(ProductConnectActivity.PRODUCT_TYPE, productType);
        context.startActivity(intent);
    }
}
