/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuhaiyang.looping.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import com.yuhaiyang.looping.viewpager.adapter.LoopingViewPagerAdapter;
import com.yuhaiyang.looping.viewpager.utils.UnitUtils;

public class LoopingViewPager extends ViewPager {
    private static final String TAG = "LoopingViewPager";
    /**
     * 最大增幅
     */
    private static final float CURRENT_MAX_INCREASE = 0.35f;

    private int mIndicatorRadius;
    private int mCurrentIndicatorRadius;
    private int mNextIndicatorRadius;

    private int mIndicatorColor;
    private int mIndicatorGravity;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mRealCurrentPosition;

    private Paint mIndicatorPaint;
    private Paint mIndicatorBorderPaint;
    protected LoopingViewPagerAdapter mAdapter;

    public LoopingViewPager(Context context) {
        this(context, null);
    }

    public LoopingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopingViewPager);
        mIndicatorRadius = a.getDimensionPixelSize(R.styleable.LoopingViewPager_indicatorRadius, getDefaultIndicatorRadius());
        mIndicatorColor = a.getColor(R.styleable.LoopingViewPager_indicatorColor, Color.WHITE);
        a.recycle();
        init();
    }


    private void init() {
        addOnPageChangeListener(onPageChangeListener);

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setDither(true);
        mIndicatorPaint.setColor(mIndicatorColor);

        mIndicatorBorderPaint = new Paint();
        mIndicatorBorderPaint.setAntiAlias(true);
        mIndicatorBorderPaint.setDither(true);
        mIndicatorBorderPaint.setColor(Color.GRAY);
        mIndicatorBorderPaint.setStyle(Paint.Style.STROKE);

        mIndicatorWidth = 4 * mIndicatorRadius;
        mIndicatorHeight = 6 * mIndicatorRadius;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter instanceof LoopingViewPagerAdapter) {
            mAdapter = (LoopingViewPagerAdapter) adapter;
            super.setAdapter(adapter);
        } else {
            throw new IllegalArgumentException("need set a LoopingViewPagerAdapter");
        }
    }

    @Override
    public int getCurrentItem() {
        return mAdapter != null ? mAdapter.getRealPosition(super.getCurrentItem()) : 0;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mAdapter.getInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawIndicator(canvas);
    }


    private void drawIndicator(Canvas canvas) {
        final int count = mAdapter.getRealCount();
        final int realWidth = mIndicatorWidth * count;
        final int startX = getWidth() / 2 - realWidth / 2 + getScrollX();
        final int y = getBottom() - mIndicatorHeight / 2;
        for (int i = 0; i < count; i++) {
            // 求圆圈的圆心坐标
            int x = startX + i * mIndicatorWidth + (mIndicatorWidth - mIndicatorRadius) / 2;
            if (i == mRealCurrentPosition) {
                canvas.drawCircle(x, y, mCurrentIndicatorRadius, mIndicatorPaint);
                canvas.drawCircle(x, y, mCurrentIndicatorRadius, mIndicatorBorderPaint);
            } else if (i == mRealCurrentPosition + 1) {
                canvas.drawCircle(x, y, mNextIndicatorRadius, mIndicatorPaint);
                canvas.drawCircle(x, y, mNextIndicatorRadius, mIndicatorBorderPaint);
            } else {
                canvas.drawCircle(x, y, mIndicatorRadius, mIndicatorPaint);
                canvas.drawCircle(x, y, mIndicatorRadius, mIndicatorBorderPaint);
            }
        }
    }


    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            if (mAdapter == null) {
                Log.i(TAG, "onPageSelected: adapter is null");
                return;
            }

            mRealCurrentPosition = mAdapter.getRealPosition(position);
            if (mPreviousPosition != mRealCurrentPosition) {
                mPreviousPosition = mRealCurrentPosition;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mAdapter == null) {
                Log.i(TAG, "onPageScrolled: adapter is null");
                return;
            }
            mCurrentIndicatorRadius = (int) (mIndicatorRadius + mIndicatorRadius * (1 - positionOffset) * CURRENT_MAX_INCREASE);
            mNextIndicatorRadius = (int) (mIndicatorRadius + mIndicatorRadius * positionOffset * CURRENT_MAX_INCREASE);


            mRealCurrentPosition = mAdapter.getRealPosition(position);
            if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0 || position == mAdapter.getCount() - 1)) {
                setCurrentItem(mRealCurrentPosition, false);
            }

            mPreviousOffset = positionOffset;

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mAdapter == null) {
                Log.i(TAG, "onPageScrollStateChanged: adapter is null");
                return;
            }
            int position = LoopingViewPager.super.getCurrentItem();
            int realPosition = mAdapter.getRealPosition(position);
            if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0 || position == mAdapter.getCount() - 1)) {
                setCurrentItem(realPosition, false);
            }
        }
    };


    // *********************************
    public int getDefaultIndicatorRadius() {
        return UnitUtils.dip2px(4);
        //return getContext().getResources().getDimensionPixelSize()
    }
}
