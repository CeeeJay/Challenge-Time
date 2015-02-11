package com.ceejay.challengetime.builder;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ceejay.challengetime.R;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */

public class BuilderMapManager {

    public GoogleMap googleMap;

    private TextView challengeName;
    private TextView challengeType;
    private TextView challengeRecord;

    private ArrayList<OnMarkerFocusChangeListener> onMarkerFocusChangeListeners = new ArrayList<>();

    public BuilderMapManager(Context context, GoogleMap gMap) {

        challengeName = (TextView) ((Activity)context).findViewById(R.id.challengeName);
        challengeType = (TextView) ((Activity)context).findViewById(R.id.challengeType);
        challengeRecord = (TextView) ((Activity)context).findViewById(R.id.challengeRecord);

        googleMap = gMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                for (OnMarkerFocusChangeListener onMarkerFocusChangeListener : onMarkerFocusChangeListeners) {
                    onMarkerFocusChangeListener.onMarkerFocusChange(null);
                }
            }
        });

    }

    public BuilderMapManager zoom( Marker marker ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        return this;
    }
    public BuilderMapManager zoom( LatLng latLng ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        return this;
    }

    public BuilderMapManager lock(){
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        return this;
    }
    public BuilderMapManager unLock(){
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        return this;
    }

    public void clear(){
        googleMap.clear();
    }
    public Circle addArea( LatLng position , int radius , int color ){
        return googleMap.addCircle(new CircleOptions().center(position).radius(radius).fillColor(color).strokeWidth(0));
    }
    public Circle addArea( Location position , int radius , int color ){
        return addArea(new LatLng(position.getLatitude(), position.getLongitude()), radius, color);
    }
    public Marker addMarker( Challenge challenge ){
        MarkerOptions markerOptions = new MarkerOptions().position(challenge.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        return googleMap.addMarker(markerOptions);
    }

    public Polyline addPolyline( List<LatLng> track ){
        return googleMap.addPolyline(new PolylineOptions().addAll(track));
    }
    public Polyline addPolyline( PolylineOptions polylineOptions ){
        return googleMap.addPolyline(polylineOptions);
    }

    public interface OnMarkerFocusChangeListener{
        public void onMarkerFocusChange(Marker marker);
    }
    public void addOnMarkerFocusChangeListener(@NonNull OnMarkerFocusChangeListener onMarkerFocusChangeListener ){
        onMarkerFocusChangeListeners.add(onMarkerFocusChangeListener);
    }
    public void removeOnMarkerFocusChangeListener(@NonNull OnMarkerFocusChangeListener onMarkerFocusChangeListener ){
        if( onMarkerFocusChangeListeners.contains(onMarkerFocusChangeListener) ) {
            onMarkerFocusChangeListeners.remove(onMarkerFocusChangeListener);
        }
    }
    public void removeAllOnMarkerFocusChangeListener( ) {
        onMarkerFocusChangeListeners.clear();
    }
}




