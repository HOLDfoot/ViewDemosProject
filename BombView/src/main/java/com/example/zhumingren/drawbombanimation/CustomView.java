package com.example.zhumingren.drawbombanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by ZhuMingren on 2017/12/20.
 */

public class CustomView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private Context context;
    private SurfaceHolder surfaceHolder;
    private CustomThread customThread;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setKeepScreenOn(true);
        this.setFocusable(true);
        //用于触摸事件
        this.setLongClickable(true);
        this.context = context;
        //获取对象实例
        surfaceHolder = this.getHolder();
        // 给SurfaceView添加回调
        surfaceHolder.addCallback(this);
        //创建工作线程
        customThread = new CustomThread(surfaceHolder);

    }

    //创建的时候调用
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        customThread.canRun = true;
        //工作线程开始工作
        customThread.start();
        Log.d("-->surfaceCreated", "surfaceCreated");
    }

    //发生改变的时候调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        Log.d("-->surfaceChanged", "surfaceChanged");
    }

    //销毁时的时候调用
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        customThread.canRun = false;
        Log.d("-->surfaceDestroyed", "surfaceDestroyed");

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:


                Log.d("MotionEvent", "ACTION_DOWN");

                break;

            case MotionEvent.ACTION_UP:


                Log.d("MotionEvent", "ACTION_UP");

                break;


            case MotionEvent.ACTION_MOVE:


                Log.d("MotionEvent", "ACTION_MOVE");

                break;


        }

        return super.onTouchEvent(event);

    }
}

//工作线程
class CustomThread extends Thread {
    private SurfaceHolder surfaceHolder;
    public boolean canRun;

    public CustomThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        canRun = true;
    }

    @Override
    public void run() {

        while (canRun) {
            Canvas canvas = null;
            //线程同步
            synchronized (surfaceHolder) {
                //创建画笔
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setTextSize(200);
                // 锁定画布(想一下,类似Bitmap的效果)
                canvas = surfaceHolder.lockCanvas();
                Log.d("zhumr", "run canRun = " + canRun);
                if (!canRun || canvas == null) {
                    break;
                }
                //画背景
                canvas.drawColor(Color.WHITE);
                //画字
                canvas.drawText("妈蛋", 100, 300, paint);
            }

            if (canvas != null) {
                // 结束锁定
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

        }

    }
}