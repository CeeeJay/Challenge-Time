package com.ceejay.challengetime.main;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.Layer;
import com.ceejay.challengetime.helper.Transferor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */

public class MapManager {
    public final static String TAG = MapManager.class.getSimpleName();

    public GoogleMap googleMap;
    public HashMap<Marker,Challenge> markerAdapter;

    private TextView challengeName;
    private TextView challengeType;
    private TextView challengeRecord;

    public Layer markerLayer;
    public Layer challangeLayer;

    private ArrayList<OnMarkerFocusChangeListener> onMarkerFocusChangeListeners = new ArrayList<>();

    public MapManager( Context context , GoogleMap gMap  ) {

        markerLayer = new Layer( gMap );
        challangeLayer = new Layer( gMap );

        markerAdapter = new HashMap<>();

        challengeName = (TextView) ((Activity)context).findViewById(R.id.challengeName);
        challengeType = (TextView) ((Activity)context).findViewById(R.id.challengeType);
        challengeRecord = (TextView) ((Activity)context).findViewById(R.id.challengeRecord);

        googleMap = gMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                zoom(marker);
                Challenge.setFocus(markerAdapter.get(marker));
                marker.showInfoWindow();
                for (OnMarkerFocusChangeListener onMarkerFocusChangeListener : onMarkerFocusChangeListeners) {
                    onMarkerFocusChangeListener.onMarkerFocusChange(marker);
                }
                return true;
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                for (OnMarkerFocusChangeListener onMarkerFocusChangeListener : onMarkerFocusChangeListeners) {
                    onMarkerFocusChangeListener.onMarkerFocusChange(null);
                }
            }
        });
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Challenge.setUserLocation(location);
            }
        });
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = ((Activity) Transferor.context).getLayoutInflater().inflate(R.layout.info_window,null);
                TextView name = (TextView)view.findViewById(R.id.challengeName);
                name.setText("Test");
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });
    }

    public MapManager zoom( Marker marker ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        return this;
    }
    public MapManager zoom( LatLng latLng ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        return this;
    }

    public MapManager lock(){
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        return this;
    }
    public MapManager unLock(){
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        return this;
    }

    public void clear(){
        markerLayer.clear();
        challangeLayer.clear();
        googleMap.clear();
    }
    public Circle addArea( LatLng position , int radius , int color ){
        return challangeLayer.addCircle(position, radius, color);
    }

    public Marker addMarker( Challenge challenge ){
        Marker marker = markerLayer.addMarker(challenge.getLatLng());
        markerAdapter.put( marker , challenge );
        return marker;
    }

    public void hideMarker(){
        markerLayer.hideMarkers();
    }

    public void showMarker(){
        markerLayer.show();
    }

    public void clearMarker(){
        markerLayer.clear();
    }

    public void clearChallengeLayer(){
        challangeLayer.clear();
    }

    public Polyline addPolyline( PolylineOptions polylineOptions ){
        return challangeLayer.addPolyline( polylineOptions );
    }

    public interface OnMarkerFocusChangeListener{
        public void onMarkerFocusChange( Marker marker );
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




