package com.ceejay.challengetime.challenge;

import android.graphics.Color;
import com.ceejay.challengetime.Transferor;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class RunChallenge extends Challenge {

    LatLng startLocation;
    LatLng stopLocation;
    PolylineOptions track;

    Circle startArea;
    Circle stopArea;
    Polyline trackPolyline;




    public RunChallenge( LatLng location , PolylineOptions track ) {
        super(location);
        this.track = track;
        startLocation = track.getPoints().get(0);
        stopLocation = track.getPoints().get(track.getPoints().size()-1);
    }

    @Override
    public void focus() {
        super.focus();
        startArea = Transferor.mapManager.addArea(startLocation, sizeStartArea, Color.argb(70, 0, 255, 0));
        stopArea = Transferor.mapManager.addArea(stopLocation, sizeStopArea, Color.argb(70, 255, 0, 0));
        trackPolyline = Transferor.mapManager.addPolyline(track);
    }



}




