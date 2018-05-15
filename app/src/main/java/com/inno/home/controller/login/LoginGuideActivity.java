package com.inno.home.controller.login;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.utils.EncryptUtil;
import com.inno.home.utils.UiUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lcheng on 2018/4/8.
 */

public class LoginGuideActivity extends BaseActivity {

    @BindView(R.id.root_view)
    ConstraintLayout rootView;
    @BindView(R.id.login_facebook)
    Button facebookForLogin;
    @BindView(R.id.login_email)
    Button createAccountForLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_login_guide;
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initView() {
        UiUtil.setTransparentStatusBar(this);
        UiUtil.setViewTransparentPadding(this, titleBar);
        if (titleBar != null) {
            titleBar.setRightMenuEvent(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.showLogin(context);
                }
            }, getString(R.string.prompt_login), 0, 0).setBarBackground(Color.TRANSPARENT);
        }
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.login_email, R.id.login_facebook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_email:
                Navigation.showRegister(context);
//                Navigation.showMain(context);
                break;
            case R.id.login_facebook:
//                ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(this);
//                builder.setType("text/html");
//                builder.setHtmlText("<a href='http://www.baidu.com'>百度试试</a>");
//                builder.setChooserTitle(R.string.app_name);
//                builder.setSubject(getString(R.string.action_sign_in));
//                builder.setText("哈哈哈哈哈哈或http://www.baidu.com");
//                builder.startChooser();
                Log.i("sss", "onClick: "+ EncryptUtil.md5("111111"));
                break;
        }
    }
}
