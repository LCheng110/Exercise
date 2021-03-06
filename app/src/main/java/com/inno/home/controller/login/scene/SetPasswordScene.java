package com.inno.home.controller.login.scene;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.utils.EncryptUtil;

/**
 * Created by lcheng on 2018/4/17.
 */

public class SetPasswordScene extends SubmitBaseScene {

    // Password View references.
    private TextView mSetPasswordPromptView;
    private EditText mCodeEditView;
    private EditText mSetPasswordEditView;
    private Button mSubmitButton;

    @NonNull
    public static SetPasswordScene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId,
                                                     @NonNull Context context) {
        @SuppressWarnings("unchecked")
        SparseArray<SubmitBaseScene> scenes =
                (SparseArray<SubmitBaseScene>) sceneRoot.getTag(android.support.transition.R.id.transition_scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTag(android.support.transition.R.id.transition_scene_layoutid_cache, scenes);
        }
        SetPasswordScene scene = (SetPasswordScene) scenes.get(layoutId);
        if (scene != null) {
            return scene;
        } else {
            scene = new SetPasswordScene(sceneRoot, layoutId, context);
            scenes.put(layoutId, scene);
            return scene;
        }
    }

    public SetPasswordScene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        super(sceneRoot, layout);
        mSetPasswordPromptView = mSceneView.findViewById(R.id.input_prompt);
        mCodeEditView = mSceneView.findViewById(R.id.ev_input_first);
        mSetPasswordEditView = mSceneView.findViewById(R.id.ev_input_second);
        mSceneView.findViewById(R.id.iv_input_icon_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                mSetPasswordEditView.setTransformationMethod(view.isSelected() ? HideReturnsTransformationMethod.getInstance
                        () : PasswordTransformationMethod.getInstance());
                mSetPasswordEditView.setSelection(mSetPasswordEditView.getText().length());
            }
        });
        mSubmitButton = mSceneView.findViewById(R.id.btn_submit_password);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mCodeEditView.getText().toString())) {
                    mCodeEditView.setError("");
                    mCodeEditView.requestFocus();
                } else if (TextUtils.isEmpty(mSetPasswordEditView.getText().toString())) {
                    mSetPasswordEditView.setError("");
                    mCodeEditView.requestFocus();
                } else {
                    submitListener.onSubmit(view);
                }
            }
        });
        mCodeEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mSubmitButton.setSelected(canSubmitPassword());
            }
        });
        mSetPasswordEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mSubmitButton.setSelected(canSubmitPassword());
            }
        });
    }

    private SetPasswordScene(ViewGroup sceneRoot, int layoutId, Context context) {
        this(sceneRoot, LayoutInflater.from(context).inflate(layoutId, sceneRoot, false));
    }

    public String getVaildCode() {
        return mCodeEditView.getText().toString();
    }

    public String getPassword() {
        return EncryptUtil.md5(mSetPasswordEditView.getText().toString());
    }

    private boolean canSubmitPassword() {
        boolean firstNameNotNull = mCodeEditView.getText().length() > 0;
        boolean secondNameNotNull = mSetPasswordEditView.getText().length() > 0;
        return firstNameNotNull && secondNameNotNull;
    }
}
