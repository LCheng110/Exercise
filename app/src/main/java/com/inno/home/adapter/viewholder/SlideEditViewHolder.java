package com.inno.home.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.inno.home.R;

public class SlideEditViewHolder extends RecyclerView.ViewHolder {
    TextView tv_item_edit, tv_item_delete;

    public SlideEditViewHolder(View itemView) {
        super(itemView);
        tv_item_edit = itemView.findViewById(R.id.tv_item_edit);
        tv_item_delete = itemView.findViewById(R.id.tv_item_delete);
    }

    public void setOnEditListener(View.OnClickListener clickListener) {
        tv_item_edit.setOnClickListener(clickListener);
    }

    public void setOnDeleteListener(View.OnClickListener clickListener) {
        tv_item_delete.setOnClickListener(clickListener);
    }
}
