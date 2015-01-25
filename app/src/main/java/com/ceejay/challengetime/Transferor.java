package com.ceejay.challengetime;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 24.01.2015 for Challenge Time.
 *
 */
public class Transferor {
    public static LatLng User = new LatLng(0,0);


    public static Location getUserLocation(){
        Location userLocation = new Location("User");

        userLocation.setLatitude(User.latitude);
        userLocation.setLongitude(User.longitude);
        return userLocation;
    }

    public static MapManager mapManager;

}




