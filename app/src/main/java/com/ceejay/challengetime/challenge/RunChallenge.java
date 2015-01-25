package com.ceejay.challengetime.challenge;

import android.graphics.Color;
import android.location.Location;

import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.StopWatch;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 */
public class RunChallenge extends Challenge {

    Location startLocation;
    Location stopLocation;

    PolylineOptions track;
    StopWatch stopWatch;


    public RunChallenge( Location location , PolylineOptions track ) {
        super(location);
        this.track = track;
    }

    @Override
    public void call() {
        marker.remove();
        Transferor.mapManager.addArea(startLocation, 50, Color.argb(70, 0, 255, 0));
        Transferor.mapManager.addArea(stopLocation, 50, Color.argb(70, 255, 0, 0));
        Transferor.mapManager.addPolyline(track);
    }

    public void start(){
        stopWatch = new StopWatch();
    }

    public void stop(){


    }

}




