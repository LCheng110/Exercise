package com.inno.home.controller.person;

import com.inno.home.R;
import com.inno.home.base.BaseActivity;

public class ContactUsActivity extends BaseActivity {
    @Override
    protected int initLayout() {
        return R.layout.activity_contact_us;
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
            titleBar.setTitleText(R.string.main_text_contact);
        }
    }
}
