package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
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

    private static Challenge focusedChallenge;

    protected static Context context;
    protected static Location userLocation;

    public static boolean isReady = false;
    public static boolean isActivated = false;
    public static boolean isStarted = false;
    public static boolean isFinished = false;
    public static boolean isStopped = false;

    public static void setContext(Context context) {
        Challenge.context = context;
    }
    public static void setUserLocation(Location userLocation) {
        if(userLocation != null) {
            Challenge.userLocation = userLocation;
            if( Challenge.focusedChallenge != null ) {
                Challenge.focusedChallenge.setUserLocation();
            }
        }
    }
    public static Location getUserPosition() {
        return userLocation;
    }
    public static void setFocus( Challenge challenge ){

        if(getFocus() != null) {
            getFocus().removeAllListener();
        }
        focusedChallenge = challenge;

        if(challenge != null) {
            challenge.setOnChallengeReadyListener(new Challenge.OnChallengeReadyListener() {
                @Override
                public void onReady() {
                    Transferor.animHol.hideActivateButton(false);
                }
            });

            challenge.setOnChallengeActivateListener(new Challenge.OnChallengeActivateListener() {
                @Override
                public void onActivate() {
                    Transferor.animHol.hideActivateButton(true);
                }
            });
            getFocus().setUserLocation();
        }
    }
    public static Challenge getFocus(){
        return focusedChallenge;
    }

    private String challengeName;
    protected LatLng latLng;
    protected Marker marker;
    protected int sizeStartArea = 40;
    protected int sizeStopArea = 40;
    public StopWatch stopWatch;
    public ArrayList<OnChallengeReadyListener> readyListeners = new ArrayList<>();
    public ArrayList<OnChallengeActivateListener> activateListeners = new ArrayList<>();
    public ArrayList<OnChallengeStartListener> startListeners = new ArrayList<>();
    public ArrayList<OnChallengeFinishListener> finishListeners = new ArrayList<>();
    public ArrayList<OnChallengeStopListener> stopListeners = new ArrayList<>();
    public ArrayList<ChallengeListener> challengeListeners = new ArrayList<>();

    public Challenge( LatLng latLng ) {
        this.latLng = latLng;
        stopWatch = new StopWatch();
        this.marker = ChallengeAdapter.getMapManager().addMarker(this);
    }

    public void setUserLocation(){
        if ( Distance.between(userLocation,latLng) < sizeStartArea ) {
            if(!isReady) {
                ready();
            }
            isReady = true;
        }else{
            isReady = false;
        }
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
        setFocus(this);
    }
    public LatLng getLatLng(){
        return latLng;
    }

    public void ready(){
        for (OnChallengeReadyListener readyListener : readyListeners) {
            readyListener.onReady();
        }
    }

    public void activate(){
        if( userLocation != null && Distance.between(userLocation,latLng) < sizeStartArea && !isActivated && !isStarted) {
            Toast.makeText(Transferor.context, "Activated", Toast.LENGTH_SHORT).show();
            isActivated = true;
            for (OnChallengeActivateListener activateListener : activateListeners) {
                activateListener.onActivate();
            }
            //userLocationChanged();
        }
    }

    protected void start(){
        Toast.makeText(Transferor.context, "Started", Toast.LENGTH_SHORT).show();
        isStarted = true;
        stopWatch.start();
        for (OnChallengeStartListener startListener : startListeners) {
            startListener.onStart();
        }
        //userLocationChanged();
    }

    protected void finish(){
        isActivated = false;
        isStarted = false;
        isFinished = true;
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Finished at " + stopWatch.getTime() ,Toast.LENGTH_LONG).show();
        for (OnChallengeFinishListener finishListener : finishListeners) {
            finishListener.onFinish();
        }
    }

    public void stop(){
        isReady = false;
        isActivated = false;
        isStarted = false;
        isFinished = false;
        isStopped = true;
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Stopped at " + stopWatch.getTime() ,Toast.LENGTH_SHORT).show();
        stopWatch.stop();
        for (OnChallengeStopListener stopListener : stopListeners) {
            stopListener.onStop();
        }
    }

    /**
     * OnChallengeReadyListener
     *
     */
    public interface OnChallengeReadyListener{
        public void onReady();
    }
    public void setOnChallengeReadyListener(@NonNull OnChallengeReadyListener readyListener ){

        readyListeners.add(readyListener);
    }
    public void removeOnChallengeReadyListener(@NonNull OnChallengeReadyListener readyListener ){
        if( readyListeners.contains(readyListener) ) {
            readyListeners.remove(readyListener);
        }
    }
    public void removeAllOnChallengeReadyListener( ){
            readyListeners.clear();
    }

    /**
     * OnChallengeReadyListener
     *
     */
    public interface OnChallengeActivateListener{
        public void onActivate();
    }
    public void setOnChallengeActivateListener(@NonNull OnChallengeActivateListener activateListener ){
        activateListeners.add(activateListener);
    }
    public void removeOnChallengeActivateListener(@NonNull OnChallengeActivateListener activateListener ){
        if( activateListeners.contains(activateListener) ) {
            activateListeners.remove(activateListener);
        }
    }
    public void removeAllOnChallengeActivateListener( ){
        activateListeners.clear();
    }

    /**
     * OnChallengeStartedListener
     *
     */
    public interface OnChallengeStartListener{
        public void onStart();
    }
    public void setOnChallengeStartListener(@NonNull OnChallengeStartListener startListener ){
        startListeners.add(startListener);
    }
    public void removeOnChallengeStartListener(@NonNull OnChallengeStartListener startListener ){
        if( startListeners.contains(startListener) ) {
            startListeners.remove(startListener);
        }
    }
    public void removeAllOnChallengeStartListener( ){
        startListeners.clear();
    }

    /**
     * OnChallengeStartedListener
     *
     */
    public interface OnChallengeFinishListener{
        public void onFinish();
    }
    public void setOnChallengeFinishListener(@NonNull OnChallengeFinishListener finishListener ){
        finishListeners.add(finishListener);
    }
    public void removeOnChallengeFinishListener(@NonNull OnChallengeFinishListener finishListener ){
        if( finishListeners.contains(finishListener) ) {
            finishListeners.remove(finishListener);
        }
    }
    public void removeAllOnChallengeFinishListener( ){
        finishListeners.clear();
    }


    /**
     * OnChallengeStartedListener
     *
     */
    public interface OnChallengeStopListener{
        public void onStop();
    }
    public void setOnChallengeStopListener(@NonNull OnChallengeStopListener stopListener ){
        stopListeners.add(stopListener);
    }
    public void removeOnChallengeStopListener(@NonNull OnChallengeStopListener stopListener ){
        if( stopListeners.contains(stopListener) ) {
            stopListeners.remove(stopListener);
        }
    }
    public void removeAllOnChallengeStopListener( ){
        stopListeners.clear();
    }


    /**
     * OnChallengeStartedListener
     *
     */

    public interface ChallengeListener{
        public void onReady();
        public void onActivate();
        public void onStart();
        public void onFinish();
        public void onStop();
    }

    public void setChallengeListener(@NonNull ChallengeListener challengeListener ){
        challengeListeners.add(challengeListener);
    }

    public void removeChallengeListener(@NonNull ChallengeListener challengeListener ){
        if( challengeListeners.contains(challengeListener) ) {
            challengeListeners.remove(challengeListener);
        }
    }
    public void removeAllChallengeListener( ){
        challengeListeners.clear();
    }

    public void removeAllListener(){
        removeAllOnChallengeReadyListener();
        removeAllOnChallengeActivateListener();
        removeAllOnChallengeStartListener();
        removeAllOnChallengeFinishListener();
        removeAllOnChallengeStopListener();
        removeAllChallengeListener();
    }

    /**
     * Builder
     *
     */

    public static class Builder{
        private String challengeName;
        protected LatLng latLng;
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




