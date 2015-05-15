package com.ceejay.challengetime.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by CJay on 15.05.2015 for Challenge Time.
 */
public class SquareLayout extends LinearLayout {

    public final static String TAG = SquareLayout.class.getSimpleName();


    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //int value = widthMeasureSpec < heightMeasureSpec ? widthMeasureSpec : heightMeasureSpec;
        Log.i(TAG,widthMeasureSpec+";"+heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}




