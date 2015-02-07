package com.ceejay.challengetime;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.Transferor;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by CJay on 23.01.2015 for Challenge Time.
 *
 */
public class LocationObserver {

    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;

    public LocationObserver(Context context) {
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
        Toast.makeText(Transferor.context,"1" , Toast.LENGTH_SHORT).show();

        if (location != null) {
            Toast.makeText(Transferor.context,"2" , Toast.LENGTH_SHORT).show();

            mylistener.onLocationChanged(location);

        }
        locationManager.requestLocationUpdates(provider, 500, 1, mylistener);

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Challenge.setUserLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            Challenge.setUserLocation(null);
        }
    }
}




