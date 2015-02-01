package com.ceejay.challengetime;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by CJay on 01.02.2015 for Challenge Time.
 */
public class AnimationHolder {
    public final static String TAG = AnimationHolder.class.getSimpleName();

    private Animation activateButtonShow;
    private Animation activateButtonHide;
    private View button;

    private Context context;

    public AnimationHolder(Context ctx ) {
        context = ctx;

        button = ((Activity) context).findViewById(R.id.start);
        activateButtonHide = AnimationUtils.loadAnimation(context, R.anim.activate_button_hide);
        activateButtonShow = AnimationUtils.loadAnimation(context, R.anim.activate_button_show);
        activateButtonShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        activateButtonHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void hideActivateButton(boolean hider) {
        if (hider) {
            if(button.getVisibility() == View.VISIBLE) {
                button.startAnimation(activateButtonHide);
            }
        }else{
            if(button.getVisibility() == View.INVISIBLE) {
                button.startAnimation(activateButtonShow);
            }
        }
    }

}


