package com.ceejay.challengetime.challenge;

import android.support.annotation.NonNull;

import com.ceejay.challengetime.geo.MapManager;

import java.util.ArrayList;

/**
 * Created by CJay on 28.01.2015 for Challenge Time.
 *
 */
public class ChallengeAdapter extends ArrayList<Challenge> {

    public static ArrayList<Challenge> challenges = new ArrayList<>();
    public static Challenge focusedChallenge;
    public static ChallengeObserver observer;

    private static ArrayList<OnChallengeFocusChangeListener> onChallengeFocusChangeListeners = new ArrayList<>();

    public static void setChallengeObserver( ChallengeObserver o ){
        observer = o;
    }

    public static void addChallenge(Challenge challenge){
        if(challenge != null) {
            challenges.add(challenge);
            MapManager.addMarker(challenge);
        }
    }

    public static void focusChallenge( Challenge challenge ){
        if(focusedChallenge != null && focusedChallenge.status == Challenge.Status.SHOWN){
            focusedChallenge.status = Challenge.Status.HIDDEN;
            MapManager.showMarkerLayer();
        }

        if( observer != null){
            focusedChallenge = challenge;
            observer.setChallenge(focusedChallenge);
            for( OnChallengeFocusChangeListener listener : onChallengeFocusChangeListeners ){
                listener.onChallengeFocusChange( challenge );
            }
        }
    }

    public static Challenge getFocusedChallenge() {
        return focusedChallenge;
    }

    public static void setFocusedChallenge(Challenge focusedChallenge) {
        ChallengeAdapter.focusedChallenge = focusedChallenge;
    }

    public static interface OnChallengeFocusChangeListener {
        public void onChallengeFocusChange( Challenge challenge );
    }
    public static void addOnChallengeFocusChangeListener(@NonNull OnChallengeFocusChangeListener onChallengeFocusChangeListener){
        onChallengeFocusChangeListeners.add(onChallengeFocusChangeListener);
    }
    public static void removeOnChallengeFocusChangeListener(@NonNull OnChallengeFocusChangeListener onChallengeFocusChangeListener){
        if( onChallengeFocusChangeListeners.contains(onChallengeFocusChangeListener) ) {
            onChallengeFocusChangeListeners.remove(onChallengeFocusChangeListener);
        }
    }
    public static void removeAllOnChallengeFocusChangeListener( ) {
        onChallengeFocusChangeListeners.clear();
    }

}




