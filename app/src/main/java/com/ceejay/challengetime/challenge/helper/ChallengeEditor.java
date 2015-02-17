package com.ceejay.challengetime.challenge.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ceejay.challengetime.challenge.RunChallenge;
import com.ceejay.challengetime.editor.EditorButton;
import com.ceejay.challengetime.helper.Layer;
import com.ceejay.challengetime.helper.slider.OptionButtonMode;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by CJay on 15.02.2015 for Challenge Time.
 */
public class ChallengeEditor {
    public final static String TAG = ChallengeEditor.class.getSimpleName();

    protected ArrayList<EditorButton> editorButtons = new ArrayList<>();
    protected GoogleMap googleMap;
    protected Layer editorLayer;
    protected Context context;

    public ChallengeEditor(final Context context, GoogleMap gMap) {
        this.context = context;
        googleMap = gMap;
        editorLayer = new Layer( googleMap );

        final EditorButton editorButton = setUpOptionButton(OptionButtonMode.CHECK);
        editorButton.setType(EditorButton.Type.NONE);
        editorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG,editorButtons.get(0).getMarkers().size()+"");
                ArrayList<LatLng> latLngs = new ArrayList<>();
                latLngs.add(editorButtons.get(1).getMarkers().get(0).getPosition());
                latLngs.add(editorButtons.get(2).getMarkers().get(0).getPosition());


                ChallengeAdapter.challenges.add(new RunChallenge(latLngs,"TestApp"));
                ((Activity)context).finish();
            }
        });
    }

    public EditorButton setUpOptionButton( OptionButtonMode mode ){
        EditorButton editorButton = new EditorButton( context );
        editorButton.changeType(mode);
        editorButtons.add(editorButton);
        if( editorButton.getType() != EditorButton.Type.NONE ) {
            editorButton.setOnTouchListener(getOnTouchListener(editorButton));
        }
        return editorButton;
    }

    public View.OnTouchListener getOnTouchListener( final EditorButton editorButton ){
        return new View.OnTouchListener() {
            Marker currentMarker;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_MOVE ){
                    if(currentMarker == null){
                        currentMarker = editorLayer.addMarker(getLatLng((int)event.getRawX(),(int)event.getRawY()+100));
                        editorButton.addMarker(currentMarker);
                    }
                    currentMarker.setPosition(getLatLng((int)event.getRawX(),(int)event.getRawY()+100));
                }
                if( event.getAction() == MotionEvent.ACTION_UP ){
                    if( currentMarker != null ){
                        currentMarker.setPosition(getLatLng((int) event.getRawX(), (int) event.getRawY() + 100));
                        if (editorButton.getType() == EditorButton.Type.MULTIPLE) {
                            currentMarker = null;
                        }
                    }
                }
                return false;
            }
        };
    }

    public LatLng getLatLng( int x , int y ){
        Projection projection = googleMap.getProjection();
        return projection.fromScreenLocation(new Point(x,y));
    }

    public ArrayList<EditorButton> getEditorButtons() {
        return new ArrayList<>(editorButtons);
    }

    public Layer getEditorLayer() {
        return editorLayer;
    }

    public void setEditorLayer(Layer editorLayer) {
        this.editorLayer = editorLayer;
    }

}




