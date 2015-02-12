package com.ceejay.challengetime.helper.slider;

import android.support.annotation.AnimRes;
import android.support.annotation.DimenRes;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 12.02.2015 for Challenge Time.
 */
public enum OptionButtonMode{
    INVISIBLE ( -1 , -1 ),
    REFRESH (R.drawable.refresh , R.anim.refresh_rotate ),
    WATCH (R.drawable.eye , -1 ),
    LOCATION (R.drawable.location , R.anim.location_rotate ),
    ACTIVATE (R.drawable.logo , R.anim.zoom_blink ),
    STOP (R.drawable.kreuz , -1 ),

    STARTLOCATION (R.drawable.stop_location , -1 ),
    STOPLOCATION (R.drawable.stop_location , -1 );


    private int resource;
    private int animation;
    OptionButtonMode( @DimenRes int resource , @AnimRes int animation ) {
        this.resource = resource;
        this.animation = animation;
    }

    public int getResource(){
        return resource;
    }
    public int getAnimation(){ return animation; }
}




