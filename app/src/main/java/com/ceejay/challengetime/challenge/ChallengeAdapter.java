package com.ceejay.challengetime.challenge;

import android.support.annotation.NonNull;

import com.ceejay.challengetime.geo.MapManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 28.01.2015 for Challenge Time.
 *
 */
public class ChallengeAdapter extends ArrayList<Challenge> {

    private static MapManager mapManager;
    private static ArrayList<Challenge> challenges = new ArrayList<>();
    private static Challenge focusedChallenge;

    public static void addChallenge(@NonNull Challenge challenge){
        challenges.add(challenge);
        //challenge.position = new LatLng(49.29055,7.12955);
        MapManager.addMarker(challenge);
        focusChallenge(challenge);
    }

    public static void focusChallenge( Challenge challenge ){
        focusedChallenge = challenge;
    }

    public static void showChallenge(){
        MapManager.hideMarker();
        focusedChallenge.show();
    }

    public static MapManager getMapManager() {
        return mapManager;
    }


}




