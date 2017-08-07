package com.administrator.litian20170807;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/8/7.
 */

public class VerticalScrollerLayout extends ViewGroup {

    /**
     * 屏幕高度
     */
    private int mScreenHeight;

    /**
     * 手指上次触摸事件的y轴位置
     */
    private int mLastY;

    /**
     * 点击时y轴的偏移量
     */
    private int mStart;

    /**
     * 滚动控件
     */
    private Scroller mScroller;

    /**
     * 最小移动距离判定
     */
    private int mTouchSlop;

    /**
     * 滑动结束的偏移量
     */
    private int mEnd;

    /**
     * 初始按下y轴坐标
     */
    private int mDownStartX;

    /**
     * 记录当前y轴坐标
     */
    private int y;


    public VerticalScrollerLayout(Context context) {
        super(context, null);
    }

    public VerticalScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenHeight = dm.heightPixels;

        mScroller = new Scroller(context);

        /**
         * 获取最小移动距离
         */
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            // 手动获取childView的高度，此为大坑
            int childHeight = MeasureSpec.makeMeasureSpec(mScreenHeight,MeasureSpec.EXACTLY);
            measureChild(childView, widthMeasureSpec, childHeight);
        }


        Log.i("info", "onMeasure:"+getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = childCount*mScreenHeight;
        setLayoutParams(mlp);


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
            Log.i("info", "childViewHeight:" + i + "````" + getChildAt(i).getMeasuredHeight());
        }
        //Log.i("info", "onLayout" + "``" + changed);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownStartX = (int) ev.getY();
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(y-mDownStartX)>mTouchSlop){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                Log.i("info","mLastY"+mLastY+"y"+y);
                int dy = mLastY - y;

                if (getScrollY() < 0) {
                    dy = 0;
                }

                Log.i("info", "scroll:" + getScrollY() + "height:" + getHeight() + "mScreenHeight:" + mScreenHeight + "dy" + dy);
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;
                }

                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();//偏移量，初始y-移动后的y
                int dScrollY = mEnd - mStart;
                // Log.i("info",dScrollY);
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断scroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(0, mScroller.getCurrY());
            //刷新试图
            postInvalidate();
        }
    }
}
