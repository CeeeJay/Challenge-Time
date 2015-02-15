package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.ceejay.challengetime.helper.Layer;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.OptionButtonMode;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 15.02.2015 for Challenge Time.
 */
public class ChallengeEditor {
    public final static String TAG = ChallengeEditor.class.getSimpleName();

    protected HashMap<OptionButton,Marker> optionButtons = new HashMap<>();
    protected GoogleMap googleMap;
    protected Layer editorLayer;
    protected Context context;
    protected Marker marker;

    public ChallengeEditor(Context context, GoogleMap gMap) {
        this.context = context;
        googleMap = gMap;
        editorLayer = new Layer( googleMap );
    }

    public void setUpOptionButton( OptionButtonMode mode ){
        OptionButton startOptionButton = new OptionButton( context );
        startOptionButton.changeType(mode);
        marker = editorLayer.addMarker(new LatLng(0,0));
        marker.setVisible(false);
        optionButtons.put(startOptionButton, marker);
        startOptionButton.setOnTouchListener(getOnTouchListener(marker));
    }

    public View.OnTouchListener getOnTouchListener( final Marker marker ){
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    marker.setVisible(true);
                }
                if( event.getAction() == MotionEvent.ACTION_MOVE ){
                    marker.setPosition(getLatLng((int)event.getRawX(),(int)event.getRawY()+100));
                }
                if( event.getAction() == MotionEvent.ACTION_UP ){
                    marker.setPosition(getLatLng((int)event.getRawX(),(int)event.getRawY()+100));
                }
                return false;
            }
        };
    }

    public LatLng getLatLng( int x , int y ){
        Projection projection = googleMap.getProjection();
        return projection.fromScreenLocation(new Point(x,y));
    }

    public ArrayList<OptionButton> getOptionButtons() {
        return new ArrayList<>(optionButtons.keySet());
    }

    public Layer getEditorLayer() {
        return editorLayer;
    }

    public void setEditorLayer(Layer editorLayer) {
        this.editorLayer = editorLayer;
    }

}




