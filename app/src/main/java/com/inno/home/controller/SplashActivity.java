package com.inno.home.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.dao.Session;
import com.inno.home.utils.UiUtil;
import com.inno.home.utils.permission.PermissionManager;

import java.util.Locale;

/**
 * Created by qhm on 2018/1/12
 * <p>
 * App启动页面
 */

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIME = 2_000;
    private static final long DELAY_TIME = 500;
    private ImageView iv_splash;

    private boolean hasAdv = false; //有没有广告，默认没有

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initValue() {
    }

    @Override
    protected void initView() {
        UiUtil.setTransparentStatusBar(this);
        iv_splash = findViewById(R.id.iv_splash);
    }

    @Override
    protected void initData() {
        iv_splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMain();
            }
        }, SPLASH_TIME);

        if (Session.getLanguage() == Session.LANGUAGE_ENDLISH) {
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.ENGLISH;
            resources.updateConfiguration(config, dm);
        } else {
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.SIMPLIFIED_CHINESE;
            resources.updateConfiguration(config, dm);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void showMain() {
        if (isDestroyed() || isFinishing()) {
            return;
        }
        if (TextUtils.isEmpty(Session.getAccessToken())) {
            Navigation.showLoginGuide(context);
        } else {
            Navigation.showMain(context);
        }
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionManager.getInstance(this).setPermissionReceiver(null);
    }
}
