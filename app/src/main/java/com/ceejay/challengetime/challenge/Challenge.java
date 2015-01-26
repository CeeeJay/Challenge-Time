package com.ceejay.challengetime.challenge;

import android.graphics.Color;
import android.location.Location;

import com.ceejay.challengetime.Transferor;
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

    public Challenge( LatLng latLng ) {
        this.latLng = latLng;
        location = new Location("Location");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        init();
    }

    public Challenge( Location location ) {
        this.location = location;
        this.latLng = new LatLng( location.getLatitude() , location.getLongitude() );
        init();
    }

    private void init(){
        this.marker = Transferor.mapManager.addMarker(this);
    }

    public void call(){
        Transferor.mapManager.addArea(latLng,50, Color.argb(70,0,255,0));
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public Location getLocation() {
        return location;
    }


}




