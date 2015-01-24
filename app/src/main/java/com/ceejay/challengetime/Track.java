package com.ceejay.challengetime;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 24.01.2015 for Challenge Time.
 *
 */
public class Track {
    public static LatLng Start = new LatLng(49.28720,7.11924);
    public static LatLng Stop = new LatLng(49.28895,7.11910);



    public static LatLng User = new LatLng(0,0);

    public static Location getStartLocation(){
        Location startLocation = new Location("Start");

        startLocation.setLatitude(Start.latitude);
        startLocation.setLongitude(Start.longitude);
        return startLocation;
    }
    public static Location getStopLocation(){
        Location stopLocation = new Location("Stop");

        stopLocation.setLatitude(Stop.latitude);
        stopLocation.setLongitude(Stop.longitude);
        return stopLocation;
    }

    public static Location getUserLocation(){
        Location userLocation = new Location("User");

        userLocation.setLatitude(User.latitude);
        userLocation.setLongitude(User.longitude);
        return userLocation;
    }

}




