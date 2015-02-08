package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ceejay.challengetime.helper.Distance;
import com.ceejay.challengetime.helper.StopWatch;
import com.ceejay.challengetime.helper.Transferor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class Challenge {

    /**
     * Static Stuff
     *
     */

    private static Challenge focusedChallenge;

    protected static Context context;
    protected static Location userLocation;

    private static ArrayList<OnFocusChangeListener> onFocusChangeListeners = new ArrayList<>();

    public static Challenge getFocus(){
        return focusedChallenge;
    }

    public static void setContext(Context context) {
        Challenge.context = context;
    }
    public static void setUserLocation(Location userLocation) {
        Challenge.userLocation = userLocation;
        if( Challenge.focusedChallenge != null ) {
            Challenge.focusedChallenge.userLocationChanged();
        }
    }
    public static Location getUserLoaction() {
        return userLocation;
    }
    public static LatLng getUserLatLng() {
        return new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
    }
    public static void setFocus( Challenge challenge ){
        if(getFocus() != null) {
            getFocus().removeAllListener();
            getFocus().setChallengeState(ChallengeState.isNotFocused);
        }
        focusedChallenge = challenge;
        if( focusedChallenge != null) {
            challenge.focus();
        }
        for(OnFocusChangeListener onFocusChangeListener : onFocusChangeListeners){
            onFocusChangeListener.onFocusChange( focusedChallenge );
        }
    }

    public static void addOnFocusChangeListener(OnFocusChangeListener onFocusChange){
        onFocusChangeListeners.add(onFocusChange);
    }
    public static void removeOnFocusChange( OnFocusChangeListener onFocusChange ){
        if(onFocusChangeListeners.contains(onFocusChange)) {
            onFocusChangeListeners.remove(onFocusChange);
        }
    }
    public static void clearAllOnFocusChange(){
        onFocusChangeListeners.clear();
    }
    public interface OnFocusChangeListener{
        public void onFocusChange( Challenge focus );
    }

    /**
     * Instance Stuff
     *
     */

    public enum ChallengeState{
        isNotFocused(0),
        isFocused (1),
        isShown (2),
        isReady (3),
        isActivated (4),
        isStarted (5),
        isFinished (6),
        isStopped (7);

        private int valence = 0;
        ChallengeState( int valence ) {
            this.valence = valence;
        }

        public int getValence() {
            return valence;
        }

        public boolean isNotFocused(){
            return valence == 0;
        }
        public boolean isFocused(){
            return valence == 1;
        }
        public boolean isShown(){
            return valence == 2;
        }
        public boolean isReady(){
            return valence == 3;
        }
        public boolean isActivated(){
            return valence == 4;
        }
        public boolean isStarted(){
            return valence == 5;
        }
        public boolean isFinished(){
            return valence == 6;
        }
        public boolean isStopped(){
            return valence == 7;
        }

    }

    private String challengeName;
    private StopWatch stopWatch;

    protected ChallengeState challengeState;
    protected LatLng latLng;
    protected Marker marker;
    protected int sizeStartArea = 40;
    protected int sizeStopArea = 40;

    public ArrayList<OnChallengeReadyListener> readyListeners = new ArrayList<>();
    public ArrayList<OnChallengeActivateListener> activateListeners = new ArrayList<>();
    public ArrayList<OnChallengeStartListener> startListeners = new ArrayList<>();
    public ArrayList<OnChallengeFinishListener> finishListeners = new ArrayList<>();
    public ArrayList<OnChallengeStopListener> stopListeners = new ArrayList<>();
    public ArrayList<OnChallengeStateChangeListener> onChallengeStateChangeListeners = new ArrayList<>();
    public ArrayList<ChallengeListener> challengeListeners = new ArrayList<>();

    public Challenge( LatLng latLng ) {
        init(latLng, "Untiled");
    }
    public Challenge( LatLng latLng , String challengeName ) {
        init(latLng, challengeName);
    }

    public void init( LatLng latLng , String challengeName ){
        setChallengeState(ChallengeState.isNotFocused);
        this.challengeName = challengeName;
        this.latLng = latLng;
        stopWatch = new StopWatch();
        setStopWatchVibrate();
        this.marker = ChallengeAdapter.getMapManager().addMarker(this);
    }

    public void userLocationChanged(){
        if( userLocation != null ) {
            if ( Distance.between(userLocation, latLng) < sizeStartArea) {
                if (challengeState.isShown()) {
                    ready();
                }
            }else{
                if ( challengeState.isActivated() ) {
                    start();
                } else if( challengeState.isReady() ){
                    setChallengeState(ChallengeState.isShown);
                }
            }
        }else if( challengeState.isReady() ){
            setChallengeState(ChallengeState.isShown);
        }
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
    public void setStopWatchVibrate(){
        stopWatch.setStartVibrate(500);
        stopWatch.setStopVibrate(500);
    }
    public void setChallengeState( ChallengeState challengeState ){
        this.challengeState = challengeState;
        for(OnChallengeStateChangeListener onChallengeStateChangeListener : onChallengeStateChangeListeners){
            onChallengeStateChangeListener.onStateChange(challengeState);
        };
    }

    public ChallengeState getState(){
        return challengeState;
    }
    public Marker getMarker() {
        return marker;
    }
    public String getChallengeName() {
        return challengeName;
    }
    public LatLng getLatLng(){
        return latLng;
    }
    public ChallengeState getChallengeState(){
        return challengeState;
    }

    public void focus(){
        setChallengeState(ChallengeState.isFocused);
    }
    public void show(){
        ChallengeAdapter.getMapManager().hideMarker();
        if( challengeState.isFocused() || challengeState.isStopped() ){
            setChallengeState(ChallengeState.isShown);
        }
    }
    public void ready(){
        Toast.makeText(Transferor.context, "Ready", Toast.LENGTH_SHORT).show();
        for (OnChallengeReadyListener readyListener : readyListeners) {
            readyListener.onReady();
        }
        setChallengeState(ChallengeState.isReady);
    }
    public void activate(){
        if( userLocation != null && Distance.between(userLocation,latLng) < sizeStartArea && challengeState.isReady()) {
            Toast.makeText(Transferor.context, "Activated", Toast.LENGTH_SHORT).show();
            for (OnChallengeActivateListener activateListener : activateListeners) {
                activateListener.onActivate();
            }
            setChallengeState(ChallengeState.isActivated);
        }
    }
    protected void start(){
        Toast.makeText(Transferor.context, "Started", Toast.LENGTH_SHORT).show();
        stopWatch.start();
        for (OnChallengeStartListener startListener : startListeners) {
            startListener.onStart();
        }
        setChallengeState(ChallengeState.isStarted);
    }
    protected void finish(){
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Finished at " + stopWatch.getTime() ,Toast.LENGTH_LONG).show();
        for (OnChallengeFinishListener finishListener : finishListeners) {
            finishListener.onFinish();
        }
        setChallengeState(ChallengeState.isFinished);
    }
    public void stop(){
        stopWatch.pause();
        Toast.makeText(Transferor.context,"Stopped at " + stopWatch.getTime() ,Toast.LENGTH_SHORT).show();
        stopWatch.stop();
        for (OnChallengeStopListener stopListener : stopListeners) {
            stopListener.onStop();
        }
        setChallengeState(ChallengeState.isStopped);
        show();
    }

    /**
     * OnChallengeReadyListener
     *
     */
    public interface OnChallengeReadyListener{
        public void onReady();
    }
    public void addOnChallengeReadyListener(@NonNull OnChallengeReadyListener readyListener ){
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
    public void addOnChallengeActivateListener(@NonNull OnChallengeActivateListener activateListener ){
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
    public void addOnChallengeStartListener(@NonNull OnChallengeStartListener startListener ){
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
    public void addOnChallengeFinishListener(@NonNull OnChallengeFinishListener finishListener ){
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



    public interface OnChallengeStopListener{
        public void onStop();
    }
    public void addOnChallengeStopListener(@NonNull OnChallengeStopListener stopListener){
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
     * OnChallengeStateChangeListener
     *
     */

    public interface OnChallengeStateChangeListener{
        public void onStateChange( ChallengeState challengeState );
    }
    public void addOnChallengeStateChangeListener(@NonNull OnChallengeStateChangeListener onChallengeStateChangListener){
        onChallengeStateChangeListeners.add(onChallengeStateChangListener);
    }
    public void removeOnChallengeStateChangeListener(@NonNull OnChallengeStateChangeListener onChallengeStateChangListener ){
        if( onChallengeStateChangeListeners.contains(onChallengeStateChangListener) ) {
            onChallengeStateChangeListeners.remove(onChallengeStateChangListener);
        }
    }
    public void removeAllOnChallengeStateChangeListener( ){
        onChallengeStateChangeListeners.clear();
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
    public void addChallengeListener(@NonNull ChallengeListener challengeListener ){
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
        removeAllOnChallengeStateChangeListener();
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
            return new RunChallenge( latLngs , challengeName != null ? challengeName : "Untiteld");
        }

        public CheckpointChallenge getCheckpointChallenge(){
            return new CheckpointChallenge(checkpointLocations, challengeName != null ? challengeName : "Untiteld");
        }

        public void setChallengeName( String challengeName ) {
            this.challengeName = challengeName;
        }

        public void setStartLocation( LatLng startLocation ){
            this.startLocation = startLocation;
        }

        public void setCheckpointLocations( ArrayList<LatLng> checkpointLocations ){
            this.checkpointLocations = checkpointLocations;
        }

        public void setStopLocation( LatLng stopLocation ){
            this.stopLocation = stopLocation;
        }
    }

}




