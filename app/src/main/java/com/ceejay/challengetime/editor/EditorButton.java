package com.ceejay.challengetime.editor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.ceejay.challengetime.helper.slider.OptionButton;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by CJay on 15.02.2015 for Challenge Time.
 */
public class EditorButton extends OptionButton {
    public final static String TAG = EditorButton.class.getSimpleName();

    private Type currentType = Type.SINGEL;
    private ArrayList<Marker> markers;
    private Drawable markerStyle;

    public EditorButton(Context context) {
        super(context);
        init();
    }

    public EditorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        markers = new ArrayList<>();
    }

    public static enum Type {
        NONE,SINGEL,MULTIPLE
    }

    public void setType( Type type ){
        currentType = type;
    }

    public Type getType() {
        return currentType;
    }

    public void addMarker( Marker marker ){
        if (currentType != Type.NONE){
            if(currentType == Type.SINGEL){
                markers.add(0,marker);
            }else{
                markers.add(marker);
            }
        }
    }

    public ArrayList<Marker> getMarkers(){
        return markers;
    }

}




