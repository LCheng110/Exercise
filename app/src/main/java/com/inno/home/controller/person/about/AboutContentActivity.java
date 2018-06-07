package com.inno.home.controller.person.about;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.base.BaseActivity;

import butterknife.BindView;

public class AboutContentActivity extends BaseActivity {

    public static final String CONTENT_TYPE = "CONTENT_TYPE";

    public static final int TYPE_ABOUT = 0;
    public static final int TYPE_AGREEMENT = 1;
    public static final int TYPE_POLICY = 2;

    @BindView(R.id.iv_content_img)
    ImageView iv_content_img;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private int mContentType;

    @Override
    protected int initLayout() {
        return R.layout.activity_about_content;
    }

    @Override
    protected void initValue() {
        mContentType = getIntent().getIntExtra(CONTENT_TYPE, TYPE_ABOUT);
    }

    @Override
    protected void initView() {
        if (titleBar != null) {
            switch (mContentType) {
                case TYPE_ABOUT:
                    titleBar.setTitleText(R.string.about_title_us);
                    break;
                case TYPE_AGREEMENT:
                    titleBar.setTitleText(R.string.about_title_agreement);
                    iv_content_img.setVisibility(View.GONE);
                    break;
                case TYPE_POLICY:
                    titleBar.setTitleText(R.string.about_title_policy);
                    iv_content_img.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    protected void initData() {

    }
}
