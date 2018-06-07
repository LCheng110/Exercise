package com.inno.home.controller.person;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.adapter.UserInfoAdapter;
import com.inno.home.adapter.divide.MarginStartItemDecoration;
import com.inno.home.base.BaseActivity;
import com.inno.home.controller.dialog.DialogHelper;
import com.inno.home.listen.click.OnItemClickListener;

import java.util.Arrays;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.rv_about_list)
    RecyclerView rv_about_list;
    private UserInfoAdapter infoAdapter;
    private String[] titleArray;

    @Override
    protected int initLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initValue() {
        titleArray = getResources().getStringArray(R.array.about_item);
    }

    @Override
    protected void initView() {
        infoAdapter = new UserInfoAdapter(Arrays.asList(titleArray));
        MarginStartItemDecoration itemDecoration = new MarginStartItemDecoration(context, R.color.colorLine,
                getResources().getDimensionPixelSize(R.dimen.line_width));
        itemDecoration.setmMarginStart(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        rv_about_list.addItemDecoration(itemDecoration);
        rv_about_list.setLayoutManager(new LinearLayoutManager(context));
        rv_about_list.setAdapter(infoAdapter);
        infoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        Navigation.showAboutContent(context, position);
                        break;
                    case 3:
                        new DialogHelper()
                                .init(context)
                                .setMessage(R.string.text_version_update)
                                .setPositiveListener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (titleBar != null) {
            titleBar.setTitleText(R.string.person_text_about);
        }
    }
}
