package com.inno.home.controller.login.scene;

import android.support.annotation.NonNull;
import android.support.transition.Scene;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lcheng on 2018/4/17.
 */

public class SubmitBaseScene extends Scene {

    public View mSceneView;
    OnSubmitListener submitListener;

    public SubmitBaseScene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        super(sceneRoot, layout);
        mSceneView = layout;
    }

    public void setSubmitListener(OnSubmitListener submitListener) {
        this.submitListener = submitListener;
    }

    public interface OnSubmitListener {
        void onSubmit(View view);
    }
}
