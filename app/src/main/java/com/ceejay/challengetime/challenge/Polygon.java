package com.ceejay.challengetime.challenge;

import android.graphics.Color;

import com.ceejay.challengetime.geo.MapManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

/**
 * Created by CJay on 29.04.2015 for Challenge Time.
 */
public class Polygon {
    public final static String TAG = Polygon.class.getSimpleName();

    public Challenge context;
    public String title;
    public String description;
    public int fillColor = Color.TRANSPARENT;
    public int strokeColor = Color.BLACK;
    public int strokeWidth = 0;
    public boolean visible = true;

    public ArrayList<LatLng> points;

    private com.google.android.gms.maps.model.Polygon polygon;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void show() {
        PolygonOptions options = new PolygonOptions().visible( visible ).addAll( points ).strokeWidth( strokeWidth ).strokeColor( strokeColor ).fillColor( fillColor );
        polygon = MapManager.addPolygon(options);
    }

    public void changeStrokeColor( String color ){
        if( polygon != null ){
            polygon.setStrokeColor( Color.parseColor(color) );
        }
    }

    public void changeFillColor( String color ){
        if( polygon != null ){
            polygon.setFillColor(Color.parseColor(color));
        }
    }

    public void changeStrokeWidth( int width ){
        if( polygon != null ){
            polygon.setStrokeWidth( width );
        }
    }

    public void changeVisible( boolean visible ){
        if( polygon != null ){
            polygon.setVisible(visible);
        }
    }

}




