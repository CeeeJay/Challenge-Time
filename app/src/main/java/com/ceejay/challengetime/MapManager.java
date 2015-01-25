package com.ceejay.challengetime;

import android.location.Location;

import com.ceejay.challengetime.challenge.Challenge;
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

    private GoogleMap googleMap;
    private HashMap<Marker,Challenge> list;

    public MapManager( GoogleMap googleMap  ) {
        list = new HashMap<>();
        this.googleMap = googleMap;
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                list.get(marker).call();
                return false;
            }
        });
    }

    public void clear(){
        googleMap.clear();
    }

    public void addArea( LatLng position , int radius , int color ){
        Circle circle = googleMap.addCircle(new CircleOptions().center(position).radius(radius).fillColor(color).strokeWidth(0));
    }
    public void addArea( Location position , int radius , int color ){
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(position.getLatitude(),position.getLongitude()))
                .radius(radius)
                .fillColor(color)
                .strokeWidth(0);
        Circle circle = googleMap.addCircle(circleOptions);
    }

    public void addMarker( Challenge challenge ){
        Marker marker = googleMap.addMarker(new MarkerOptions().position(challenge.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.challenge)));
        challenge.setMarker(marker);
        list.put(marker,challenge);
    }

    public void addPolyline( List<LatLng> track ){
        Polyline polyline = googleMap.addPolyline(new PolylineOptions().addAll(track));
    }

    public void addPolyline( PolylineOptions polylineOptions ){
        Polyline polyline = googleMap.addPolyline(polylineOptions);
    }


}




