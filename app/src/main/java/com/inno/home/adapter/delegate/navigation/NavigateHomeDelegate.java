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
import com.inno.home.listen.click.OnItemClickListener;
import com.inno.home.model.event.HomeSelectEvent;
import com.inno.home.model.navigate.HomeModel;
import com.inno.home.widget.SlideEditRecycleView;

import org.greenrobot.eventbus.EventBus;

public class NavigateHomeDelegate extends BaseDelegateAdapter {

    private int selectHomePosition;

    public NavigateHomeDelegate(int selectHomePosition) {
        this.selectHomePosition = selectHomePosition;
    }

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
        ConstraintLayout home_title_view;
        ImageView iv_nv_flag;
        SlideEditRecycleView rv_home_list;

        public HomeViewHolder(View itemView) {
            super(itemView);
            home_title_view = itemView.findViewById(R.id.home_title_view);
            iv_nv_flag = itemView.findViewById(R.id.iv_nv_flag);
            rv_home_list = itemView.findViewById(R.id.rv_home_list);
        }

        @Override
        public void bind(final HomeModel homeModel) {
            home_title_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    rv_home_list.setVisibility(v.isSelected() ? View.VISIBLE : View.GONE);
                    iv_nv_flag.setRotation(v.isSelected() ? 90f : 0);

                }
            });
            rv_home_list.setLayoutManager(new LinearLayoutManager(rv_home_list.getContext()));
            final NavigateHomeAdapter homeAdapter = new NavigateHomeAdapter(homeModel.homeItemModels);
            homeAdapter.selectHome(selectHomePosition);
            rv_home_list.setAdapter(homeAdapter);
            rv_home_list.setOnItemClick(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    homeAdapter.selectHome(position);
                    EventBus.getDefault().post(new HomeSelectEvent(homeModel.homeItemModels.get(position)));
                    homeAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
