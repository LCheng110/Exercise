package com.inno.home.controller.person;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.controller.dialog.DialogHelper;
import com.inno.home.dao.UserCMD;
import com.inno.home.listen.net.NetRequestListener;
import com.inno.home.utils.ToastUtil;
import com.inno.home.utils.glide.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    private static final int INDEX_OF_AVATAR = 0;
    private static final int INDEX_OF_ID = 1;
    private static final int INDEX_OF_NAME = 2;
    private static final int INDEX_OF_GENDER = 3;
    private static final int INDEX_OF_EMAIL = 4;
    private static final int INDEX_OF_FACEBOOK = 5;
    private static final int INDEX_OF_PASSWORD = 6;

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_content_id)
    TextView tv_content_id;
    @BindView(R.id.tv_content_name)
    TextView tv_content_name;
    @BindView(R.id.tv_content_gender)
    TextView tv_content_gender;
    @BindView(R.id.tv_content_email)
    TextView tv_content_email;
    @BindView(R.id.tv_content_facebook)
    TextView tv_content_facebook;
    @BindView(R.id.tv_content_password)
    TextView tv_content_password;

    public TextView alert_prompt;
    public EditText alert_edit;

    String avatar;
    String id;
    String username;
    String gender;
    String email;
    String facebookUserId;

    @Override
    protected int initLayout() {
        return R.layout.activity_account;
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
            titleBar.setTitleText(R.string.person_text_account);
        }
        getUserInfo();
    }

    @OnClick({R.id.person_item_avatar, R.id.person_item_id, R.id.person_item_name, R.id.person_item_gender,
            R.id.person_item_email, R.id.person_item_facebook, R.id.person_item_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_item_avatar:
                break;
            case R.id.person_item_id:
                break;
            case R.id.person_item_name:
                break;
            case R.id.person_item_gender:
                break;
            case R.id.person_item_email:
                showAlertDialog(INDEX_OF_EMAIL);
                break;
            case R.id.person_item_facebook:
                break;
            case R.id.person_item_password:
                showAlertDialog(INDEX_OF_PASSWORD);
                break;
        }
    }

    private void getUserInfo() {
        UserCMD.getUserInfo(new HashMap<String, String>(), new NetRequestListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    avatar = data.getString("avatar");
                    id = data.getString("id");
                    username = data.getString("username");
                    gender = data.getString("gender");
                    email = data.getString("email");
                    facebookUserId = data.getString("facebookUserId");
                    refreshUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.showToast("初始化用户信息失败");
                    finish();
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    private void refreshUserInfo() {
        ImageLoader.loadCircleImage(iv_avatar.getContext(), avatar, iv_avatar);
        tv_content_id.setText(id);
        tv_content_name.setText(username);
        tv_content_gender.setText(gender);
        tv_content_email.setText(email);
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
