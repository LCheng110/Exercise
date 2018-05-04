package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;

public class InvitedSharedUserAdapter extends RecyclerView.Adapter<InvitedSharedUserAdapter.SharedInvitedViewHolder> {

    @Override
    public SharedInvitedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SharedInvitedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shared_invited, parent, false));
    }

    @Override
    public void onBindViewHolder(SharedInvitedViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class SharedInvitedViewHolder extends BaseViewHolder {
        @BindView(R.id.invited_user_avatar)
        ImageView invited_user_avatar;
        @BindView(R.id.invited_user_name)
        TextView invited_user_name;
        @BindView(R.id.invited_user_status)
        TextView invited_user_status;

        public SharedInvitedViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(final int position) {

        }
    }
}
