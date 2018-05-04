package com.inno.home.controller.login.scene;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;

/**
 * Created by lcheng on 2018/4/17.
 */

public class RegisterNameScene extends SubmitBaseScene {

    // Register Name View references.
    private TextView mRegisterPromptView;
    private EditText mRegisterNameFirstEditView;
    private ImageView mNameFirstClearView, mNameSecondClearView;
    private EditText mRegisterNameSecondEditView;

    @NonNull
    public static SubmitBaseScene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId,
                                          @NonNull Context context) {
        @SuppressWarnings("unchecked")
        SparseArray<SubmitBaseScene> scenes =
                (SparseArray<SubmitBaseScene>) sceneRoot.getTag(android.support.transition.R.id.transition_scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTag(android.support.transition.R.id.transition_scene_layoutid_cache, scenes);
        }
        SubmitBaseScene scene = scenes.get(layoutId);
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
        mRegisterNameFirstEditView = mSceneView.findViewById(R.id.ev_input_first);
        mNameFirstClearView = mSceneView.findViewById(R.id.iv_input_icon_first);
        mRegisterNameSecondEditView = mSceneView.findViewById(R.id.ev_input_second);
        mNameSecondClearView = mSceneView.findViewById(R.id.iv_input_icon_second);
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
        mSceneView.findViewById(R.id.btn_submit_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitListener.onSubmit(view);
            }
        });
    }

    private RegisterNameScene(ViewGroup sceneRoot, int layoutId, Context context) {
        this(sceneRoot, LayoutInflater.from(context).inflate(layoutId, sceneRoot, false));
    }
}
