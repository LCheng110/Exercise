package com.inno.home.controller.person;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inno.home.R;
import com.inno.home.adapter.InvitedSharedUserAdapter;
import com.inno.home.adapter.SharedUserAdapter;
import com.inno.home.base.BaseActivity;
import com.inno.home.widget.SlideEditRecycleView;

import butterknife.BindView;

public class SharedUserActivity extends BaseActivity {

    @BindView(R.id.rv_shared_user_list)
    SlideEditRecycleView rv_shared_user_list;
    @BindView(R.id.rv_invited_list)
    RecyclerView rv_invited_list;
    @BindView(R.id.fl_shared_empty)
    ConstraintLayout fl_shared_empty;

    @Override
    protected int initLayout() {
        return R.layout.activity_shared_user;
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initView() {
        if (titleBar != null) {
            titleBar.setRightMenuEvent(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }, getString(R.string.text_invite), Color.parseColor("#930db2"), 15);
        }
        rv_shared_user_list.setLayoutManager(new LinearLayoutManager(context));
        rv_shared_user_list.setAdapter(new SharedUserAdapter());
        rv_invited_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv_invited_list.setAdapter(new InvitedSharedUserAdapter());
        fl_shared_empty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }
}
