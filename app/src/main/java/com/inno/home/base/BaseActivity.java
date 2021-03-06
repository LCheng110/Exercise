package com.inno.home.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inno.home.R;
import com.inno.home.utils.ActivityPageManager;
import com.inno.home.widget.UCTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lcheng on 2018/4/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.title_bar)
    public UCTitleBar titleBar;
    protected Context context;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        context = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        ActivityPageManager.getInstance().addActivity(this);
        if (titleBar != null) {
            titleBar.setBackEvent(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        finish();
                    }
                }
            }).setBackground(R.color.BgPrimary);
        }
        initValue();
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initValue();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
        ActivityPageManager.getInstance().removeActivity(this);
    }

    public ProgressDialog showProgressDialog(String title, String message) {
        return progressDialog = ProgressDialog.show(context, title, message, false, false);
    }

    public ProgressDialog showProgressDialogCancelable(String title, String message) {
        return progressDialog = ProgressDialog.show(context, title, message, false, true);
    }

    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
}
