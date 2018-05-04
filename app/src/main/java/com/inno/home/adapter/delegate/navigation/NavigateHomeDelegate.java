package com.inno.home.adapter.delegate.navigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.NavigateHomeAdapter;
import com.inno.home.adapter.delegate.BaseDelegateAdapter;
import com.inno.home.model.navigate.HomeModel;

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
        TextView home_title;
        RecyclerView rv_home_list;

        public HomeViewHolder(View itemView) {
            super(itemView);
            home_title = itemView.findViewById(R.id.home_title);
            rv_home_list = itemView.findViewById(R.id.rv_home_list);
        }

        @Override
        public void bind(HomeModel homeModel) {
            rv_home_list.setLayoutManager(new LinearLayoutManager(rv_home_list.getContext()));
            rv_home_list.setAdapter(new NavigateHomeAdapter(homeModel.homeItemModels));
        }
    }
}
