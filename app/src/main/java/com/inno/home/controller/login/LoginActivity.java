package com.inno.home.controller.login;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.config.ServiceParam;
import com.inno.home.controller.login.scene.InputEmailScene;
import com.inno.home.controller.login.scene.LoginScene;
import com.inno.home.controller.login.scene.RegisterNameScene;
import com.inno.home.controller.login.scene.SetPasswordScene;
import com.inno.home.controller.login.scene.SubmitBaseScene;
import com.inno.home.dao.Session;
import com.inno.home.dao.UserCMD;
import com.inno.home.listen.net.NetRequestListener;
import com.inno.home.utils.ToastUtil;
import com.inno.home.utils.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements SubmitBaseScene.OnSubmitListener {

    public static final String INPUT_VIEW_FLAG = "INPUT_VIEW_FLAG";
    public static final int INPUT_VIEW_FLAG_LOGIN = 0;
    public static final int INPUT_VIEW_FLAG_REGISTER = 1;
    public static final int INPUT_VIEW_FLAG_PASSWORD = 2;

    private Transition mSceneTransition;
    private LoginScene mLoginScene;
    private RegisterNameScene mRegisterNameScene;
    private InputEmailScene mEmailScene;
    private SetPasswordScene mSetPasswordScene;
    private View mCurrentView;
    private int mViewFlag;

    // UI references.
    @BindView(R.id.input_form_container)
    ScrollView mInputFormView;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initValue() {
        mViewFlag = getIntent().getIntExtra(INPUT_VIEW_FLAG, INPUT_VIEW_FLAG_LOGIN);
    }

    @Override
    protected void initView() {
        UiUtil.setTransparentStatusBar(this);
        // 根据布局id创建View
        mLoginScene = LoginScene.getSceneForLayout(mInputFormView, R.layout.scene_input_login, this);
        mRegisterNameScene = RegisterNameScene.getSceneForLayout(mInputFormView, R.layout
                .scene_input_register_name, this);
        mEmailScene = InputEmailScene.getSceneForLayout(mInputFormView, R.layout.scene_input_email, this);
        mSetPasswordScene = SetPasswordScene.getSceneForLayout(mInputFormView, R.layout
                .scene_input_set_password, this);
        initScene();
    }

    private void initScene() {
        mLoginScene.setSubmitListener(this);
        mRegisterNameScene.setSubmitListener(this);
        mEmailScene.setSubmitListener(this);
        mSetPasswordScene.setSubmitListener(this);
        // 场景转换动画
        mSceneTransition = TransitionInflater.from(context).inflateTransition(R.transition.transition_scene);
        switch (mViewFlag) {
            case INPUT_VIEW_FLAG_LOGIN:
                if (titleBar != null) {
                    titleBar.setRightMenuEvent(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Navigation.showForgotPassword(context);
                        }
                    }, getString(R.string.prompt_forget_password), ContextCompat.getColor(this, R.color.text_hint), 0);
                }
                TransitionManager.go(mLoginScene, mSceneTransition);
                break;
            case INPUT_VIEW_FLAG_REGISTER:
                TransitionManager.go(mRegisterNameScene, mSceneTransition);
                break;
            case INPUT_VIEW_FLAG_PASSWORD:
                TransitionManager.go(mEmailScene, mSceneTransition);
                break;
        }
    }

    @Override
    protected void initData() {
        if (titleBar != null) {
            titleBar.setBarBackground(Color.TRANSPARENT);
        }
    }

    @Override
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_login:
                login(mLoginScene.getEmail(), mLoginScene.getPassword());
                break;
            case R.id.btn_submit_name:
                TransitionManager.go(mEmailScene, mSceneTransition);
                break;
            case R.id.btn_submit_email:
                JSONObject object = new JSONObject();
                try {
                    object.put(ServiceParam.EMAIL, mEmailScene.getEmail());
                    object.put(ServiceParam.TYPE, UserCMD.REGISTER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final ProgressDialog verifyDialog = showProgressDialog("", "正在发送验证码，请稍后~");
                UserCMD.getVerifyCode(object.toString(), new NetRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        verifyDialog.cancel();
                        TransitionManager.go(mSetPasswordScene, mSceneTransition);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        verifyDialog.cancel();
                    }
                });
                break;
            case R.id.btn_submit_password:
                final JSONObject registerObject = new JSONObject();
                final ProgressDialog registerDialog = showProgressDialog("", "请稍后~");
                try {
                    registerObject.put(ServiceParam.NAME_FIRST, mRegisterNameScene.getFirstName());
                    registerObject.put(ServiceParam.NAME_LAST, mRegisterNameScene.getLastName());
                    registerObject.put(ServiceParam.EMAIL, mEmailScene.getEmail());
                    registerObject.put(ServiceParam.PASSWORD, mSetPasswordScene.getPassword());
                    registerObject.put(ServiceParam.CODE, mSetPasswordScene.getVaildCode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mViewFlag == INPUT_VIEW_FLAG_REGISTER) {
                    UserCMD.register(registerObject.toString(), new NetRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            registerDialog.cancel();
                            login(mEmailScene.getEmail(), mSetPasswordScene.getPassword());
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            registerDialog.cancel();
                        }
                    });
                } else if (mViewFlag == INPUT_VIEW_FLAG_PASSWORD) {
                    UserCMD.resetPassword(registerObject.toString(), new NetRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            registerDialog.cancel();
                            login(mEmailScene.getEmail(), mSetPasswordScene.getPassword());
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            registerDialog.cancel();
                        }
                    });
                }
                break;
        }
    }

    private void login(String email, String password) {
        Map<String, String> map = new HashMap<>();
        map.put(ServiceParam.LOGIN_TYPE_PASSWORD, "other");
        map.put(ServiceParam.EMAIL, email);
        map.put(ServiceParam.PASSWORD, password);
        final ProgressDialog loginDialog = showProgressDialog("", "正在登陆中，请稍后~");
        UserCMD.login(map, new NetRequestListener() {
            @Override
            public void onSuccess(String response) {
                loginDialog.cancel();
                try {
                    JSONObject object = new JSONObject(response);
                    Session.seAccessToken(object.getString(Session.SP_KEY_ACCESS_TOKEN));
                    Session.seRefreshToken(object.getString(Session.SP_KEY_REFRESH_TOKEN));
                    Navigation.showMain(context);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.showToast("登陆失败，参数异常");
                }
            }

            @Override
            public void onError(Throwable throwable) {
                loginDialog.cancel();
            }
        });
    }

}

