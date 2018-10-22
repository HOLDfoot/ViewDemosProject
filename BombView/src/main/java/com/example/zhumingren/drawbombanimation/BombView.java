package com.example.zhumingren.drawbombanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuMingren on 2017/12/20.
 */

public class BombView extends View {
    
    private static final String TAG = BombConfig.TAG;
    private static final boolean DEBUG = BombConfig.DEBUG;
    private static final float INTERVAL = 10f;
    private static final int alphaTime = 200;
    private static final int mDuration = 3600;
    
    private int w;
    private int h;
    private int drawW;
    private int drawH;
    private int count = 100;
    private float density;
    private float msec = -INTERVAL;
    private boolean alphaEnd;
    
    private Drawable drawable;
    private ValueAnimator mAnimator;
    private AlphaAnimation alphaAnimation;
    private List<BombConfig> configs = new ArrayList<>();
    private Context mContext;
    
    public BombView(Context context) {
        this(context, null);
    }
    
    public BombView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        w = context.getResources().getDisplayMetrics().widthPixels;
        h = context.getResources().getDisplayMetrics().heightPixels;
        density = context.getResources().getDisplayMetrics().density;
        count = context.getResources().getInteger(R.integer.pieces_count_of_bomb_view);
        logd(TAG, "count = " + count);
        
        int color = Color.argb(255, 255, 255, 0);
        Drawable drawable = context.getResources().getDrawable(R.mipmap.paper_scraps);
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        this.drawable = drawable;
        Bitmap bitmap = drawableToBitmap(drawable);
    }
    
    public Bitmap drawableToBitmap(Drawable drawable) {
        
        drawW = drawable.getIntrinsicWidth();
        drawH = drawable.getIntrinsicHeight();
        logd(TAG, "drawableToBitmap w = " + w + " h = " + h);
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        //Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawW / 2, drawH / 2);
        //drawable.draw(canvas);
        
        return bitmap;
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (msec == -INTERVAL || alphaEnd) {
            logd(TAG, "onDraw alphaEnd = " + alphaEnd);
            return;
        }
        
        for (int i = 0; i < configs.size(); i++) {
            loge(TAG, "config i = " + i);
            BombConfig bombConfig = configs.get(i);
            
            int sw = (int) (bombConfig.scale * drawW);
            int sh = (int) (bombConfig.scale * drawH);
            drawable.setBounds(0, 0, sw, sh);
            drawable.setColorFilter(bombConfig.color, PorterDuff.Mode.MULTIPLY);
            
            float Fx, Fy;
            if (msec == 0) {
                //canvas.drawBitmap(bitmap, w / 2, h / 3, null);
                Fx = w / 2;
                Fy = h / 3;
                canvas.translate(Fx, Fy);
                drawable.draw(canvas);
                canvas.translate(-Fx, -Fy);
                loge(TAG, "onDraw Fx = " + w / 2 + " Fy = " + h / 3);
                return;
            }
            
            float Vx = (float) bombConfig.Vx;
            Fx = Vx * msec + w / 2f;
            
            float Vy = (float) bombConfig.Vy;
            float a = 0.00080f * density / 3.0f; // 写死不变
            logd(TAG, " Vy = " + Vy + " a = " + a * 10000 + " msec = " + msec + " i = " + i);
            float Vay = a * msec * msec * 0.5f;
            float Vyy = Vy * msec;
            Fy = h / 3 + (Vay + Vyy);
            logd(TAG, "onDraw Fx = " + Fx + "Fy = " + Fy + " Vay = " + Vay + " Vyy = " + Vyy + " i = " + i);
            //canvas.drawBitmap(bitmap, Fx, Fy, null);
            canvas.save();
            canvas.translate(Fx, Fy);
            canvas.rotate(bombConfig.rotate * bombConfig.minus);
            drawable.draw(canvas);
            canvas.rotate(-bombConfig.rotate * bombConfig.minus);
            canvas.translate(-Fx, -Fy);
            canvas.save();
        }
    }
    
    private void loge(String tag, String log) {
        if (DEBUG) {
            Log.e(tag, log);
        }
    }
    
    private void logd(String tag, String log) {
        if (DEBUG) {
            Log.d(tag, log);
        }
    }
    
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float start = Float.parseFloat(animation.getAnimatedValue().toString());
            //logd(TAG, "onAnimationUpdate start = " + start);
            msec = start;
            postInvalidate();
            
            if (msec >= (mDuration - alphaTime)) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        startAlphaAnimate();
                    }
                });
            }
            
            if (msec >= mDuration) {
                loge(TAG, "onAnimationUpdate msec = " + msec);
                post(new Runnable() {
                    @Override
                    public void run() {
                        setVisibility(GONE);
                    }
                });
            }
        }
    };
    
    public void startPlay() {
        setVisibility(VISIBLE);
        setAlpha(1.0f);
        msec = -INTERVAL;
        alphaAnimation = null;
        alphaEnd = false;
        
        configs.clear();
        int total = count;
        for (int i = 0; i < total; i++) {
            BombConfig bombConfig = BombConfig.newBombConfig(mContext, total, i);
            configs.add(bombConfig);
        }
        
        mAnimator = ValueAnimator.ofFloat(0, mDuration);
        mAnimator.setDuration(mDuration);
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
        mAnimator.setRepeatCount(0);
        mAnimator.setInterpolator(new DecelerateAccelerateInterpolator());
        mAnimator.start();
    }
    
    public void stopPlay() {
        if (mAnimator == null || !mAnimator.isRunning()) return;
        mAnimator.cancel();
        mAnimator = null;
    }
    
    private void startAlphaAnimate() {
        if (alphaAnimation != null) return;
        logd(TAG, "startAlphaAnimate");
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(alphaTime);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                loge(TAG, "startAlphaAnimate onAnimationEnd");
                alphaEnd = true;
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
            
            }
        });
        this.startAnimation(alphaAnimation);
    }
    
    private class DecelerateAccelerateInterpolator implements Interpolator {
        
        public DecelerateAccelerateInterpolator() {
        }
        
        public float getInterpolation(float t) {
            float x = 2.0f * t - 1.0f;
            float y = 0.5f * (x * x * x + 1.0f); // 直接返回y会导致一阶微分方程在t=0.5处为0, 界面停滞
            float output = (y + 2.0f*t) / 3.0f;
            return output;
        }
        
    }
    
}