package com.inno.home.adapter.divide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liucheng on 2018/4/6.
 * GridLayoutManager 分割线
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    private int divideSpace;

    public GridItemDecoration(Context context, Drawable drawable) {
        mDrawable = drawable;
    }

    public GridItemDecoration(Context context, @DrawableRes int drawableRes) {
        mDrawable = ContextCompat.getDrawable(context, drawableRes);
    }

    public GridItemDecoration(Context context, @ColorRes int colorRes, int space) {
        //获取 Drawable 对象
        mDrawable = new ColorDrawable(ContextCompat.getColor(context, colorRes));
        divideSpace = space;
    }

    /**
     * 基本操作是留出分割线位置
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int bottom;
        int right;
        if (mDrawable instanceof ColorDrawable) {
            bottom = divideSpace;
            right = divideSpace;
        } else {
            bottom = mDrawable.getIntrinsicHeight();
            right = mDrawable.getIntrinsicWidth();
        }
        //如果是最后一列，留出位置
        if (isLastCol(view, parent)) {
            right = 0;
        }
        //如果是最后一行，留出位置
        if (isLastRow(view, parent)) {
            bottom = 0;
        }
        outRect.bottom = bottom;
        outRect.right = right;
    }

    /**
     * 是否是最后一行
     * 当前位置+1 > (行数-1)*列数
     *
     * @return
     */
    private boolean isLastRow(View view, RecyclerView parent) {
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //获取列数
        int mSpanCount = getSpanCount(parent);
        //获取行数 =  总的条目/列数 如果能除尽则为行数，如果除不尽，则为结果+1
        int rowNum = parent.getAdapter().getItemCount() / mSpanCount == 0 ? parent.getAdapter()
                .getItemCount() / mSpanCount : (parent.getAdapter().getItemCount() / mSpanCount + 1);
        return (currentPosition + 1) > (rowNum - 1) * mSpanCount;
    }

    /**
     * 是否是最后一列
     * （当前位置+1）% 列数 ==0，判断是否为最后一列
     *
     * @return
     */
    private boolean isLastCol(View view, RecyclerView parent) {
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //获取列数
        int mSpanCount = getSpanCount(parent);
        return (currentPosition + 1) % mSpanCount == 0;
    }

    /**
     * 获取列数
     * 如果是GridView，就获取列数，如果是ListView，列数就是1
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
        if (mLayoutManager instanceof GridLayoutManager) {
            GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
            return mGridLayoutManager.getSpanCount();
        }
        return 1;
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //绘制水平方向
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            //获取每个子布局
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) mChildView
                    .getLayoutParams();
            int left = mChildView.getLeft() - mLayoutParams.leftMargin;
            int right = mChildView.getRight() + mDrawable.getIntrinsicWidth() + mLayoutParams
                    .rightMargin;
            int top = mChildView.getBottom() + mLayoutParams.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();

            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }


        //绘制垂直方向
        for (int i = 0; i < mChildCount; i++) {
            //获取每个子布局
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) mChildView
                    .getLayoutParams();
            int left = mChildView.getRight() + mLayoutParams.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            int top = mChildView.getTop() - mLayoutParams.topMargin;
            int bottom = mChildView.getBottom() + mLayoutParams.bottomMargin;
            if (isLastCol(mChildView, parent)) {
                continue;
            }
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }
}