package com.ceejay.challengetime.helper.math;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Development on 22.12.2014.
 *
 */
public class PointD extends Point{
    public final static String TAG = PointD.class.getSimpleName();

    public double x = 0;
    public double y = 0;

    public PointD(){};

    public PointD(double x, double y){
        this.x = x;
        this.y = y;
    }

    public PointD(String parse){
        parse = parse.substring(6,parse.length() - 1);
        x = Double.parseDouble(parse.split(" ")[0]);
        y = Double.parseDouble(parse.split(" ")[1]);
    }

    public void set( double x,double y ){
        this.x = x;
        this.y = y;
    }

    public LatLng toLatLng(){
        return new LatLng(x,y);
    }

    public static ArrayList<LatLng> getPoints(String parse){
        ArrayList<LatLng> latLngs = new ArrayList<>();
        parse = parse.substring(19,parse.length() - 1);
        String[] points = parse.split(",");//
        for( String point : points ){
            latLngs.add((new PointD(point)).toLatLng());
        }
        return latLngs;
    }
}
