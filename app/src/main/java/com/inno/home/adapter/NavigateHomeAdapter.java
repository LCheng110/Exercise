package com.inno.home.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.viewholder.SlideEditViewHolder;
import com.inno.home.controller.dialog.DialogHelper;
import com.inno.home.model.navigate.HomeModel;

import java.util.List;

public class NavigateHomeAdapter extends RecyclerView.Adapter<NavigateHomeAdapter.HomeItemViewHolder> {

    private List<HomeModel.HomeItemModel> itemModelList;

    public NavigateHomeAdapter(List<HomeModel.HomeItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    @Override
    public HomeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nv_home, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return itemModelList != null ? itemModelList.size() + 1 : 1;
    }

    public class HomeItemViewHolder extends SlideEditViewHolder {
        TextView home_name;
        ImageView iv_shared, iv_selected;
        View alertView;
        TextView alert_prompt;
        EditText alert_edit;

        public HomeItemViewHolder(View itemView) {
            super(itemView);
            home_name = itemView.findViewById(R.id.home_name);
            iv_shared = itemView.findViewById(R.id.iv_shared);
            iv_selected = itemView.findViewById(R.id.iv_selected);
            alertView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.dialog_alert,
                    (ViewGroup) itemView, false);
            alert_prompt = alertView.findViewById(R.id.alert_prompt);
            alert_edit = alertView.findViewById(R.id.alert_edit);
        }

        public void bind(final int position) {
            if (itemModelList == null || itemModelList.size() == position) {
                home_name.setText(R.string.main_text_home_add);
                iv_shared.setImageResource(R.drawable.ic_nv_home_add);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRenameDialog("");
                    }
                });
            } else {
                iv_shared.setImageResource(itemModelList.get(position).isSharedHome ? R.drawable.ic_nv_shared : 0);
                home_name.setText(itemModelList.get(position).homeName);
                iv_selected.setVisibility(itemModelList.get(position).isSelected ? View.VISIBLE : View.GONE);
                if (itemModelList.get(position).isSharedHome) {
                    hideDeleteItem();
                    setEditText(R.string.edit_item_leave);
                } else {
                    setEditText(R.string.edit_item_rename);
                    setOnDeleteListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteHome(position);
                        }
                    });
                }
                setOnEditListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemModelList.get(position).isSharedHome) {
                            deleteHome(position);
                        } else {
                            showRenameDialog(home_name.getText().toString());
                        }
                    }
                });
            }
        }

        private void showRenameDialog(String name) {
            alert_prompt.setVisibility(View.GONE);
            alert_edit.setHint(R.string.main_dialog_text_home_name);
            alert_edit.setText(name);
            final AlertDialog dialog = new DialogHelper()
                    .init(itemView.getContext())
                    .setTitle(R.string.main_dialog_text_home_title)
                    .setContentView(alertView)
                    .setPositiveListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
            alert_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(s.length() != 0);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        private void deleteHome(int position) {
            itemModelList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, itemModelList.size());
        }
    }

    public void selectHome(int position) {
        for (int i = 0; i < itemModelList.size(); i++) {
            itemModelList.get(i).isSelected = position == i;
        }
        notifyDataSetChanged();
    }
}
