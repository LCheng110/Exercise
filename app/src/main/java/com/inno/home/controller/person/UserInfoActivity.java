package com.inno.home.controller.person;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.adapter.UserInfoAdapter;
import com.inno.home.adapter.divide.MarginStartItemDecoration;
import com.inno.home.base.BaseActivity;
import com.inno.home.controller.dialog.DialogHelper;
import com.inno.home.listen.click.OnItemClickListener;
import com.inno.home.model.UserInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserInfoActivity extends BaseActivity {

    private static final int INDEX_OF_AVATAR = 0;
    private static final int INDEX_OF_ID = 1;
    private static final int INDEX_OF_NAME = 2;
    private static final int INDEX_OF_GENDER = 3;
    private static final int INDEX_OF_EMAIL = 4;
    private static final int INDEX_OF_FACEBOOK = 5;
    private static final int INDEX_OF_PASSWORD = 6;

    @BindView(R.id.rv_info_list)
    RecyclerView rv_info_list;
    private String[] titleArray;
    private UserInfoAdapter userInfoAdapter;
    private List<UserInfoModel> userInfoModelList = new ArrayList<>();
    private MarginStartItemDecoration itemDecoration;

    public TextView alert_prompt;
    public EditText alert_edit;

    @Override
    protected int initLayout() {
        return R.layout.activity_account;
    }

    @Override
    protected void initValue() {
        titleArray = getResources().getStringArray(R.array.user_item);
        for (String title : titleArray) {
            userInfoModelList.add(new UserInfoModel(title));
        }
    }

    @Override
    protected void initView() {
        userInfoAdapter = new UserInfoAdapter(userInfoModelList);
        rv_info_list.setLayoutManager(new LinearLayoutManager(context));
        rv_info_list.setAdapter(userInfoAdapter);
        itemDecoration = new MarginStartItemDecoration(context, R.color.colorLine,
                getResources().getDimensionPixelSize(R.dimen.line_width));
        itemDecoration.setmMarginStart(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        rv_info_list.addItemDecoration(itemDecoration);
        userInfoAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case INDEX_OF_AVATAR:
                        break;
                    case INDEX_OF_ID:
                        break;
                    case INDEX_OF_NAME:
                        break;
                    case INDEX_OF_GENDER:
                        break;
                    case INDEX_OF_EMAIL:
                        showAlertDialog(position);
                        break;
                    case INDEX_OF_FACEBOOK:
                        break;
                    case INDEX_OF_PASSWORD:
                        showAlertDialog(position);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (titleBar != null) {
            titleBar.setTitleText(R.string.person_text_account);
        }
    }

    private void showAlertDialog(final int type) {
        View alertView = View.inflate(context, R.layout.dialog_alert, null);
        alert_edit = alertView.findViewById(R.id.alert_edit);
        alert_prompt = alertView.findViewById(R.id.alert_prompt);
        alert_prompt.setText(getString(R.string.prompt_modify_email));
        new DialogHelper()
                .init(context)
                .setTitle(null)
                .setContentView(alertView)
                .setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (type == INDEX_OF_EMAIL) {
                            Navigation.showEmail(context);
                        } else if (type == INDEX_OF_PASSWORD) {

                        }
                    }
                })
                .show();
    }
}
