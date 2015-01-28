package com.ceejay.challengetime.helper;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Development on 22.12.2014.
 *
 */
public class PointD extends Point{
    public double x = 0;
    public double y = 0;

    public PointD(){};

    public PointD(double x, double y){
        this.x = x;
        this.y = y;
    }

    public PointD(String parse){
        parse = parse.replace("POINT(","");
        parse = parse.replace(")","");
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
}
