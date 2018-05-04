package com.inno.home.adapter.delegate.navigation;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.delegate.BaseDelegateAdapter;
import com.inno.home.model.navigate.ShareModel;

public class NavigateShareDelegate extends BaseDelegateAdapter {

    @Override
    public int getItemViewType() {
        return ShareModel.ITEM_TYPE_SHARE;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ShareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .navigation_share,
                parent, false));
    }

    public class ShareViewHolder extends BaseViewHolder<ShareModel> {
        TextView share_source;

        public ShareViewHolder(View itemView) {
            super(itemView);
            share_source = itemView.findViewById(R.id.share_source);
        }

        @Override
        public void bind(ShareModel model) {
            share_source.setText(model.shareName);
        }
    }
}
