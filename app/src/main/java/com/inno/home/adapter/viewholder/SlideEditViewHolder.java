package com.inno.home.adapter.viewholder;

import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.widget.SlideEditRecycleView;

public abstract class SlideEditViewHolder extends BaseViewHolder {
    private LinearLayout ll_item_root;
    private TextView tv_item_edit, tv_item_delete;
    private FrameLayout.LayoutParams layoutParams;
    private SlideEditRecycleView.OnItemClickListener onItemClickListener;

    public SlideEditViewHolder(View itemView) {
        super(itemView);
        ll_item_root = itemView.findViewById(R.id.ll_item_root);
        tv_item_edit = itemView.findViewById(R.id.tv_item_edit);
        tv_item_delete = itemView.findViewById(R.id.tv_item_delete);
        layoutParams = (FrameLayout.LayoutParams) ll_item_root.getLayoutParams();
    }

    public void setEditText(String text) {
        tv_item_edit.setText(text);
    }

    public void setEditText(@StringRes int stringId) {
        tv_item_edit.setText(stringId);
    }

    public void setDeleteText(String text) {
        tv_item_delete.setText(text);
    }

    public void setDeleteText(@StringRes int stringId) {
        tv_item_delete.setText(stringId);
    }

    public void hideEditItem() {
        tv_item_edit.setVisibility(View.GONE);
        if (layoutParams.getMarginEnd() ==
                tv_item_edit.getResources().getDimensionPixelSize(R.dimen.item_edit_hide_width)) {
            layoutParams.setMarginEnd(tv_item_edit.getResources().getDimensionPixelSize(R.dimen.item_edit_hide_width_single));
        } else if (layoutParams.getMarginEnd() ==
                tv_item_edit.getResources().getDimensionPixelSize(R.dimen.item_edit_hide_width_single)) {
            layoutParams.setMarginEnd(0);
        }
    }

    public void hideDeleteItem() {
        tv_item_delete.setVisibility(View.GONE);
        if (layoutParams.getMarginEnd() ==
                tv_item_edit.getResources().getDimensionPixelSize(R.dimen.item_edit_hide_width)) {
            layoutParams.setMarginEnd(tv_item_edit.getResources().getDimensionPixelSize(R.dimen.item_edit_hide_width_single));
        } else if (layoutParams.getMarginEnd() ==
                tv_item_edit.getResources().getDimensionPixelSize(R.dimen.item_edit_hide_width_single)) {
            layoutParams.setMarginEnd(0);
        }
    }

    public void setOnEditListener(final View.OnClickListener clickListener) {
        tv_item_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick();
                }
                clickListener.onClick(v);
            }
        });
    }

    public void setOnDeleteListener(final View.OnClickListener clickListener) {
        tv_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick();
                }
                clickListener.onClick(v);
            }
        });
    }

    public void setOnItemClickListener(SlideEditRecycleView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getVisibilityCount() {
        int count = 0;
        if (tv_item_edit.getVisibility() == View.VISIBLE) {
            count++;
        }
        if (tv_item_delete.getVisibility() == View.VISIBLE) {
            count++;
        }
        Log.i("sss", "getVisibilityCount: "+count);
        return count;
    }
}
