package com.inno.home.adapter.delegate.navigation;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.Navigation;
import com.inno.home.R;
import com.inno.home.adapter.delegate.BaseDelegateAdapter;
import com.inno.home.model.navigate.UserModel;
import com.inno.home.utils.glide.ImageLoader;

public class NavigateUserDelegate extends BaseDelegateAdapter {

    @Override
    public int getItemViewType() {
        return UserModel.ITEM_TYPE_USER;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_header,
                parent, false));
    }

    public class UserViewHolder extends BaseViewHolder<UserModel> {
        ImageView user_avatar, user_message;
        TextView user_name;

        public UserViewHolder(View itemView) {
            super(itemView);
            user_avatar = itemView.findViewById(R.id.user_avatar);
            user_name = itemView.findViewById(R.id.user_name);
            user_message = itemView.findViewById(R.id.user_message);
        }

        @Override
        public void bind(UserModel model) {
            ImageLoader.loadCircleImage(user_avatar.getContext(), model.userAvatar, user_avatar);
            user_name.setText(model.userName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.showPerson(v.getContext());
                }
            });
        }
    }
}
