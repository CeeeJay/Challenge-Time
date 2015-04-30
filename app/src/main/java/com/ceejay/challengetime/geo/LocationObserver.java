package com.ceejay.challengetime.geo;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 23.01.2015 for Challenge Time.
 *
 */
public class LocationObserver {

    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;

    public static Location location = new Location("test");
    public static LatLng position = new LatLng(49.28722,7.11829);

    public LocationObserver(Context context) {
        location.setLatitude(49.28722);
        location.setLatitude(7.11929);

        // Get the location manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);	//default

        criteria.setCostAllowed(false);
        // get the best provider depending on the criteria
        provider = locationManager.getBestProvider(criteria, false);

        // the last known location of this provider
        Location location = locationManager.getLastKnownLocation(provider);

        mylistener = new MyLocationListener();
        if (location != null) {
            mylistener.onLocationChanged(location);

        }
        locationManager.requestLocationUpdates(provider, 500, 1, mylistener);

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location pos) {
            location = pos;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            location = null;
        }
    }
}




