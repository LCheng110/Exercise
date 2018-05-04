package com.inno.home.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.BaseViewHolder;
import com.inno.home.model.UserInfoModel;
import com.inno.home.utils.glide.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class UserInfoAdapter extends BaseRecycleAdapter {

    private List<UserInfoModel> infoModelList;

    public UserInfoAdapter(List<UserInfoModel> infoModelList) {
        this.infoModelList = infoModelList;
    }

    @Override
    public UserInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserInfoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_info, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return infoModelList == null ? 0 : infoModelList.size();
    }

    public class UserInfoViewHolder extends BaseViewHolder {

        @BindView(R.id.info_title)
        TextView info_title;
        @BindView(R.id.info_avatar)
        ImageView info_avatar;
        @BindView(R.id.info_content)
        TextView info_content;

        public UserInfoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            info_title.setText(infoModelList.get(position).userTitle);
            if (!TextUtils.isEmpty(infoModelList.get(position).userContent)) {
                info_content.setText(infoModelList.get(position).userContent);
            }
            if (!TextUtils.isEmpty(infoModelList.get(position).userAvatar)) {
                ImageLoader.loadCircleImage(info_avatar.getContext(),
                        infoModelList.get(position).userAvatar, info_avatar);
            }
        }
    }
}
