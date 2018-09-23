package com.example.zhumingren.percentheightdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ZhuMingren on 2017/10/14.
 */

public class PercentLinearLayout extends LinearLayout {

    private static final float heightDivideWidth = 0.5f;

    public PercentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        /**
         * 按照比例改变高度值
         */
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize * heightDivideWidth), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
