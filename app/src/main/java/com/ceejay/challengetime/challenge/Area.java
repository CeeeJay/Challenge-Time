package com.ceejay.challengetime.challenge;

import android.animation.IntEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ceejay.challengetime.geo.MapManager;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Area {
    public final static String TAG = Area.class.getSimpleName();

    public Challenge context;
    public String title;
    public String description;
    public String color;
    public int radius;
    public boolean focus;
    public LatLng position;

    public Circle circle, animateCircle;

    public Area() {

    }

    public void show(){
        circle = MapManager.addArea( position , radius , Color.parseColor(color) );


        if(false) {
            animateCircle = MapManager.addArea( position , radius , Color.argb(0, 0, 0, 0));
            animateCircle.setStrokeColor(Color.parseColor("#000000"));
            animateCircle.setStrokeWidth(5);

            ValueAnimator vAnimator = new ValueAnimator();
            vAnimator.setRepeatCount(ValueAnimator.INFINITE);
            vAnimator.setRepeatMode(ValueAnimator.RESTART);
            vAnimator.setIntValues(0, 100);
            vAnimator.setDuration(1000);
            vAnimator.setEvaluator(new IntEvaluator());
            vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            vAnimator.setInterpolator(new TimeInterpolator() {
                @Override
                public float getInterpolation(float input) {
                    //return (float)( 1 - Math.cos(input % 1 * Math.PI));
                    return (float)Math.sin(input/2 * Math.PI);
                }
            });
            vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    animateCircle.setRadius((int) (50 + animatedFraction * 10));
                    int strokeColor = animateCircle.getStrokeColor();
                    animateCircle.setStrokeColor(Color.argb((int) (255 - animatedFraction * 255), Color.red(strokeColor), Color.green(strokeColor), Color.blue(strokeColor)));
                }
            });
            vAnimator.start();
        }
    }

    public void changeColor( String color ){
        //this.color = color;
        if( circle != null ){
            circle.setFillColor(Color.parseColor(color));
        }
    }


}




