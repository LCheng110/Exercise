package com.inno.home.controller.login.scene;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.utils.EncryptUtil;

/**
 * Created by lcheng on 2018/4/17.
 */

public class LoginScene extends SubmitBaseScene {

    // Login View references.
    private TextView mLoginPromptView;
    private AutoCompleteTextView mLoginEmailEditView;
    private ImageView mLoginEmailClearView;
    private EditText mLoginPasswordView;
    private Button mSubmitButton;

    @NonNull
    public static LoginScene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId,
                                               @NonNull Context context) {
        @SuppressWarnings("unchecked")
        SparseArray<SubmitBaseScene> scenes =
                (SparseArray<SubmitBaseScene>) sceneRoot.getTag(android.support.transition.R.id.transition_scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTag(android.support.transition.R.id.transition_scene_layoutid_cache, scenes);
        }
        LoginScene scene = (LoginScene) scenes.get(layoutId);
        if (scene != null) {
            return scene;
        } else {
            scene = new LoginScene(sceneRoot, layoutId, context);
            scenes.put(layoutId, scene);
            return scene;
        }
    }

    public LoginScene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        super(sceneRoot, layout);
        mLoginPromptView = mSceneView.findViewById(R.id.input_prompt);
        mLoginEmailEditView = mSceneView.findViewById(R.id.ev_input_first);
        mLoginEmailClearView = mSceneView.findViewById(R.id.iv_input_icon_first);
        mLoginPasswordView = mSceneView.findViewById(R.id.ev_input_second);
        mSubmitButton = mSceneView.findViewById(R.id.btn_submit_login);
        mLoginEmailEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mLoginEmailClearView.setVisibility(editable.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                mSubmitButton.setSelected(canSubmitEmail());
            }
        });
        mLoginEmailClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginEmailEditView.setText(null);
            }
        });
        mSceneView.findViewById(R.id.iv_input_icon_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                mLoginPasswordView.setTransformationMethod(view.isSelected() ? HideReturnsTransformationMethod.getInstance
                        () : PasswordTransformationMethod.getInstance());
                mLoginPasswordView.setSelection(mLoginPasswordView.getText().length());
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitListener.onSubmit(view);
            }
        });
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private LoginScene(ViewGroup sceneRoot, int layoutId, Context context) {
        this(sceneRoot, LayoutInflater.from(context).inflate(layoutId, sceneRoot, false));
    }

    public String getEmail() {
        return mLoginEmailEditView.getText().toString();
    }

    public String getPassword() {
        return EncryptUtil.md5(mLoginPasswordView.getText().toString());
    }

    private boolean canSubmitEmail() {
        boolean firstNameNotNull = mLoginEmailEditView.getText().toString().contains("@");
        return firstNameNotNull;
    }
}
