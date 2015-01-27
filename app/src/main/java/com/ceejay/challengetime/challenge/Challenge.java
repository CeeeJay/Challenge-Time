package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.location.Location;
import android.os.Vibrator;
import android.widget.Toast;

import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.LatLngConvert;
import com.ceejay.challengetime.helper.StopWatch;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class Challenge {

    protected Location location;
    protected LatLng latLng;
    protected Marker marker;
    protected int sizeStartArea = 40;
    protected int sizeStopArea = 40;
    public StopWatch stopWatch;

    protected static Location userLocation;
    public static void setUserLocation(Location userLocation) {
        if(userLocation != null) {
            Challenge.userLocation = userLocation;
            if(isActivated && Challenge.focusedChallenge != null) {
                if (isStarted) {
                    Challenge.focusedChallenge.finish();
                } else {
                    Challenge.focusedChallenge.start();
                }
            }
        }
    }
    public static void setUserLocation(LatLng userLocation) {
        setUserLocation(LatLngConvert.toLocation(userLocation, "User"));
    }
    public static Location getUserPosition() {
        return userLocation;
    }

    private static Challenge focusedChallenge;
    public static void setFocus( Challenge challenge ){
        focusedChallenge = challenge;
    }
    public static Challenge getFocus(){
        return focusedChallenge;
    }

    public static boolean isActivated;
    public static boolean isStarted;

    public Challenge( LatLng latLng ) {
        this.latLng = latLng;
        location = LatLngConvert.toLocation(latLng,"Start");
        stopWatch = new StopWatch();
        this.marker = Transferor.mapManager.addMarker(this);
    }
    public Challenge( Location location ) {
        latLng = new LatLng(location.getLatitude(),location.getLongitude());
        this.location = location;
    }


    public void focus(){
        focusedChallenge = this;
    }
    public LatLng getLatLng(){
        return latLng;
    }
    public Location getLocation() {
        return location;
    }

    public void activate(){
        if( userLocation != null && userLocation.distanceTo(location) < sizeStartArea && !isActivated && !isStarted) {
            Toast.makeText(Transferor.context, "Activated", Toast.LENGTH_SHORT).show();
            isActivated = true;
        }
    }

    public void start(){
        if ( userLocation.distanceTo(location) > sizeStartArea ) {
            Toast.makeText(Transferor.context, "Started", Toast.LENGTH_SHORT).show();
            isStarted = true;
            stopWatch.start();
            Vibrator v = (Vibrator) Transferor.context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    public void finish(){
        isActivated = false;
        isStarted = false;
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Finished at " + stopWatch.getTime() ,Toast.LENGTH_SHORT).show();
    }

    public void stop(){
        isActivated = false;
        isStarted = false;
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Stopped at " + stopWatch.getTime() ,Toast.LENGTH_SHORT).show();
        stopWatch.stop();
    }

}




