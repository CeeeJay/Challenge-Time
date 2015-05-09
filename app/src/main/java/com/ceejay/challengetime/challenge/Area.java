package com.ceejay.challengetime.challenge;

import android.animation.IntEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.geo.MapManager;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Area {
    public final static String TAG = Area.class.getSimpleName();

    public Challenge context;
    public String title;
    public String description;
    public int radius;
    public boolean focus = false;
    public LatLng position;
    public int fillColor = Color.TRANSPARENT;
    public int strokeColor = Color.BLACK;
    public int strokeWidth = 0;
    public boolean visible = true;
    public Circle circle, animateCircle;

    private ValueAnimator animator;

    public Area() {}

    public void show(){
        CircleOptions options = new CircleOptions().center( position ).radius( radius ).visible( visible ).strokeWidth(strokeWidth).strokeColor( strokeColor ).fillColor( fillColor );
        circle = MapManager.addArea( options );
        if(focus) {
            startAnimation();
        }
    }

    private void startAnimation(){
        stopAnimation();

        animateCircle = MapManager.addArea( position , radius , Color.argb(0, 0, 0, 0));
        animateCircle.setStrokeColor(fillColor);
        animateCircle.setStrokeWidth(5);

        animator = new ValueAnimator();
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setIntValues(0, 100);
        animator.setDuration(2000);
        animator.setEvaluator(new IntEvaluator());
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                //return (float)( 1 - Math.cos(input % 1 * Math.PI));
                return (float) Math.sin(input / 2 * Math.PI);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                animateCircle.setRadius((int) (radius + animatedFraction * 20));
                animateCircle.setStrokeWidth(5 + animatedFraction * 2);

                int strokeColor = animateCircle.getStrokeColor();
                animateCircle.setStrokeColor(Color.argb((int) (255 - animatedFraction * 255), Color.red(strokeColor), Color.green(strokeColor), Color.blue(strokeColor)));
            }
        });
        animator.start();
    }

    public void stopAnimation(){
        if( animator != null ){
            animator.cancel();
            animator = null;
            animateCircle.remove();
        }
    }

    public void changePosition( LatLng position ){
        if( circle != null ){
            circle.setCenter(position);
        }
    }

    public void changeRadius( double radius ){
        if( circle != null ){
            circle.setRadius(radius);
        }
    }

    public void changeStrokeColor( String color ){
        if( circle != null ){
            circle.setStrokeColor(Color.parseColor(color));
        }
    }

    public void changeFillColor( String color ){
        if( circle != null ){
            circle.setFillColor(Color.parseColor(color));
            animateCircle.setStrokeColor(Color.parseColor(color));
        }
    }

    public void changeStrokeWidth( int width ){
        if( circle != null ){
            circle.setStrokeWidth(width);
        }
    }

    public void changeVisible( boolean visible ){
        if( circle != null ){
            circle.setVisible(visible);
        }
    }

    public void changeFocus(boolean state){
        if( state && animator == null ) {
            startAnimation();
        }else if(!state){
            stopAnimation();
        }
    }

    public void close(){
        stopAnimation();
        circle = null;
        animateCircle = null;
    }

    public View getListView( LayoutInflater inflater , ViewGroup container){
        View view = inflater.inflate(R.layout.list_item, container, false);
        ((TextView)view.findViewById(R.id.type)).setText("A");
        if(title != null){
            ((TextView)view.findViewById(R.id.name)).setText(title);
        }
        ((TextView)view.findViewById(R.id.worth)).setText(position.toString()+"");
        return view;
    }

}




