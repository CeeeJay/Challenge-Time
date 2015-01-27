package com.ceejay.challengetime.challenge;

import android.graphics.Color;
import android.location.Location;

import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.LatLngConvert;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class RunChallenge extends Challenge {

    Location startLocation;
    Location stopLocation;
    PolylineOptions track;

    public RunChallenge( LatLng location , PolylineOptions track ) {
        super(location);
        this.track = track;
        startLocation = LatLngConvert.toLocation(track.getPoints().get(0), "Start");
        stopLocation = LatLngConvert.toLocation(track.getPoints().get(track.getPoints().size()-1),"Stop");
    }

    @Override
    public void focus() {
        super.focus();
        Transferor.mapManager.addArea(startLocation, sizeStartArea, Color.argb(70, 0, 255, 0));
        Transferor.mapManager.addArea(stopLocation, sizeStopArea, Color.argb(70, 255, 0, 0));
        Transferor.mapManager.addPolyline(track);
    }

    @Override
    public void finish() {
        if( userLocation.distanceTo(stopLocation) < sizeStopArea ) {
            super.finish();
        }
    }
}




