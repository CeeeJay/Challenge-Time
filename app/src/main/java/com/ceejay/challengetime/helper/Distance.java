package com.ceejay.challengetime.helper;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 28.01.2015 for Challenge Time.
 */
public class Distance {

    public static double between( LatLng latLng1 , LatLng latLng2 ){
        return toLocation(latLng1,"1").distanceTo(toLocation(latLng2,"2"));
    }

    public static double between( Location location1 , Location location2 ){
        return location1.distanceTo(location2);
    }

    public static double between( LatLng latLng , Location location ){
        return toLocation(latLng,"1").distanceTo(location);
    }

    public static double between( Location location , LatLng latLng ){
        return between(latLng,location);
    }

    public static Location toLocation( LatLng latLng , String string ){
        Location location = new Location( string );
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }

}




