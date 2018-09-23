package com.zhumingren.viewdraghelperdemo.View;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * ViewDragHelper实现下拉Drawer
 */
public class TopDrawerLayout extends ViewGroup {

    public static final String TAG = TopDrawerLayout.class.getSimpleName();
    /**
     * Minimum velocity that will be detected as a fling
     */
    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    private View mTopMenuView;

    private ViewDragHelper mHelper;

    public TopDrawerLayout(Context context) {
        this(context, null);
    }

    public TopDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;

        mHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.d(TAG, "clampViewPositionVertical ");
                int newTop = Math.max(-child.getHeight(), Math.min(top, 0));
                Log.d(TAG, "clampViewPositionVertical top = " + top + " dy = " + dy + " newTop = " + newTop);
                return newTop;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                Log.d(TAG, "getViewVerticalDragRange");
                return mTopMenuView == child ? child.getHeight() : 0;
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                final int childHeight = releasedChild.getHeight();
                int getTop = releasedChild.getTop();
                float offset = (childHeight + getTop) * 1.0f / childHeight;
                int finalTop;
                // 手抬起来后的位置超过父View的一半
                if (offset > 0.5f) {
                    if (yvel < -minVel) {
                        finalTop = -releasedChild.getHeight();
                    } else {
                        finalTop = 0;
                    }
                } else {
                    // 向上滑动
                    if (yvel < -minVel) {
                        finalTop = -releasedChild.getHeight();
                    } else if (yvel > minVel) {
                        finalTop = 0;
                    } else {
                        Log.e(TAG, "onViewReleased yvel = " + yvel + " minVel = " + minVel);
                        finalTop = -releasedChild.getHeight();
                    }
                }
                Log.d(TAG, "onViewRealeased getTop = " + getTop + " yvel = " + yvel + " finalTop = " + finalTop + " offset = " + offset);
                mHelper.settleCapturedViewAt(releasedChild.getLeft(), finalTop);
                invalidate();
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                Log.d(TAG, "onViewPositionChanged top = " + top);
                final int childHeight = changedView.getHeight();
                float offset = (float) (childHeight + top) / childHeight;
                Log.d(TAG, "onViewPositionChanged changedView.visibility = " + changedView.getVisibility() + " offset = " + offset);
                invalidate();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                Log.d(TAG, "onEdgeDragStarted pointerId = " + pointerId);
                mHelper.captureChildView(mTopMenuView, pointerId);
            }

            @Override
            public boolean tryCaptureView(View view, int i) {
                Log.d(TAG, "tryCaptureView");
                return view == mTopMenuView;
            }
        });
        mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
        mHelper.setMinVelocity(minVel);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure widthSize = " + widthSize + " heightSize = " + heightSize);

        setMeasuredDimension(widthSize, heightSize);
        if (getChildCount() > 1) {
            throw new IllegalArgumentException("Child Views can only be one, you have set " + getChildCount()
                    + " child views in " + TopDrawerLayout.class.getSimpleName());
        }

        View topMenuView = getChildAt(0);
        MarginLayoutParams lp = (MarginLayoutParams)
                topMenuView.getLayoutParams();

        final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                lp.leftMargin + lp.rightMargin, lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                lp.topMargin + lp.bottomMargin,
                lp.height);
        topMenuView.measure(drawerWidthSpec, drawerHeightSpec);

        mTopMenuView = topMenuView;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        Log.d(TAG, "onLayout measuredWidth = " + mTopMenuView.getMeasuredWidth() + " measuredHeight = " + mTopMenuView.getMeasuredHeight());
        //MarginLayoutParams marginLayoutParams = (MarginLayoutParams) mTopMenuView.getLayoutParams();
        mTopMenuView.layout(0, -mTopMenuView.getMeasuredHeight(), mTopMenuView.getMeasuredWidth(), 0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void closeDrawer() {
        mHelper.smoothSlideViewTo(mTopMenuView, 0, -mTopMenuView.getHeight());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void openDrawer() {
        View menuView = mTopMenuView;
        mHelper.smoothSlideViewTo(menuView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 显示到该View高度的百分比
     *
     * @param verticalPercent
     */
    public void openDrawerTo(float verticalPercent) {
        if (verticalPercent > 1 || verticalPercent < 0) {
            throw new IllegalArgumentException("Argument verticalPercent is int in (0,1)");
        }
        View menuView = mTopMenuView;
        int top = (int) (menuView.getHeight() * (verticalPercent - 1.0f));
        mHelper.smoothSlideViewTo(menuView, 0, top);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

}
