package com.ceejay.challengetime;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.Transferor;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */

public class MapManager {

    public GoogleMap googleMap;
    public HashMap<Marker,Challenge> markerAdapter;
    public HashMap<MarkerOptions,Challenge> markerOptionsMap;

    public static Marker focusedMarker;

    private TextView challengeName;
    private TextView challengeType;
    private TextView challengeRecord;

    private ArrayList<OnMarkerFocusChangeListener> onMarkerFocusChangeListeners = new ArrayList<>();

    public MapManager( Context context , GoogleMap gMap  ) {
        markerAdapter = new HashMap<>();
        markerOptionsMap = new HashMap<>();

        challengeName = (TextView) ((Activity)context).findViewById(R.id.challengeName);
        challengeType = (TextView) ((Activity)context).findViewById(R.id.challengeType);
        challengeRecord = (TextView) ((Activity)context).findViewById(R.id.challengeRecord);

        googleMap = gMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                zoom(marker);
                challengeName.setText(markerAdapter.get(marker).getChallengeName());
                marker.showInfoWindow();
                focusedMarker = marker;
                for(OnMarkerFocusChangeListener onMarkerFocusChangeListener : onMarkerFocusChangeListeners){
                    onMarkerFocusChangeListener.onMarkerFocusChange(marker);
                }
                return true;
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                focusedMarker = null;
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

    public MapManager lock(){
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        return this;
    }

    public MapManager unLock(){
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
        Marker marker = googleMap.addMarker(markerOptions);
        markerAdapter.put(marker, challenge);
        markerOptionsMap.put(markerOptions, challenge);
        return marker;
    }

    public void refreshMarker(){
        focusedMarker = null;
        clear();
        markerAdapter.clear();
        for(MarkerOptions marker : markerOptionsMap.keySet()) {
            markerAdapter.put(googleMap.addMarker(marker), markerOptionsMap.get(marker));
        }
    }

    public Polyline addPolyline( List<LatLng> track ){
        return googleMap.addPolyline(new PolylineOptions().addAll(track));
    }

    public Polyline addPolyline( PolylineOptions polylineOptions ){
        return googleMap.addPolyline(polylineOptions);
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




