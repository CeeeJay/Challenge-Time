package com.ceejay.challengetime.editor;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.Layer;
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

public class EditorMapManager {

    public GoogleMap googleMap;
    public EditorMapManager mapManager;

    public Layer editorLayer;
    public Layer cacheLayer;

    public Marker marker;
    public Marker marker2;

    private ArrayList<OnMarkerFocusChangeListener> onMarkerFocusChangeListeners = new ArrayList<>();

    public EditorMapManager(Context context , GoogleMap gMap) {
        googleMap = gMap;

        cacheLayer = new Layer( googleMap );
        marker = cacheLayer.addMarker(new LatLng(0,0));
        marker2 = cacheLayer.addMarker(new LatLng(0,0));

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

    public EditorMapManager zoom( Marker marker ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        return this;
    }
    public EditorMapManager zoom( LatLng latLng ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        return this;
    }

    public EditorMapManager lock(){
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        return this;
    }
    public EditorMapManager unLock(){
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        return this;
    }

    public void setCircle( LatLng latLng ){
        marker.setPosition(latLng);
    }
    public void setCircle2( LatLng latLng ){
        marker2.setPosition(latLng);
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




