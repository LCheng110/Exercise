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

public class MarginStartItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    private int divideHorSpace;
    private int divideVerSpace;
    private int mMarginStart;

    public MarginStartItemDecoration(Context context, Drawable drawable) {
        mDrawable = drawable;
    }

    public MarginStartItemDecoration(Context context, @DrawableRes int drawableRes) {
        mDrawable = ContextCompat.getDrawable(context, drawableRes);
        divideHorSpace = mDrawable.getIntrinsicWidth();
        divideVerSpace = mDrawable.getIntrinsicHeight();
    }

    public MarginStartItemDecoration(Context context, @ColorRes int colorRes, int space) {
        //获取 Drawable 对象
        mDrawable = new ColorDrawable(ContextCompat.getColor(context, colorRes));
        divideHorSpace = space;
        divideVerSpace = space;
    }

    public void setmMarginStart(int mMarginStart) {
        this.mMarginStart = mMarginStart;
    }

    /**
     * 基本操作是留出分割线位置
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        //如果是最后一行，留出位置
//        if (isLastRow(view, parent)) {
//            bottom = 0;
//        }
        outRect.bottom = divideVerSpace;
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //绘制水平方向
        int mChildCount = parent.getChildCount();
        int top;
        int bottom;
        int left;
        int right;
        for (int i = 0; i < mChildCount; i++) {
            //获取每个子布局
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();
            left = mChildView.getLeft() + mMarginStart - mLayoutParams.leftMargin;
            right = mChildView.getRight() + mLayoutParams.rightMargin;
            top = mChildView.getBottom() + mLayoutParams.bottomMargin;
            bottom = top + divideVerSpace;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }

    /**
     * 是否是第一行
     */
    private boolean isFirstRow(View view, RecyclerView parent) {
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //获取列数
        int mSpanCount = getSpanCount(parent);
        return currentPosition < mSpanCount;
    }

    /**
     * 是否是最后一行
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
}