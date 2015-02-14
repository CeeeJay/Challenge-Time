package com.ceejay.challengetime.helper;

import com.ceejay.challengetime.R;
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

/**
 * Created by CJay on 14.02.2015 for Challenge Time.
 */
public class Layer {
    public final static String TAG = Layer.class.getSimpleName();

    public ArrayList<Marker> markers;
    public ArrayList<Polyline> polylines;
    public ArrayList<Circle> circles;

    public GoogleMap googleMap;

    public Layer( GoogleMap googleMap ) {
        markers = new ArrayList<>();
        polylines = new ArrayList<>();
        circles = new ArrayList<>();
        this.googleMap = googleMap;
    }



    public Marker addMarker( LatLng latLng ){
        MarkerOptions markerOptions = new MarkerOptions().position( latLng ).icon( BitmapDescriptorFactory.fromResource(R.drawable.marker) );
        return addMarker(markerOptions);
    }

    public Marker addMarker( MarkerOptions markerOptions ){
        return addMarker(googleMap.addMarker(markerOptions));
    }

    public Marker addMarker( Marker marker ){
        markers.add( marker );
        return markers.get(markers.size() - 1);
    }

    public Marker getMarker( int index ){
        return markers.get(index);
    }

    public ArrayList<Marker> getMarkers(){
        return markers;
    }

    public void showMarkers(){
        for( Marker marker : markers ){
            marker.setVisible(true);
        }
    }

    public void hideMarkers(){
        for( Marker marker : markers ){
            marker.setVisible(false);
        }
    }

    public void removeMarker( Marker marker ){
        marker.remove();
        markers.remove(marker);
    }

    public void clearMarker(){
        for( Marker marker : markers ){
            marker.remove();
        }
        markers.clear();
    }
    //#########################Circle


    public Circle addCircle( LatLng latLng , double radius , int fillColor ){
        CircleOptions circleOptions = new CircleOptions().center( latLng ).radius( radius ).fillColor(fillColor).strokeWidth(0);
        return addCircle(circleOptions);
    }

    public Circle addCircle( CircleOptions circleOptions ){
        return addCircle(googleMap.addCircle(circleOptions));
    }

    public Circle addCircle( Circle marker ){
        circles.add( marker );
        return circles.get(circles.size() - 1);
    }

    public Circle getCircle( int index ){
        return circles.get(index);
    }

    public ArrayList<Circle> getCircles(  ){
        return circles;
    }

    public void showCircles(){
        for( Circle circle : circles ){
            circle.setVisible(true);
        }
    }

    public void hideCircles(){
        for( Circle circle : circles ){
            circle.setVisible(false);
        }
    }

    public void removeCircle( Circle circle ){
        circle.remove();
        circles.remove( circle );
    }

    public void clearCircle(){
        for( Circle circle : circles ){
            circle.remove();
        }
        circles.clear();
    }
    //#########################Polyline


    public Polyline addPolyline( LatLng latLng , ArrayList<LatLng> points ){
        PolylineOptions polylineOptions = new PolylineOptions().addAll( points );
        return addPolyline( polylineOptions );
    }

    public Polyline addPolyline( PolylineOptions polylineOptions ){
        return addPolyline(googleMap.addPolyline( polylineOptions ));
    }

    public Polyline addPolyline( Polyline marker ){
        polylines.add( marker );
        return polylines.get(polylines.size() - 1);
    }

    public Polyline getPolyline( int index ){
        return polylines.get(index);
    }

    public ArrayList<Polyline> getPolylines(  ){
        return polylines;
    }

    public void showPolylines(){
        for( Polyline polyline : polylines ){
            polyline.setVisible(true);
        }
    }

    public void hidePolylines(){
        for( Polyline polyline : polylines ){
            polyline.setVisible(false);
        }
    }

    public void removePolyline( Polyline circle ){
        circle.remove();
        polylines.remove( circle );
    }

    public void clearPolyline(){
        for( Polyline polyline : polylines ){
            polyline.remove();
        }
        polylines.clear();
    }

    public void show(){
        showMarkers();
        showCircles();
        showPolylines();
    }

    public void hide(){
        hideMarkers();
        hideCircles();
        hidePolylines();
    }

    public void clear(){
        clearMarker();
        clearCircle();
        clearPolyline();
    }

}





