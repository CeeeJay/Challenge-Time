package com.ceejay.challengetime.challenge;

import android.support.annotation.NonNull;

import com.ceejay.challengetime.helper.HttpPostContact;
import com.ceejay.challengetime.helper.Layer;
import com.ceejay.challengetime.helper.Transferor;
import com.ceejay.challengetime.main.MapManager;
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
    private static Layer markerCache;

    public static void setMapManager(@NonNull MapManager mapManager) {
        if(ChallengeAdapter.mapManager != null) {
            /*markerCache = ChallengeAdapter.mapManager.markerLayer;
            ChallengeAdapter.mapManager = mapManager;
            ChallengeAdapter.mapManager.markerOptionsMap = markerCache;*/
        }else{
            ChallengeAdapter.mapManager = mapManager;
        }

        if( Challenge.getFocus() == null || Challenge.getFocus().getChallengeState().getValence() <= 1 ){
            mapManager.showMarker();
            if(challenges.size() == 0) {

                challenges.add(Transferor.getCheckpointChallenge());
                challenges.add(Transferor.getRunChallenge());

                try {
                    HttpPostContact.reciveChallanges(challenges);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }else if( Challenge.getFocus().getChallengeState().getValence() > 1){
            Challenge.getFocus().show();
        }
    }

    public static MapManager getMapManager() {
        return mapManager;
    }


}




