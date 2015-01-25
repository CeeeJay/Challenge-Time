package com.ceejay.challengetime;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by CJay on 23.01.2015 for Challenge Time.
 *
 */
public class MyLocationManager {

    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;
    private GoogleMap googleMap;
    public PolylineOptions track;
    public Context context;

    public MyLocationManager(Context context, GoogleMap googleMap) {
        track = new PolylineOptions();

        this.context = context;

        this.googleMap = googleMap;

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
            locationManager.requestLocationUpdates(provider, 500, 5, mylistener);
        }


    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            track.add(latLng);
            Track.User = latLng;
            googleMap.clear();
            googleMap.addPolyline(track);
            googleMap.setContentDescription("");
            googleMap.addCircle(new CircleOptions().center(Track.Start).radius(50).fillColor(Color.argb(100, 0, 255, 0)).strokeWidth(5).strokeColor(Color.BLACK));
            googleMap.addCircle(new CircleOptions().center(Track.Stop).radius(50).fillColor(Color.argb(100, 255, 0, 0)).strokeWidth(5).strokeColor(Color.BLACK));
            googleMap.addCircle(new CircleOptions().center(Track.User).radius(5).fillColor(Color.argb(100, 0, 0, 0)).strokeWidth(5).strokeColor(Color.BLACK));
            //googleMap.addGroundOverlay(new GroundOverlayOptions().position(Track.User,1).image(BitmapDescriptorFactory.fromResource(R.drawable.icon)));
            Marker marker = googleMap.addMarker(new MarkerOptions().position(Track.User).icon(BitmapDescriptorFactory.fromResource(R.drawable.challenge)));

            Toast.makeText(context,"Position",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {


        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}




