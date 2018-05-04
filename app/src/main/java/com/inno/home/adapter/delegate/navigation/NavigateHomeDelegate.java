package com.inno.home.adapter.delegate.navigation;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inno.home.R;
import com.inno.home.adapter.NavigateHomeAdapter;
import com.inno.home.adapter.delegate.BaseDelegateAdapter;
import com.inno.home.model.navigate.HomeModel;
import com.inno.home.widget.SlideEditRecycleView;

public class NavigateHomeDelegate extends BaseDelegateAdapter {

    @Override
    public int getItemViewType() {
        return HomeModel.ITEM_TYPE_HOME;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .navigation_home, parent, false));
    }

    public class HomeViewHolder extends BaseViewHolder<HomeModel> {
        ConstraintLayout title_view;
        ImageView iv_nv_flag;
        SlideEditRecycleView rv_home_list;

        public HomeViewHolder(View itemView) {
            super(itemView);
            title_view = itemView.findViewById(R.id.title_view);
            iv_nv_flag = itemView.findViewById(R.id.iv_nv_flag);
            rv_home_list = itemView.findViewById(R.id.rv_home_list);
        }

        @Override
        public void bind(HomeModel homeModel) {
            title_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    rv_home_list.setVisibility(v.isSelected() ? View.VISIBLE : View.GONE);
                    iv_nv_flag.setRotation(v.isSelected() ? 90f : 0);

                }
            });
            rv_home_list.setLayoutManager(new LinearLayoutManager(rv_home_list.getContext()));
            rv_home_list.setAdapter(new NavigateHomeAdapter(homeModel.homeItemModels));
            rv_home_list.setSingle(true);
        }
    }
}
