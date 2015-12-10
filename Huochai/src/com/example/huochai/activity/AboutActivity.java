package com.example.huochai.activity;


import com.example.huochai.R;
import com.example.huochai.view.WaveDrawable;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class AboutActivity extends Activity  {

    private WaveDrawable waveDrawable;
    Interpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView imageView = (ImageView) findViewById(R.id.image);


        waveDrawable = new WaveDrawable(Color.parseColor("#8e44ad"), 500);
        imageView.setBackgroundDrawable(waveDrawable);
        interpolator = new AccelerateInterpolator();
        waveDrawable.setWaveInterpolator(interpolator);
        waveDrawable.startAnimation();


    }


    
}
