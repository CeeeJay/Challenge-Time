package com.ceejay.challengetime.helper;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

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

public class MapManager {

    public GoogleMap googleMap;
    public Layer layer = new Layer();

    public MapManager( Context context , GoogleMap gMap  ) {
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
        googleMap.clear();
    }

    public void addMarker( Challenge challenge ){
        layer.addMarker(challenge.getLatLng());
    }

    private ArrayList<OnMarkerFocusChangeListener> onMarkerFocusChangeListeners = new ArrayList<>();

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


    public class Layer{

        public ExpandedList<MarkerOptions> markerStyles;
        public ExpandedList<PolylineOptions> polylineStyles;
        public ExpandedList<CircleOptions> circleStyles;

        public ExpandedList<Holder<Marker>> markers;
        public ExpandedList<Holder<Polyline>> polylines;
        public ExpandedList<Holder<Circle>> circles;

        public Layer() {
            markerStyles = new ExpandedList<>();
            polylineStyles = new ExpandedList<>();
            circleStyles = new ExpandedList<>();

            markers = new ExpandedList<>();
            polylines = new ExpandedList<>();
            circles = new ExpandedList<>();
        }

        public Layer addMarker( LatLng latLng ){
            MarkerOptions markerOptions = new MarkerOptions().position( latLng ).icon( BitmapDescriptorFactory.fromResource(R.drawable.marker) );
            markerStyles.add(markerOptions);
            return this;
        }

        public Holder<Marker> addMarker( MarkerOptions markerOptions ){
            markerStyles.add(markerOptions);
            return markers.getLast();
        }

        public Holder<Marker> getMarker( int index ){
            return markers.get( index );
        }

        public void drawMarkers(){
            clearMarker();
            for( MarkerOptions markerOptions : markerStyles ){
                markers.set( markerStyles.indexOf(markerOptions) , new Holder<>( googleMap.addMarker( markerOptions ) ) );
            }
        }

        public void removeMarker( int index ){
            markers.remove( index );
            markers.get(index).getValue().remove();
        }

        public void removeMarker( Holder<Marker> marker ){
            marker.getValue().remove();
            markers.remove( marker );
        }

        public void clearMarker(){
            for( Holder<Marker> marker : markers ){
                removeMarker( marker );
            }
        }

        public void draw(){
            drawMarkers();
        }

        public void clear(){
            clearMarker();
        }
    }
}




