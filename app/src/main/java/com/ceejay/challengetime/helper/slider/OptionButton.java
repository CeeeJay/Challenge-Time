package com.ceejay.challengetime.helper.slider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class OptionButton extends Button{
    public final static String TAG = OptionButton.class.getSimpleName();

    public enum ButtonMode{
        INVISIBLE (-1),
        REFRESH (R.drawable.refresh_button),
        WATCH (R.drawable.watch_button),
        LOCATION (R.drawable.location_button),
        ACTIVATE (R.drawable.activate_button),
        STOP (R.drawable.stop_button),

        STARTLOCATION (R.drawable.start_location_button),
        STOPLOCATION (R.drawable.stop_location_button);


        private int resource;
        ButtonMode( int resource ) {
            this.resource = resource;
        }

        public int getResource(){
            return resource;
        }
    }

    private ButtonMode buttonMode = ButtonMode.WATCH;
    private Context context;
    private ViewDragHelper mDragHelper;

    public OptionButton(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.option_button), (int) context.getResources().getDimension(R.dimen.option_button)));
        this.context = context;
        changeType(buttonMode);
    }

    public OptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OptionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void changeType( ButtonMode mode ){
        buttonMode = mode;
        if(buttonMode.getResource() >= 0) {
            setBackground(context.getResources().getDrawable(buttonMode.getResource()));
        }else{
            setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        requestLayout();
    }

    public void setPosition( float x , float y ){
        setX(x);
        setY(y);
    }

    public static class DragHelper{

    }

}




