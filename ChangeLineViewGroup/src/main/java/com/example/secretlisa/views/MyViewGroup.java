package com.example.secretlisa.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by ZhuMingren on 2017/5/15.
 */

public class MyViewGroup extends ViewGroup {

    private int width;
    private int height;
    private static final String TAG = "zhumr";

    private int limitWidth; // 需要指定整体宽度，不可以是wrap_content, 后期可扩展为比例布局
    private int itemHorizantalMargin; // 可选，各个元素的水平间距
    private int itemVerticalMargin; // 可选，各个元素的数值间距
    private int marginLeft; // 整体布局的左侧margin
    private int marginRight; // 整体布局的右侧margin

    public MyViewGroup(Context context) {
        super(context);
        initParams(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attributeSet) {
        this.limitWidth = getScreenWidth(context);
        this.itemHorizantalMargin = dip2pixel(context, 20);
        this.itemVerticalMargin = dip2pixel(context, 15);
        this.marginLeft = dip2pixel(context, 20);
        this.marginRight = dip2pixel(context, 20);
        if (attributeSet != null) {
            Log.d(TAG, "itemHorizantalMargin = " + itemHorizantalMargin + " itemVerticalMargin = " + itemVerticalMargin);
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyViewGroup);
            this.itemHorizantalMargin = dip2pixel(context, typedArray.getDimension(R.styleable.MyViewGroup_horizontal_margin, this.itemHorizantalMargin));
            this.itemVerticalMargin = dip2pixel(context, typedArray.getDimension(R.styleable.MyViewGroup_vertical_margin, this.itemVerticalMargin));
            Log.d(TAG, "itemHorizantalMargin = " + itemHorizantalMargin + " itemVerticalMargin = " + itemVerticalMargin);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        // 对所有子view进行测量，触发所有子view的onMeasure函数
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 测量宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 高度模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 测量高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 子view数目
        int childCount = getChildCount();
        if (childCount == 0) {
            // 如果当前ViewGroup没有子View，就没有存在的意义，无需占空间
            setMeasuredDimension(0, 0);
        } else {
            // wrap_content
            if (widthMode == MeasureSpec.AT_MOST) {
                // 宽度为所有子view宽度相加，高度取子view最大高度
                Log.d(TAG, "onMeasure: Your should specific the width or use match_parent");
                width = getTotalWidth();
            }
            // match_parent & special dp
            else if (widthMode == MeasureSpec.EXACTLY) {
                // 宽度为所有子View宽度相加，高度为测量高度
                width = widthSize;
            } else {
                Log.d("zhumr else", "IllegalParameters");
            }

            if (heightMode == MeasureSpec.AT_MOST) {
                // 宽度为所有子view宽度相加，高度取子view最大高度
                height = getMaxHeight();
            } else if (heightMode == MeasureSpec.EXACTLY) {
                // 宽度为所有子View宽度相加，高度为测量高度
                Log.d("zhumr else", "IllegalParameters");
            } else {
                Log.d("zhumr else", "no resolve");
            }
            Log.d(TAG, "width = " + width + " height = " + height);
            setMeasuredDimension(width, height);
        }
    }

    private int getMaxHeight() {
        // 最大高度
        int maxHeight = 0;
        int childCount = getChildCount();
        // 遍历子view拿取最大高度
        int currentWidth = marginLeft;
        int currrentLineHeight =0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            // 摆放子view,参数分别是子view矩形区域的左、上，右，下。
            Log.d(TAG, "currentWidth + width = " + (currentWidth + width));
            if (currentWidth + width + marginRight> limitWidth) {
                currentWidth = marginLeft;
                maxHeight = maxHeight + currrentLineHeight + itemVerticalMargin;
                currrentLineHeight = 0; //每次换行，下一次最大行高重新计算
                Log.d(TAG, "maxHeight = " + maxHeight + " currentLineHeight = " + currrentLineHeight);
            }
            if (currrentLineHeight < height) {
                currrentLineHeight = height;
            }
            currentWidth += width;
        }
        maxHeight = maxHeight + currrentLineHeight;
        Log.d(TAG, "final maxHeight = " + maxHeight + " currentLineHeight = " + currrentLineHeight);

        return maxHeight;
    }

    private int getTotalWidth() {
        // 所有子view宽度之和
        int totalWidth = 0;
        // 子View数目
        int childCount = getChildCount();
        // 遍历所有子view拿取所有子view宽度之和
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            totalWidth += childView.getMeasuredWidth();
        }
        return totalWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        int childCount = getChildCount();
        // 记录当前宽度位置
        Log.d(TAG, "l = " + l + " t = " + t + " r = " + r + " b = " + b);
        t = 0;
        int currentWidth = marginLeft;
        // 逐个摆放子view
        int currrentLineHeight =0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            // 摆放子view,参数分别是子view矩形区域的左、上，右，下。
            Log.d(TAG, "currentWidth + width = " + (currentWidth + width));
            if (currentWidth + width + marginRight> limitWidth) {
                t = t +currrentLineHeight + itemVerticalMargin;
                currrentLineHeight = height; //初始化下一行的的第一个高度
                currentWidth = marginLeft;
            }

            if (currrentLineHeight < height) {
                currrentLineHeight = height;
            }
            Log.d(TAG, "currentWidth = " + currentWidth + " t =" + t + " x2 = " + (currentWidth + width) + " y2 = " + (t + height));
            childView.layout(currentWidth, t, currentWidth + width, t + height);
            currentWidth += width + itemHorizantalMargin;
        }
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    public static int dip2pixel(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }

}
