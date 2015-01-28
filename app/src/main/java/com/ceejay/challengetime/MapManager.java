package com.ceejay.challengetime;

import android.content.Context;
import android.location.Location;

import com.ceejay.challengetime.challenge.Challenge;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */

public class MapManager {

    public GoogleMap googleMap;
    private HashMap<Marker,Challenge> markerAdapter;
    private HashMap<MarkerOptions,Challenge> markerOptionsMap;

    public MapManager( GoogleMap gMap  ) {
        markerAdapter = new HashMap<>();
        markerOptionsMap = new HashMap<>();
        googleMap = gMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clear();
                markerAdapter.get(marker).focus();
                markerAdapter.clear();
                return true;
            }
        });
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Challenge.setUserLocation(location);
            }
        });
    }

    public void clear(){
        googleMap.clear();
    }

    public Circle addArea( LatLng position , int radius , int color ){
        return googleMap.addCircle(new CircleOptions().center(position).radius(radius).fillColor(color).strokeWidth(0));
    }

    public Circle addArea( Location position , int radius , int color ){
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(position.getLatitude(),position.getLongitude()))
                .radius(radius)
                .fillColor(color)
                .strokeWidth(0);
        return googleMap.addCircle(circleOptions);
    }

    public Marker addMarker( Challenge challenge ){
        MarkerOptions markerOptions = new MarkerOptions().position(challenge.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        Marker marker = googleMap.addMarker(markerOptions);
        markerAdapter.put(marker, challenge);
        markerOptionsMap.put(markerOptions, challenge);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(challenge.getLatLng(), 15));
        return marker;
    }

    public void refreshMarker(){
        clear();
        markerAdapter.clear();
        for(MarkerOptions marker : markerOptionsMap.keySet()) {
            markerAdapter.put(googleMap.addMarker(marker), markerOptionsMap.get(marker));
        }
    }


    public void addPolyline( List<LatLng> track ){
        Polyline polyline = googleMap.addPolyline(new PolylineOptions().addAll(track));
    }

    public Polyline addPolyline( PolylineOptions polylineOptions ){
        return googleMap.addPolyline(polylineOptions);
    }


}




