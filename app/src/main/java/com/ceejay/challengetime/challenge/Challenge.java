package com.ceejay.challengetime.challenge;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.widget.TextView;
import android.widget.Toast;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.StopWatch;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class Challenge {

    protected Location location;
    protected LatLng latLng;
    protected Marker marker;
    protected int sizeStartArea = 40;
    protected int sizeStopArea = 40;

    public static Challenge focusedChallenge;
    public static boolean isActivated;
    public static boolean isStarted;

    StopWatch stopWatch;

    public Challenge( LatLng latLng ) {
        this.latLng = latLng;
        location = new Location("Location");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        init();
    }

    private void init(){
        this.marker = Transferor.mapManager.addMarker(this);
    }

    public void focus(){
        focusedChallenge = this;
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public Location getLocation() {
        return location;
    }

    public void start(){

    }

    public void stop(){


    }

}




