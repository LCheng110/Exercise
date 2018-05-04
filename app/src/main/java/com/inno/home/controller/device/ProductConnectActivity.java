package com.inno.home.controller.device;

import com.inno.home.R;
import com.inno.home.base.BaseActivity;

public class ProductConnectActivity extends BaseActivity {

    public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
    public static final int PRODUCT_TYPE_WATER = 0;
    public static final int PRODUCT_TYPE_MOTION = 1;
    public static final int PRODUCT_TYPE_WINDOW = 2;
    public static final int PRODUCT_TYPE_DOOR = 3;
    public static final int PRODUCT_TYPE_PERSON = 4;
    public static final int PRODUCT_TYPE_SMOKE = 5;

    private int productType;

    @Override
    protected int initLayout() {
        return R.layout.activity_product_connect;
    }

    @Override
    protected void initValue() {
        productType = getIntent().getIntExtra(PRODUCT_TYPE, PRODUCT_TYPE_WATER);
    }

    @Override
    protected void initView() {
        if (titleBar != null) {
            switch (productType) {
                case PRODUCT_TYPE_WATER:
                    titleBar.setTitleText(R.string.product_text_water);
                    break;
                case PRODUCT_TYPE_MOTION:
                    titleBar.setTitleText(R.string.product_text_motion);
                    break;
                case PRODUCT_TYPE_WINDOW:
                    titleBar.setTitleText(R.string.product_text_window);
                    break;
                case PRODUCT_TYPE_DOOR:
                    titleBar.setTitleText(R.string.product_text_door);
                    break;
                case PRODUCT_TYPE_PERSON:
                    titleBar.setTitleText(R.string.product_text_person);
                    break;
                case PRODUCT_TYPE_SMOKE:
                    titleBar.setTitleText(R.string.product_text_smoke);
                    break;
            }
        }
    }

    @Override
    protected void initData() {

    }
}
