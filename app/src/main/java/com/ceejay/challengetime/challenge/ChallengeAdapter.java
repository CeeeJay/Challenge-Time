package com.ceejay.challengetime.challenge;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ceejay.challengetime.MapManager;
import com.ceejay.challengetime.Transferor;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 28.01.2015 for Challenge Time.
 *
 */
public class ChallengeAdapter extends ArrayList<Challenge> {

    private static MapManager mapManager;
    private static ArrayList<Challenge> challenges = new ArrayList<>();
    private static HashMap<MarkerOptions,Challenge> markerCache;

    public static void setMapManager(@NonNull MapManager mapManager) {
        if(ChallengeAdapter.mapManager != null) {
            markerCache = ChallengeAdapter.mapManager.markerOptionsMap;
            ChallengeAdapter.mapManager = mapManager;
            ChallengeAdapter.mapManager.markerOptionsMap = markerCache;
        }else{
            ChallengeAdapter.mapManager = mapManager;
        }

        if(Challenge.getFocus() == null){
            mapManager.refreshMarker();
            if(challenges.size() == 0) {
                challenges.add(Transferor.getCheckpointChallenge());
                challenges.add(Transferor.getRunChallenge());
            }
        }else{
            Challenge.getFocus().focus();
        }
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public ChallengeAdapter() {
        super();

    }

}




