package com.inno.home.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

public class PicAdapter extends BaseRecycleAdapter {

    private List<Uri> uriList;

    public PicAdapter(List<Uri> uriList) {
        this.uriList = uriList;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_pic,
                parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return uriList.size() > 3 ? 3 : uriList.size();
    }

    class PicViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_pic)
        ImageView iv_pic;

        public PicViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(final int position) {
            if (uriList.get(position) != null) {
                iv_pic.setImageURI(uriList.get(position));
            } else {
                iv_pic.setImageResource(R.drawable.ic_picture_add);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }
    }
}
