package com.ceejay.challengetime.helper.slider;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class OptionButton extends Button{
    public final static String TAG = OptionButton.class.getSimpleName();

    private OptionButtonMode buttonMode = OptionButtonMode.WATCH;
    private Context context;
    private Resources resources;
    private int optionButtonSize;
    private int optionButtonMargin;
    private Drawable background;

    public OptionButton(Context context) {
        super(context);
        init(context);
    }

    public OptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OptionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context ){
        this.context = context;

        resources = context.getResources();
        optionButtonSize = (int)context.getResources().getDimension(R.dimen.option_button);
        optionButtonMargin = (int)context.getResources().getDimension(R.dimen.option_button_margin);
        background = resources.getDrawable(R.drawable.option_button);

        setLayoutParams(new LinearLayout.LayoutParams(optionButtonSize, optionButtonSize));
        changeType(buttonMode);
    }

    public void changeType( OptionButtonMode buttonMode ){
        this.buttonMode = buttonMode;

        if(buttonMode.getResource() >= 0) {
            Drawable drawableArray[]= new Drawable[]{
                    background,
                    resources.getDrawable(buttonMode.getResource())};

            LayerDrawable layerDraw = new LayerDrawable(drawableArray);
            layerDraw.setLayerInset(1, optionButtonMargin, optionButtonMargin, optionButtonMargin, optionButtonMargin);
            setBackground(layerDraw.mutate());
            if(buttonMode.getAnimation() != -1 ){
                startAnimation(AnimationUtils.loadAnimation(context,buttonMode.getAnimation()));
            }
        }else{
            setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        requestLayout();

    }

    public void changeBackground( Drawable drawable ){
        background = drawable;
        changeType( buttonMode );
    }

    public void setPosition( int x , int y ){
        ((ViewGroup.MarginLayoutParams) getLayoutParams()).setMargins(x,y,0,0);
        requestLayout();
    }

}




