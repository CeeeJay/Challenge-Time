package com.ceejay.challengetime.geo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.helper.Layer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
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

    public static GoogleMap googleMap;
    public static  HashMap<Marker,Challenge> markerAdapter;

    private static Context context;

    public static Layer markerLayer;
    public static Layer challengeLayer;

    private static ArrayList<OnMarkerFocusChangeListener> onMarkerFocusChangeListeners = new ArrayList<>();

    public static void setMap( Context ctx , GoogleMap gMap  ) {
        context = ctx;

        markerLayer = new Layer( gMap );
        challengeLayer = new Layer( gMap );

        markerAdapter = new HashMap<>();

        googleMap = gMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                zoom(marker);
                marker.showInfoWindow();
                for (OnMarkerFocusChangeListener onMarkerFocusChangeListener : onMarkerFocusChangeListeners) {
                    onMarkerFocusChangeListener.onMarkerFocusChange(marker);
                }
                ChallengeAdapter.focusChallenge(markerAdapter.get(marker));
                return true;
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                for (OnMarkerFocusChangeListener onMarkerFocusChangeListener : onMarkerFocusChangeListeners) {
                    onMarkerFocusChangeListener.onMarkerFocusChange(null);
                }
                if(ChallengeAdapter.focusedChallenge != null && ChallengeAdapter.focusedChallenge.status == Challenge.Status.HIDDEN) {
                    ChallengeAdapter.focusChallenge(null);
                }
            }
        });
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = ((Activity) MapManager.context).getLayoutInflater().inflate(R.layout.info_window,null);
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

    public static void zoom( Marker marker ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
    }
    public static void zoom( LatLng latLng ){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public static void lock(){
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }
    public static void unLock(){
        googleMap.getUiSettings().setAllGesturesEnabled(true);
    }

    public void clear(){
        markerLayer.clear();
        challengeLayer.clear();
        googleMap.clear();
    }

    public static Circle addArea( @NonNull LatLng position , int radius , int color ){
        if(challengeLayer!= null) {
            return challengeLayer.addCircle(position, radius, color);
        }
        return null;
    }

    public static Polygon addPolygon( @NonNull  ArrayList<LatLng> points , int color ){
        if(challengeLayer!= null) {
            return challengeLayer.addPolygon(points, color);
        }
        return null;
    }

    public static Polygon addPolygon( PolygonOptions options ){
        if(challengeLayer!= null) {
            return challengeLayer.addPolygon(options);
        }
        return null;
    }

    public static Circle addArea( @NonNull CircleOptions options){
        if(challengeLayer!= null) {
            return challengeLayer.addCircle(options);
        }
        return null;
    }

    public static Marker addMarker( Challenge challenge ){
        if( challenge != null && challenge.position != null && markerLayer != null ) {
            Marker marker = markerLayer.addMarker(challenge.position);
            markerAdapter.put(marker, challenge);
            return marker;
        }
        return null;
    }

    public static void showMarkerLayer(){
        if( markerLayer != null && challengeLayer != null) {
            challengeLayer.clear();
            markerLayer.show();
        }
    }

    public static void showChallengeLayer(){
        if( markerLayer != null && ChallengeAdapter.focusedChallenge != null) {
            markerLayer.hideMarkers();
            ChallengeAdapter.focusedChallenge.show();
        }
    }

    public static void clearMarker(){
        markerLayer.clear();
        markerAdapter.clear();
    }

    public static void clearChallengeLayer(){
        ;
    }

    public static Polyline addPolyline( PolylineOptions polylineOptions ){
        return challengeLayer.addPolyline( polylineOptions );
    }

    public static interface OnMarkerFocusChangeListener{
        public void onMarkerFocusChange( Marker marker );
    }
    public static void addOnMarkerFocusChangeListener(@NonNull OnMarkerFocusChangeListener onMarkerFocusChangeListener ){
        onMarkerFocusChangeListeners.add(onMarkerFocusChangeListener);
    }
    public static void removeOnMarkerFocusChangeListener(@NonNull OnMarkerFocusChangeListener onMarkerFocusChangeListener ){
        if( onMarkerFocusChangeListeners.contains(onMarkerFocusChangeListener) ) {
            onMarkerFocusChangeListeners.remove(onMarkerFocusChangeListener);
        }
    }
    public static void removeAllOnMarkerFocusChangeListener( ) {
        onMarkerFocusChangeListeners.clear();
    }
}


/*TRASH
*
*
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //Challenge.setUserLocation(location);
            }
        });
*
*
*
*
* */

