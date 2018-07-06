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
                Map<String, String> map = new HashMap<>();
                map.put(ServiceParam.LOGIN_TYPE_PASSWORD, "other");
                map.put(ServiceParam.EMAIL, mLoginScene.getEmail());
                map.put(ServiceParam.PASSWORD, mLoginScene.getPassword());
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
                final JSONObject verifyObject = new JSONObject();
                final JSONObject registerObject = new JSONObject();
                try {
                    verifyObject.put(ServiceParam.EMAIL, mEmailScene.getEmail());
                    verifyObject.put(ServiceParam.TYPE, UserCMD.REGISTER);
                    verifyObject.put(ServiceParam.CODE, mSetPasswordScene.getVaildCode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final ProgressDialog registerDialog = showProgressDialog("", "请稍后~");
                UserCMD.verifyCodeEnsure(verifyObject.toString(), new NetRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            registerObject.put(ServiceParam.NAME_FIRST, mRegisterNameScene.getFirstName());
                            registerObject.put(ServiceParam.NAME_LAST, mRegisterNameScene.getLastName());
                            registerObject.put(ServiceParam.EMAIL, mEmailScene.getEmail());
                            registerObject.put(ServiceParam.PASSWORD, mSetPasswordScene.getPassword());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        UserCMD.register(registerObject.toString(), new NetRequestListener() {
                            @Override
                            public void onSuccess(String response) {
                                registerDialog.cancel();
                                Navigation.showLogin(context);
                                finish();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                registerDialog.cancel();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }
                });
                break;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
//    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }
//
//        // Reset errors.
//        mEmailEditView.setError(null);
//        mPasswordView.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = mEmailEditView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailEditView.setError(getString(R.string.error_field_required));
//            focusView = mEmailEditView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mEmailEditView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailEditView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
////            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
//        }
//    }
}

