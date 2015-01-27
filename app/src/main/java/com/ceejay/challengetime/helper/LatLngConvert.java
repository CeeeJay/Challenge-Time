package com.ceejay.challengetime.helper;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 26.01.2015 for Challenge Time.
 */
public class LatLngConvert {

    public static Location toLocation( LatLng latLng , String name ){
        Location location = new Location(name);
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }



}




