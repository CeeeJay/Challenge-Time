package com.ceejay.challengetime.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.Polygon;

/**
 * Created by CJay on 26.01.2015 for Challenge Time.
 */
public class HexagonButton extends View {

    private Context context;
    private String text = "";
    private int color;

    public HexagonButton(Context context) {
        super(context);
        this.context = context;
    }

    public HexagonButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a =getContext().obtainStyledAttributes(
                attrs,
                R.styleable.HexagonButton);

    }

    public HexagonButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(event.getX() < 200 && event.getY() < 200){
                Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void draw(Canvas canvas) {

        Path testPath = Polygon.polygonPath(100, 100, 100, 6);

        Paint red = new Paint();
        red.setColor(Color.argb(80,0,255,255));
        red.setStrokeWidth(5);
        canvas.drawPath(testPath, red);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Go", 100, 125, paint);
    }
}




