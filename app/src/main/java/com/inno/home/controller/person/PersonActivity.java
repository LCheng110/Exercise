package com.inno.home.controller.person;

import android.view.View;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lcheng on 2018/4/16.
 */

public class PersonActivity extends BaseActivity {

    @BindView(R.id.person_text_user)
    TextView person_text_user;

    @Override
    protected int initLayout() {
        return R.layout.activity_person;
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initView() {
        if (titleBar != null) {
            titleBar.setTitleText(R.string.person_text_title);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.person_item_user, R.id.person_item_language, R.id.person_item_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_item_user:
                Navigation.showAccount(context);
                break;
            case R.id.person_item_language:
                break;
            case R.id.person_item_about:
                break;
        }
    }
}
