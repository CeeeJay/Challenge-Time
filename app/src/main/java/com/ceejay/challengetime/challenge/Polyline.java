package com.ceejay.challengetime.challenge;

import android.graphics.Color;

import com.ceejay.challengetime.geo.MapManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by CJay on 30.04.2015 for Challenge Time.
 */
public class Polyline {
    public final static String TAG = Polyline.class.getSimpleName();

    public Challenge context;
    public String title;
    public String description;
    public int color = Color.BLACK;
    public int width = 5;
    public boolean visible = true;

    public ArrayList<LatLng> points;

    private com.google.android.gms.maps.model.Polyline polyline;

    public Polyline() {
        points = new ArrayList<>();
    }

    public void show() {
        PolylineOptions options = new PolylineOptions().addAll(points).visible( visible ).color(color).width(width);
        polyline = MapManager.addPolyline(options);
    }

    public void changeColor( String color ){
        if( polyline != null ){
            polyline.setColor(Color.parseColor(color));
        }
    }

    public void changeWidth( int width ){
        if( polyline != null ){
            polyline.setWidth(width);
        }
    }

    public void changeVisible( boolean visible ){
        if( polyline != null ){
            polyline.setVisible(visible);
        }
    }

}




