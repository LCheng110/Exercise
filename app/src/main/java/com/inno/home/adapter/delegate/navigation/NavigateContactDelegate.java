package com.inno.home.adapter.delegate.navigation;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.adapter.delegate.BaseDelegateAdapter;
import com.inno.home.model.navigate.ContactModel;

public class NavigateContactDelegate extends BaseDelegateAdapter {

    @Override
    public int getItemViewType() {
        return ContactModel.ITEM_TYPE_CONTACT;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ShareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .navigation_contact,
                parent, false));
    }

    public class ShareViewHolder extends BaseViewHolder<ContactModel> {
        TextView share_title;

        public ShareViewHolder(View itemView) {
            super(itemView);
            share_title = itemView.findViewById(R.id.share_title);
        }

        @Override
        public void bind(ContactModel model) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.showContactUs(v.getContext());
                }
            });
        }
    }
}
