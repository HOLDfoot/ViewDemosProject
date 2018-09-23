package com.example.lenovo.viewdispatchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


public class CustomLayout extends LinearLayout {

    public static final float MOVE_DISTANCE = 30f; // dp
    private static final String TAG = CustomLayout.class.getSimpleName() + "/zmr";
    private float moveHeight;
    private float currentX;
    private float currentY;
    private Context context;

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomLayout(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"touched down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"touched move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"touched up");
                break;
            default:
                Log.d(TAG,"other touched");
                break;
        }

        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"dispatch touched down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"dispatch touched move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"dispatch touched up");
                break;
            default:
                Log.d(TAG,"other dispatch touched");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                moveHeight = 0;
                currentX = ev.getX();
                currentY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                float temY = ev.getY();
                moveHeight = Math.abs(temY - currentY);
                Log.d(TAG, "ACTION_UP moveHeight = " + moveHeight);
                float moveDistancePx = this.context.getResources().getDisplayMetrics().density * MOVE_DISTANCE + 0.5f;
                Log.d(TAG, "ACTION_UP moveDistancePx = " + moveDistancePx);
                if (moveHeight > moveDistancePx) {
                    dragUp();
                    return true;
                }
                break;
            default:
                Log.d(TAG,"other touched");
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    private void dragUp() {
        Log.d(TAG, "draUp Event");
    }
/*
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                break;

        }
        return super.onInterceptHoverEvent(event);
    }*/
}
