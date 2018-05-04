package com.inno.home.controller.device;

import android.view.View;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;

import butterknife.OnClick;

public class ProductTypeActivity extends BaseActivity {
    @Override
    protected int initLayout() {
        return R.layout.activity_product_type;
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
            titleBar.setTitleText(R.string.product_text_title);
        }
    }

    @OnClick({R.id.product_item_water, R.id.product_item_motion, R.id.product_item_window,
            R.id.product_item_door, R.id.product_item_person, R.id.product_item_smoke})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_item_water:
                Navigation.showProductConnect(context, ProductConnectActivity.PRODUCT_TYPE_WATER);
                break;
            case R.id.product_item_motion:
                Navigation.showProductConnect(context, ProductConnectActivity.PRODUCT_TYPE_MOTION);
                break;
            case R.id.product_item_window:
                Navigation.showProductConnect(context, ProductConnectActivity.PRODUCT_TYPE_WINDOW);
                break;
            case R.id.product_item_door:
                Navigation.showProductConnect(context, ProductConnectActivity.PRODUCT_TYPE_DOOR);
                break;
            case R.id.product_item_person:
                Navigation.showProductConnect(context, ProductConnectActivity.PRODUCT_TYPE_PERSON);
                break;
            case R.id.product_item_smoke:
                Navigation.showProductConnect(context, ProductConnectActivity.PRODUCT_TYPE_SMOKE);
                break;
        }
    }
}
