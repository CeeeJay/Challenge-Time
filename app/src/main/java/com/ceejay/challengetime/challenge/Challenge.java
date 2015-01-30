package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.Distance;
import com.ceejay.challengetime.helper.StopWatch;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class Challenge {

    private String challengeName;
    protected LatLng latLng;
    protected Marker marker;
    protected int sizeStartArea = 40;
    protected int sizeStopArea = 40;
    public StopWatch stopWatch;

    protected static Context context;
    public static void setContext(Context context) {
        Challenge.context = context;
    }

    protected static Location userLocation;
    public static void setUserLocation(Location userLocation) {
        if(userLocation != null) {
            Challenge.userLocation = userLocation;
            if( Challenge.focusedChallenge != null ) {
                Challenge.focusedChallenge.userLocationChanged();
            }
        }
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
    public static boolean isFinished;

    public Challenge( LatLng latLng ) {
        this.latLng = latLng;
        stopWatch = new StopWatch();
        this.marker = ChallengeAdapter.getMapManager().addMarker(this);
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    protected void userLocationChanged(){
        if(isActivated && Challenge.focusedChallenge != null) {
            if (isStarted) {
                Challenge.focusedChallenge.finish();
            } else {
                if ( Distance.between(userLocation,latLng) > sizeStartArea ) {
                    Challenge.focusedChallenge.start();
                }
            }
        }
    }

    public void focus(){
        focusedChallenge = this;
    }
    public LatLng getLatLng(){
        return latLng;
    }

    public void activate(){
        if( userLocation != null && Distance.between(userLocation,latLng) < sizeStartArea && !isActivated && !isStarted) {
            Toast.makeText(Transferor.context, "Activated", Toast.LENGTH_SHORT).show();
            isActivated = true;
            userLocationChanged();
        }
    }

    protected void start(){
        Toast.makeText(Transferor.context, "Started", Toast.LENGTH_SHORT).show();
        isStarted = true;
        stopWatch.start();
        userLocationChanged();
    }

    protected void finish(){
        isActivated = false;
        isStarted = false;
        isFinished = true;
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Finished at " + stopWatch.getTime() ,Toast.LENGTH_LONG).show();
    }

    public void stop(){
        isActivated = false;
        isStarted = false;
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Stopped at " + stopWatch.getTime() ,Toast.LENGTH_SHORT).show();
        stopWatch.stop();
    }

    public static class Builder{
        private String challengeName;
        protected Location location;
        protected LatLng latLng;
        protected Marker marker;
        protected int sizeStartArea = 40;
        protected int sizeStopArea = 40;
        public StopWatch stopWatch;
        public LatLng startLocation;
        public ArrayList<LatLng> checkpointLocations;
        public LatLng stopLocation;

        public Builder() {
        }

        public RunChallenge getRunChallenge(){
            ArrayList<LatLng> latLngs = new ArrayList<>();
            latLngs.add(startLocation);
            latLngs.add(stopLocation);
            return new RunChallenge( latLngs );
        }

        public CheckpointChallenge getCheckpointChallenge(){
            return new CheckpointChallenge(checkpointLocations);
        }

        public void setChallengeName( String challengeName ) {
            this.challengeName = challengeName;
        }

        public void setStartLocation( LatLng startLocation ){
            this.startLocation = startLocation;
        }

        public void setCheckpointLocations( ArrayList<LatLng> checkpointLocations ){
            Toast.makeText(Transferor.context,checkpointLocations.get(0).toString(),Toast.LENGTH_SHORT).show();
            this.checkpointLocations = checkpointLocations;
        }

        public void setStopLocation( LatLng stopLocation ){
            this.stopLocation = stopLocation;
        }
    }

}




