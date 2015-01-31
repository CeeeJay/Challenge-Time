package com.ceejay.challengetime.helper;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by CJay on 26.01.2015 for Challenge Time.
 *
 */
public class Polygon {

    ArrayList<Point> points;

    public Polygon() {

    }

    public void addPoint( Point point ){
        points.add(point);
    }



    public static Path polygonPath( int x , int y , int radius , int corners , double beginDeg ){
        Path path = new Path();
        path.moveTo( (float) ( x + Math.cos( beginDeg ) * radius ) , (float) ( y + Math.sin( beginDeg ) * radius ) );
        for( int i = 1 ; i <= corners ; i++ ){
            path.lineTo( (float) ( x + Math.cos(i * 2 * Math.PI / corners + beginDeg) * radius ) , (float) ( y + Math.sin(i * 2 * Math.PI / corners + beginDeg) * radius ) );
        }
        return path;
    }
    public static Path polygonPath( Point point, int radius , int corners , double beginDeg ){
        return polygonPath( point.x , point.y , radius , corners , beginDeg );
    }
    public static Path polygonPath( int x , int y , int radius , int corners ){
        return polygonPath( x , y , radius , corners , 0 );
    }
    public static Path polygonPath( Point point, int radius , int corners ){
        return polygonPath( point.x , point.y , radius , corners , 0 );
    }

}




