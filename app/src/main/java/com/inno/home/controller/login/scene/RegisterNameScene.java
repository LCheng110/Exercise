package com.inno.home.controller.login.scene;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.utils.ToastUtil;

/**
 * Created by lcheng on 2018/4/17.
 */

public class RegisterNameScene extends SubmitBaseScene {

    // Register Name View references.
    private TextView mRegisterPromptView;
    private TextInputLayout tl_input_first, tl_input_second;
    private EditText mRegisterNameFirstEditView;
    private ImageView mNameFirstClearView, mNameSecondClearView;
    private EditText mRegisterNameSecondEditView;
    private Button mSubmitButton;

    @NonNull
    public static RegisterNameScene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId,
                                                      @NonNull Context context) {
        @SuppressWarnings("unchecked")
        SparseArray<SubmitBaseScene> scenes =
                (SparseArray<SubmitBaseScene>) sceneRoot.getTag(android.support.transition.R.id.transition_scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTag(android.support.transition.R.id.transition_scene_layoutid_cache, scenes);
        }
        RegisterNameScene scene = (RegisterNameScene) scenes.get(layoutId);
        if (scene != null) {
            return scene;
        } else {
            scene = new RegisterNameScene(sceneRoot, layoutId, context);
            scenes.put(layoutId, scene);
            return scene;
        }
    }

    public RegisterNameScene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        super(sceneRoot, layout);
        mRegisterPromptView = mSceneView.findViewById(R.id.input_prompt);
        tl_input_first = mSceneView.findViewById(R.id.tl_input_first);
        tl_input_second = mSceneView.findViewById(R.id.tl_input_second);
        mRegisterNameFirstEditView = mSceneView.findViewById(R.id.ev_input_first);
        mNameFirstClearView = mSceneView.findViewById(R.id.iv_input_icon_first);
        mRegisterNameSecondEditView = mSceneView.findViewById(R.id.ev_input_second);
        mNameSecondClearView = mSceneView.findViewById(R.id.iv_input_icon_second);
        mSubmitButton = mSceneView.findViewById(R.id.btn_submit_name);
        mRegisterNameFirstEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mNameFirstClearView.setVisibility(editable.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                mSubmitButton.setSelected(canSubmitName());
            }
        });
        mRegisterNameSecondEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mNameSecondClearView.setVisibility(editable.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                mSubmitButton.setSelected(canSubmitName());
            }
        });
        mNameFirstClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterNameFirstEditView.setText(null);
            }
        });
        mNameSecondClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterNameSecondEditView.setText(null);
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mRegisterNameFirstEditView.getText().toString())) {
                    tl_input_first.setErrorEnabled(true);
                    tl_input_first.setError("ssss");
                    mRegisterNameFirstEditView.requestFocus();
                    ToastUtil.showToast("请先填写完整");
                } else if (TextUtils.isEmpty(mRegisterNameSecondEditView.getText().toString())) {
                    tl_input_second.setErrorEnabled(true);
                    mRegisterNameSecondEditView.requestFocus();
                    ToastUtil.showToast("请先填写完整");
                } else {
                    submitListener.onSubmit(view);
                }
            }
        });
    }

    private RegisterNameScene(ViewGroup sceneRoot, int layoutId, Context context) {
        this(sceneRoot, LayoutInflater.from(context).inflate(layoutId, sceneRoot, false));
    }

    public String getFirstName() {
        return mRegisterNameFirstEditView.getText().toString();
    }

    public String getLastName() {
        return mRegisterNameSecondEditView.getText().toString();
    }

    private boolean canSubmitName() {
        boolean firstNameNotNull = mRegisterNameFirstEditView.getText().length() > 0;
        boolean secondNameNotNull = mRegisterNameSecondEditView.getText().length() > 0;
        return firstNameNotNull && secondNameNotNull;
    }
}
