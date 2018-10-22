package com.example.zhumingren.drawbombanimation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.util.Random;

/**
 * Created by ZhuMingren on 2017/12/19.
 */

public class BombConfig {
    
    public static final String TAG = "BombView";
    public static final boolean DEBUG = false;
    
    public static Random random = new Random();
    public static float velocity_horizontal; // mDuration毫秒内从中间滑动到右边界的速度
    public static float velocity = velocity_horizontal; // 需要调试出系数
    public static int ref_minus = 1;
    public static final int velocity_factor = 3000;
    
    public int color;
    public float rotate;
    public float scale;
    public int minus = 1;
    
    public double Vx;
    public double Vy;
    
    public static BombConfig newBombConfig(Context mContext, int total, int i) {
        if (velocity == 0) {
            velocity_horizontal = mContext.getResources().getDisplayMetrics().widthPixels / 2f / velocity_factor;
            logd(TAG, "newBombConfig velocity_horizontal = " + velocity_horizontal);
            velocity = velocity_horizontal;
        }
        
        int alpha = (int) ((float) (Math.random() * 0.5f + 0.5f) * 255);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        
        int color = Color.argb(alpha, red, green, blue);
        float rotate = (float) (60f * Math.random()); // 如果minus为正rotate顺时针旋转
        float scale = (float) (Math.random() * 1.0f + 0.4f);
        ref_minus = ref_minus * (-1); // 每次取反, 确保左右的碎片数目一致
        int minus = ref_minus;
        
        float du = -10f + (200f / (total - 1f)) * i;
        double hudu = du * Math.PI / 180f;
        logd(TAG, "newBombConfig hudu = " + hudu);
        float baseVy;
        if (Math.random() < 0.2d) {
            baseVy = (float) (velocity * (Math.random() * 6.0f));
        } else {
            baseVy = (float) (velocity * (Math.random() * 2.0f + 4.0f));
        }
        float randVy = (float) (Math.random() * 0.4f + 1.0f);
        float randVx = (float) (Math.random() * 2.0f);
        double Vx = velocity * Math.cos(hudu) * randVx;
        double Vy = -velocity * Math.sin(hudu) * randVy - baseVy;
        
        logd("BombView", "newBombConfig minus = " + minus + " i= " + i + " Vx = " + Vx + " Vy = " + Vy);
        BombConfig bombConfig = new BombConfig(Vx, Vy, color, rotate, scale, minus);
        return bombConfig;
    }
    
    public BombConfig(double Vx, double Vy, int color, float rotate, float scale, int minus) {
        this.Vx = Vx;
        this.Vy = Vy;
        this.color = color;
        this.rotate = rotate;
        this.scale = scale;
        this.minus = minus;
    }
    
    private void loge(String tag, String log) {
        if (DEBUG) {
            Log.e(tag, log);
        }
    }
    
    private static void logd(String tag, String log) {
        if (DEBUG) {
            Log.d(tag, log);
        }
    }
    
}