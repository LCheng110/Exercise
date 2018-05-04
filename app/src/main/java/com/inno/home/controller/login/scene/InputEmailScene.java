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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;

/**
 * Created by lcheng on 2018/4/17.
 */

public class InputEmailScene extends SubmitBaseScene {

    // Email View references.
    private TextView mEmailPromptView;
    private AutoCompleteTextView mEmailEditView;
    private ImageView mEmailClearView;

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
            scene = new InputEmailScene(sceneRoot, layoutId, context);
            scenes.put(layoutId, scene);
            return scene;
        }
    }

    public InputEmailScene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        super(sceneRoot, layout);
        mEmailPromptView = mSceneView.findViewById(R.id.input_prompt);
        mEmailEditView = mSceneView.findViewById(R.id.ev_input_first);
        mEmailClearView = mSceneView.findViewById(R.id.iv_input_icon_first);
        mEmailEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mEmailClearView.setVisibility(editable.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });
        mEmailClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmailEditView.setText(null);
            }
        });
        mSceneView.findViewById(R.id.btn_submit_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitListener.onSubmit(view);
            }
        });
    }

    private InputEmailScene(ViewGroup sceneRoot, int layoutId, Context context) {
        this(sceneRoot, LayoutInflater.from(context).inflate(layoutId, sceneRoot, false));
    }
}
