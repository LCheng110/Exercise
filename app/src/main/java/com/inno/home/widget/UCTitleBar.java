package com.inno.home.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inno.home.R;


/**
 * Created by rookie on 17/3/3.
 * <p>
 * 通用标题栏
 */

public class UCTitleBar extends LinearLayout {

    private Context context;
    private View root, bar_divider;
    private ImageButton ibtn_left, ibtn_right;
    private TextView bar_title, tv_menu, tv_back;
    private RelativeLayout bar_rl_root;

    public UCTitleBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public UCTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        root = View.inflate(context, R.layout.uc_title_bar, null);
        ibtn_left = (ImageButton) root.findViewById(R.id.ibtn_left);
        tv_back = root.findViewById(R.id.tv_back);
        ibtn_right = (ImageButton) root.findViewById(R.id.ibtn_right);
        bar_title = (TextView) root.findViewById(R.id.bar_title);
        tv_menu = (TextView) root.findViewById(R.id.tv_menu);
        bar_divider = root.findViewById(R.id.bar_divider);
        bar_rl_root = (RelativeLayout) root.findViewById(R.id.bar_rl_root);
        addView(root);
    }

    /**
     * 设置标题
     *
     * @param text
     * @return
     */
    public UCTitleBar setTitleText(String text) {
        if (bar_title.getVisibility() != VISIBLE) {
            bar_title.setVisibility(VISIBLE);
        }
        bar_title.setText(text);
        return this;
    }

    /**
     * 设置标题
     *
     * @param text
     * @return
     */
    public UCTitleBar setTitleText(@StringRes int text) {
        if (bar_title.getVisibility() != VISIBLE) {
            bar_title.setVisibility(VISIBLE);
        }
        bar_title.setText(text);
        return this;
    }

    /**
     * 设置标题 同时设置文字颜色
     *
     * @param text
     * @param color
     * @return
     */
    public UCTitleBar setTitleText(@StringRes int text, int color) {
        if (bar_title.getVisibility() != VISIBLE) {
            bar_title.setVisibility(VISIBLE);
        }
        bar_title.setText(text);
        bar_title.setTextColor(color);
        return this;
    }

    /**
     * 设置标题 同时设置文字颜色
     *
     * @param text
     * @param color
     * @return
     */
    public UCTitleBar setTitleText(String text, int color) {
        if (bar_title.getVisibility() != VISIBLE) {
            bar_title.setVisibility(VISIBLE);
        }
        bar_title.setText(text);
        bar_title.setTextColor(color);
        return this;
    }

    /**
     * 默认展示返回按钮 设置返回事件
     *
     * @param listener
     * @return
     */
    public UCTitleBar setBackEvent(OnClickListener listener) {
        if (ibtn_left.getVisibility() != VISIBLE) {
            ibtn_left.setVisibility(VISIBLE);
        }
        ibtn_left.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置左侧按钮文字
     *
     * @return
     */
    public UCTitleBar setBackTextEvent(@StringRes int cancel, OnClickListener listener) {
        ibtn_left.setVisibility(GONE);
        tv_back.setVisibility(VISIBLE);
        tv_back.setText(cancel);
        tv_back.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置左侧按钮图片
     *
     * @param drawable
     * @return
     */
    public UCTitleBar setLeftDrawable(int drawable) {
        if (drawable != 0) {
            ibtn_left.setImageResource(drawable);
        }
        return this;
    }

    /**
     * 设置左侧事件 默认返回icon 如不需更改icon drawable 传 0
     *
     * @param listener
     * @param drawable
     * @return
     */
    public UCTitleBar setLeftEvent(OnClickListener listener, int drawable) {
        if (drawable != 0) {
            ibtn_left.setImageResource(drawable);
        }
        ibtn_left.setOnClickListener(listener);
        ibtn_left.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧图片事件 默认搜索icon 如不需更改icon drawable 传 0
     *
     * @param drawable
     * @param listener
     * @return
     */
    public UCTitleBar setRightEvent(int drawable, OnClickListener listener) {
        if (drawable != 0) {
            ibtn_right.setImageResource(drawable);
        }
        ibtn_right.setOnClickListener(listener);
        ibtn_right.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param menu
     * @return
     */
    public UCTitleBar setRightMessage(String menu) {
        if (!TextUtils.isEmpty(menu)) {
            tv_menu.setText(menu);
        }
        tv_menu.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧字体颜色
     *
     * @param color
     * @return
     */
    public UCTitleBar setRightMessageColor(int color) {
        if (color != 0) {
            tv_menu.setTextColor(color);
        }
        tv_menu.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    public UCTitleBar setRightTextColor(int color) {
        tv_menu.setTextColor(color);
        return this;
    }

    /**
     * 设置右侧文字事件 默认文字发送， 如不更改文字 menu 传 空字符串 "" 默认文字ffffff 如不更改 color 传 0
     * 默认字体大小 18 如不更改 传 0
     *
     * @param listener
     * @param menu
     * @return
     */
    public UCTitleBar setRightMenuEvent(OnClickListener listener, String menu, int color, int size) {
        if (!TextUtils.isEmpty(menu)) {
            tv_menu.setText(menu);
        }
        if (color != 0) {
            tv_menu.setTextColor(color);
        }
        if (size != 0) {
            tv_menu.setTextSize(size);
        }
        tv_menu.setOnClickListener(listener);
        tv_menu.setVisibility(VISIBLE);
        return this;
    }

    public UCTitleBar setRightMenuEventVisibility(int visibility) {
        tv_menu.setVisibility(visibility);
        return this;
    }

    public UCTitleBar setRightImageVisibility(int visibility) {
        ibtn_right.setVisibility(visibility);
        return this;
    }

    public TextView getRightTextView() {
        return tv_menu;
    }

    public ImageButton getRightImageView() {
        return ibtn_right;
    }

    /**
     * 展示分割线
     *
     * @return
     */
    public UCTitleBar showBarDivider() {
        bar_divider.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 隐藏分割线
     *
     * @return
     */
    public UCTitleBar hideBarDivider() {
        bar_divider.setVisibility(GONE);
        return this;
    }

    /**
     * 设置分割线色值，同时展示分割线
     *
     * @return
     */
    public UCTitleBar setBarDivider(int color) {
        bar_divider.setVisibility(VISIBLE);
        bar_divider.setBackgroundColor(color);
        return this;
    }

    /**
     * 修改背景色值
     *
     * @param color
     * @return
     */
    public UCTitleBar setBarBackground(int color) {
        root.setBackgroundColor(color);
        return this;
    }

    /**
     * 修改背景色值
     *
     * @param colorRes
     * @return UCTitleBar
     */
    public UCTitleBar setBackground(@ColorRes int colorRes) {
        root.setBackgroundResource(colorRes);
        return this;
    }

    /**
     * 设置透明色
     *
     * @param color
     * @return
     */
    public UCTitleBar setBarTransparent(int color) {
        bar_rl_root.setBackgroundColor(color);
        return this;
    }

    /**
     * 获取根部局
     *
     * @return
     */
    public View getRoot() {
        return root;
    }

}
