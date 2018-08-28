package com.inno.home.controller.person;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.config.Config;
import com.inno.home.config.ServiceParam;
import com.inno.home.controller.dialog.DialogHelper;
import com.inno.home.controller.window.AvatarSelectDialog;
import com.inno.home.dao.JsonFactory;
import com.inno.home.dao.UserCMD;
import com.inno.home.listen.net.NetRequestListener;
import com.inno.home.utils.BitmapUtil;
import com.inno.home.utils.StorageUtils;
import com.inno.home.utils.ToastUtil;
import com.inno.home.utils.glide.ImageLoader;
import com.inno.home.utils.luban.CompressImageUtil;
import com.inno.home.utils.luban.OnCompressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    String firstName;
    String lastName;
    private File mPickFile;
    private Map<String, String> modifyMap = new HashMap<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_account;
    }

    @Override
    protected void initValue() {
    }

    @Override
    protected void initView() {
        mPickFile = StorageUtils.getCacheDir(context, "avatar");
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
                AvatarSelectDialog dialog = new AvatarSelectDialog();
                dialog.setFile(mPickFile);
                dialog.show(getSupportFragmentManager(), "AvatarSelectDialog");
                break;
            case R.id.person_item_id:
                break;
            case R.id.person_item_name:
                break;
            case R.id.person_item_gender:
                new DialogHelper()
                        .init(context, true)
                        .setItems(R.array.user_gender, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("sss", "onClick: " + which);
                                gender = which == 0 ? "Male" : "Female";
                                refreshUserInfo();
                            }
                        })
                        .show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AvatarSelectDialog.REQ_TYPE_CAMERA:
                    Bitmap avatar = BitmapUtil.autoFixOrientation(mPickFile);
                    try {
                        setPicture(null, true, BitmapUtil.saveFile(avatar));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case AvatarSelectDialog.REQ_TYPE_GALLERY:
                    setPicture(data.getData(), false, null);
                    break;
            }
        }
    }

    private void setPicture(Uri data, boolean isFile, File file) {
        showProgressDialog("", "图片上传中");
        CompressImageUtil.compressWithLs(context, isFile, file, data, new OnCompressListener() {
            @Override
            public void onStart() {
                Log.i("CompressImageUtil", "onStart: 开始压缩");
            }

            @Override
            public void onSuccess(File file) {
                Log.i("CompressImageUtil", "onSuccess: 压缩成功");
                upload(file);
            }

            @Override
            public void onError(Throwable e) {
                cancelProgressDialog();
                Log.i("CompressImageUtil", "onError: 压缩失败" + e.toString());
            }
        });
    }

    /**
     * 上传图片
     */
    private void upload(final File file) {
        UserCMD.uploadFile(file, new NetRequestListener() {
            @Override
            public void onSuccess(String response) {
                avatar = String.format("%1$sfile/%2$s", Config.SERVICE_FILE_ADDRESS, file.getName());
                cancelProgressDialog();
                modifyMap.put(ServiceParam.AVATAR, avatar);
                uploadUserInfo();
            }

            @Override
            public void onError(Throwable throwable) {
                cancelProgressDialog();
                ToastUtil.showToast(R.string.upload_error);
            }
        });
    }

    private void uploadUserInfo() {
        if (modifyMap.isEmpty()) {
            return;
        }
        UserCMD.uploadUserInfo(JsonFactory.getJson(modifyMap), new NetRequestListener() {
            @Override
            public void onSuccess(String response) {
                modifyMap.clear();
                refreshUserInfo();
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    private void getUserInfo() {
        showProgressDialog("", getString(R.string.loading));
        UserCMD.getUserInfo(new HashMap<String, String>(), new NetRequestListener() {
            @Override
            public void onSuccess(String response) {
                cancelProgressDialog();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    firstName = data.getString("firstName");
                    lastName = data.getString("lastName");
                    avatar = data.getString("avatar");
                    id = data.getString("id");
                    username = data.getString("username");
                    email = data.getString("email");
//                    gender = data.getString("gender");
//                    facebookUserId = data.getString("facebookUserId");
                    refreshUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.showToast(R.string.toast_init_error);
                    finish();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                cancelProgressDialog();
                ToastUtil.showToast(R.string.toast_init_error);
                finish();
            }
        });
    }

    private void refreshUserInfo() {
        cancelProgressDialog();
        ImageLoader.loadCircleImage(iv_avatar.getContext(), avatar, iv_avatar, R.drawable.ic_avatar_default);
        tv_content_id.setText(username);
        tv_content_name.setText(String.format(getString(R.string.main_text_name_format), firstName, lastName));
        if (TextUtils.isEmpty(gender) || gender.equals("null")) {
            tv_content_gender.setText(null);
        } else {
            tv_content_gender.setText(gender);
        }
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
