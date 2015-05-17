package com.ceejay.challengetime.helper;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class Position{
    public final static String TAG = Position.class.getSimpleName();

    public static final double a = 6378137.0;
    public static final double b = 6356752.3142;

    double latitude;
    double longitude;

    public Position( double latitude , double longitude ) {
        if(-180.0D <= longitude && longitude < 180.0D) {
            this.longitude = longitude;
        } else {
            this.longitude = ((longitude - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
        }
        this.latitude = Math.max(-90.0D, Math.min(90.0D, latitude));

    }

    public Position( LatLng latLng ) {
        this( latLng.latitude,latLng.longitude);
    }

    public Position( Location location ) {
        this( location.getLatitude(),location.getLongitude());
    }

    public Position positionIn( double distance , float angle ){
        double ?2 = Math.asin( Math.sin(this.latitude)*Math.cos(distance/a) +
                Math.cos(this.latitude)*Math.sin(distance/a)*Math.cos(angle) );
        double ?2 = this.longitude + Math.atan2(Math.sin(angle)*Math.sin(distance/a)*Math.cos(this.latitude),
                Math.cos(distance/a)-Math.sin(this.latitude)*Math.sin(?2));
        return new Position(?2,?2);
    }

    public static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    public float distanceTo( LatLng latLng ){
        int MAXITERS = 20;
        double lat1 = this.latitude * Math.PI / 180.0;
        double lat2 = this.longitude * Math.PI / 180.0;
        double lon1 = latLng.latitude * Math.PI / 180.0;
        double lon2 = latLng.longitude * Math.PI / 180.0;

        //double a = 6378137.0; // WGS84 major axis
        //double b = 6356752.3142; // WGS84 semi-major axis
        double f = (a - b) / a;
        double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);

        double L = lon2 - lon1;
        double A = 0.0;
        double U1 = Math.atan((1.0 - f) * Math.tan(lat1));
        double U2 = Math.atan((1.0 - f) * Math.tan(lat2));

        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;

        double sigma = 0.0;
        double deltaSigma = 0.0;
        double cosSqAlpha = 0.0;
        double cos2SM = 0.0;
        double cosSigma = 0.0;
        double sinSigma = 0.0;
        double cosLambda = 0.0;
        double sinLambda = 0.0;

        double lambda = L; // initial guess
        for (int iter = 0; iter < MAXITERS; iter++) {
            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double sinSqSigma = t1 * t1 + t2 * t2; // (14)
            sinSigma = Math.sqrt(sinSqSigma);
            cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
            sigma = Math.atan2(sinSigma, cosSigma); // (16)
            double sinAlpha = (sinSigma == 0) ? 0.0 : cosU1cosU2 * sinLambda / sinSigma; // (17)
            cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
            cos2SM = (cosSqAlpha == 0) ? 0.0 : cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)

            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
            A = 1 + (uSquared / 16384.0) * // (3)
                    (4096.0 + uSquared *
                            (-768 + uSquared * (320.0 - 175.0 * uSquared)));
            double B = (uSquared / 1024.0) * // (4)
                    (256.0 + uSquared *
                            (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
            double C = (f / 16.0) *
                    cosSqAlpha *
                    (4.0 + f * (4.0 - 3.0 * cosSqAlpha)); // (10)
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * sinSigma * // (6)
                    (cos2SM + (B / 4.0) *
                            (cosSigma * (-1.0 + 2.0 * cos2SMSq) -
                                    (B / 6.0) * cos2SM *
                                            (-3.0 + 4.0 * sinSigma * sinSigma) *
                                            (-3.0 + 4.0 * cos2SMSq)));

            lambda = L +
                    (1.0 - C) * f * sinAlpha *
                            (sigma + C * sinSigma *
                                    (cos2SM + C * cosSigma *
                                            (-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

            double delta = (lambda - lambdaOrig) / lambda;
            if (Math.abs(delta) < 1.0e-12) {
                break;
            }
        }

        return (float) (b * A * (sigma - deltaSigma));
    }

    @Override
    public String toString() {
        return "\"LatLng(" + latitude + "," + longitude  + ")\"";
    }

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




/*
*
        /*double dLat = deg2rad(this.latitude-latLng.latitude);  // deg2rad below
        double dLon = deg2rad(this.longitude-latLng.longitude);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(this.latitude)) * Math.cos(deg2rad(latLng.latitude)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;*/
* */