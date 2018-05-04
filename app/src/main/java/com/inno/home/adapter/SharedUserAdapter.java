package com.inno.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.SlideEditViewHolder;

import butterknife.BindView;

public class SharedUserAdapter extends RecyclerView.Adapter<SharedUserAdapter.SharedUserViewHolder> {

    @Override
    public SharedUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SharedUserViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shared_user, parent, false));
    }

    @Override
    public void onBindViewHolder(SharedUserViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class SharedUserViewHolder extends SlideEditViewHolder {
        @BindView(R.id.shared_user_avatar)
        ImageView shared_user_avatar;
        @BindView(R.id.shared_user_name)
        TextView shared_user_name;
        @BindView(R.id.shared_user_email)
        TextView shared_user_email;

        public SharedUserViewHolder(View itemView) {
            super(itemView);
            hideEditItem();
        }

        @Override
        public void bind(final int position) {
//            tv_device_name.setText(String.valueOf(position));

        }
    }
}
