package com.zhumingren.waveview;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;

public class MainActivity extends AppCompatActivity {
    
    private WaveView mWaveView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaveView = (WaveView) findViewById(R.id.wave_view);
        mWaveView.setDuration(5000);
        mWaveView.setStyle(Paint.Style.FILL);
        mWaveView.setSpeed(400);
        mWaveView.setColor(Color.parseColor("#ff0000"));
        mWaveView.setInterpolator(new AccelerateInterpolator(1.2f));
        mWaveView.start();
    }
}
