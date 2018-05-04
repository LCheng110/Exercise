package com.inno.home.controller.login;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

import com.inno.home.R;
import com.inno.home.base.BaseActivity;
import com.inno.home.controller.login.scene.InputEmailScene;
import com.inno.home.controller.login.scene.LoginScene;
import com.inno.home.controller.login.scene.RegisterNameScene;
import com.inno.home.controller.login.scene.SetPasswordScene;
import com.inno.home.controller.login.scene.SubmitBaseScene;
import com.inno.home.utils.UiUtil;

import butterknife.BindView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements SubmitBaseScene.OnSubmitListener {

    public static final String INPUT_VIEW_FLAG = "INPUT_VIEW_FLAG";
    private static final int INPUT_VIEW_FLAG_LOGIN = 0;
    private static final int INPUT_VIEW_FLAG_REGISTER = 1;
    private static final int INPUT_VIEW_FLAG_PASSWORD = 2;

    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{"foo@example.com:hello", "bar@example.com:world"};
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private Transition mSceneTransition;
    private SubmitBaseScene mLoginScene, mRegisterNameScene, mEmailScene, mSetPasswordScene;
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
                TransitionManager.go(mRegisterNameScene, mSceneTransition);
                break;
            case R.id.btn_submit_name:
                TransitionManager.go(mEmailScene, mSceneTransition);
                break;
            case R.id.btn_submit_email:
                TransitionManager.go(mSetPasswordScene, mSceneTransition);
                break;
            case R.id.btn_submit_password:
                TransitionManager.go(mLoginScene, mSceneTransition);
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
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

