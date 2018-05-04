package com.inno.home.controller.person;

import com.inno.home.R;
import com.inno.home.base.BaseActivity;

public class EmailActivity extends BaseActivity {
    @Override
    protected int initLayout() {
        return R.layout.activity_email;
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (titleBar != null) {
            titleBar.setTitleText(R.string.person_text_email);
        }
    }
}
