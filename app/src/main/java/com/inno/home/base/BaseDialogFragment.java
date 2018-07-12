package com.inno.home.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.inno.home.R;

import butterknife.ButterKnife;


/**
 * Created by qhm on 2017/6/9
 * <p>
 * PopupWindow base类
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected View view;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(thisLayout(), container, false);
        ButterKnife.bind(this, view);
        initBasicView();
        return view;
    }

    public void initBasicView() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().setWindowAnimations(R.style.PopupWindowBottomAnimation);
        }
        int dividerId = getContext().getResources().getIdentifier("android:id/titleDivider",
                null, null);
        View divider = getDialog().findViewById(dividerId);
        if (divider != null) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }
        doInitView();
        doInitData();
    }

    public abstract int thisLayout();

    public abstract void doInitView();

    public abstract void doInitData();

    public void setGravity(int gravity) {
        if (getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = gravity;
            window.setAttributes(layoutParams);
        }
    }
}
