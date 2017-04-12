package com.gloomyer.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 实现切割线的RecyclerViewItemDecoration
 */

public class CutofRuleItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;

    public CutofRuleItemDecoration(Context mContext) {
        TypedArray a = mContext.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public CutofRuleItemDecoration(Drawable mDivider) {
        this.mDivider = mDivider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
        if (mLayoutManager instanceof LinearLayoutManager
                && !(mLayoutManager instanceof GridLayoutManager)) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutManager;
            int orientation = layoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                //纵向
                drawVertical(c, parent);
            } else {
                //横向
                drawHorizontal(c, parent);
            }
        } else if (mLayoutManager instanceof GridLayoutManager) {
            //GridLayoutManager layoutManager = (GridLayoutManager) mLayoutManager;
            drawGrid(c, parent);
        }
    }

    /**
     * 绘制表格的间隔线
     *
     * @param canvas
     * @param recyclerView
     */
    private void drawGrid(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            int pos = recyclerView.getChildPosition(child);
            //先绘制底部的
            {
                int left = child.getLeft() + child.getPaddingLeft() + lp.leftMargin;
                int right = child.getRight() - child.getPaddingRight() - lp.rightMargin;
                int top = child.getBottom() - child.getPaddingBottom() - lp.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }

            {
                //绘制纵向的
                if (pos % 3 == 2)
                    continue;//如果是最后一列就不绘制纵向的
                int left = child.getRight() - child.getPaddingRight() - lp.rightMargin;
                int right = left + mDivider.getIntrinsicWidth();
                int top = child.getTop() + child.getPaddingTop() + lp.topMargin;
                int bottom = child.getBottom() - child.getPaddingBottom() - lp.bottomMargin + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    /**
     * 绘制横向风格县
     *
     * @param canvas
     * @param recyclerView
     */
    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        int top = recyclerView.getTop() + recyclerView.getPaddingTop();
        int bottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
        if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;
            top += lp.topMargin;
            bottom -= lp.bottomMargin;
        }
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() - child.getPaddingRight() - lp.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * 绘制纵向切割线
     *
     * @param canvas
     * @param recyclerView
     */
    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        int left = recyclerView.getLeft() + recyclerView.getPaddingLeft();
        int right = recyclerView.getRight() - recyclerView.getPaddingRight();

        if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;
            left += lp.leftMargin;
            right -= lp.rightMargin;
        }

        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() - child.getPaddingBottom() - lp.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 0);        //相当于调用super
        RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
        if (mLayoutManager instanceof LinearLayoutManager
                && !(mLayoutManager instanceof GridLayoutManager)) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutManager;
            int orientation = layoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                //纵向
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                //横向
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        } else if (mLayoutManager instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) mLayoutManager;
            int pos = parent.getChildPosition(view);//坐标
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            if (pos % 3 == 2)//如果是最后一列，右边不设置因为不画线
                outRect.right = 0;
        }
    }
}
