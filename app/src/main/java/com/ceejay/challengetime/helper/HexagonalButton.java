package com.ceejay.challengetime.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.ceejay.challengetime.R;


/**
 * Created by CJay on 30.01.2015 for Challenge Time.
 *
 */
public class HexagonalButton extends Button {
    public final static String TAG = HexagonalButton.class.getSimpleName();

    protected int buttonColor = Color.parseColor("#DFDFDF");

    public HexagonalButton(Context context) {
        super(context);
    }

    public HexagonalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonalButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            buttonColor = Color.parseColor("#7700dd00");
            invalidate();
            return super.onTouchEvent(event);
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN ){
            float x = event.getX();
            float y = event.getY();

            float xCanvas = getWidth()/2;
            float yCanvas = getHeight()/2;

            double angel = Math.atan((yCanvas - y)/(xCanvas-x));

            double faktor = Math.sqrt(3)/2;
            double length = Math.cos(6*angel)*(xCanvas-xCanvas*faktor)/2 + (xCanvas + xCanvas*faktor)/2;

            if( length > Math.sqrt(Math.pow((xCanvas-x),2)+Math.pow(yCanvas-y,2) )) {
                buttonColor = Color.parseColor("#AA00dd00");
                invalidate();
                return super.onTouchEvent(event);
            }
        }
        return false;
    }

    @Override
    public void setBackgroundColor(int color) {
        buttonColor = color;
        super.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        Path path = Polygon.polygonPath(getWidth()/2,getHeight()/2,Math.min(getWidth(),getHeight())/2,6);

        Paint paint = new Paint();
        paint.setColor(buttonColor);
        canvas.drawPath(path, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glasses);
        float size = 0.7f;
        int width = (int)(getWidth() * size);
        int height = width * bitmap.getHeight() / bitmap.getWidth() ;
        Log.i(width + "",  height + "");
        int x = ( getWidth() - width )/2;
        int y = ( getHeight() - height ) / 2;
        int endX = x + width;
        int endY = y + height;

        canvas.drawBitmap(bitmap, null , new Rect( x , y , endX , endY ) , null );
        super.setBackgroundColor(Color.TRANSPARENT);
        super.draw(canvas);
    }
}




