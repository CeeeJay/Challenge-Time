package com.ceejay.challengetime.helper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class Position {
    public final static String TAG = Position.class.getSimpleName();

    public static String  toStr( ArrayList<LatLng> latLngs ){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(!latLngs.isEmpty()) {
            for ( LatLng latLng : latLngs ) {
                sb.append(toStr(latLng)+",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    public static String toStr( LatLng latLng ){
        return latLng == null ? "null" : "\"LatLng(" + latLng.latitude + "," + latLng.longitude  + ")\"";
    }

}




