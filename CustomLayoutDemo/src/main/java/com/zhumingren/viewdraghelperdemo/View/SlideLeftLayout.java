package com.zhumingren.viewdraghelperdemo.View;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhumingren.viewdraghelperdemo.R;

/**
 * Created by ZhuMingren on 2017/9/14.
 */

public class SlideLeftLayout extends FrameLayout implements GestureDetector.OnGestureListener {

    private static final String TAG = SlideLeftLayout.class.getSimpleName();

    private GestureDetector gestureDetector;
    private Context mContext;
    private LinearLayout linearLayout;
    private TextView textView;
    private TextView tvTop;
    private TextView tvMark;
    private TextView tvDelete;

    private float newX;
    private float extraWidth;

    public SlideLeftLayout(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public SlideLeftLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SlideLeftLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        if (linearLayout == null) {
            Log.e(TAG, "initView linearLayout == null");
        }
    }

    private void initListener() {
        textView = (TextView) linearLayout.findViewById(R.id.text_view);
        tvTop = (TextView) linearLayout.findViewById(R.id.top);
        tvMark = (TextView) linearLayout.findViewById(R.id.mark);
        tvDelete = (TextView) linearLayout.findViewById(R.id.delete);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        Log.d(TAG, "width = " + layoutParams.width);
        Log.d(TAG, "density = " + mContext.getResources().getDisplayMetrics().density);
        textView.setLayoutParams(layoutParams);

        setClickPrompt(tvTop, "置顶");
        setClickPrompt(tvMark, "标记");
        setClickPrompt(tvDelete, "删除");

        extraWidth = tvTop.getLayoutParams().width + tvMark.getLayoutParams().width + tvDelete.getLayoutParams().width;
    }

    private void setClickPrompt(TextView tv, final String prompt) {
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, prompt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        linearLayout = (LinearLayout) getChildAt(0);
        initListener();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector == null) {
            gestureDetector = new GestureDetector(getContext(), this);
            gestureDetector.setIsLongpressEnabled(false);
        }
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouch");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "onTouch ACTION_UP");
            if (newX <= (-extraWidth / 2)) {
                linearLayout.setX(-extraWidth);
            } else {
                linearLayout.setX(0);
            }
        }
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        // 如果左侧滑动distanceX为正数, 起始点减去终点
        int currentX = (int) linearLayout.getX();
        newX = currentX - distanceX;
        Log.e(TAG, "newX = " + newX);
        if (newX < -extraWidth) {
            newX = -extraWidth;
        }
        if (newX > 0) {
            newX = 0;
        }
        linearLayout.setX(newX);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

}
