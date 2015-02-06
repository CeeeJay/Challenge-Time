package com.ceejay.challengetime.helper;

import android.content.Context;

import com.ceejay.challengetime.challenge.CheckpointChallenge;
import com.ceejay.challengetime.challenge.RunChallenge;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 24.01.2015 for Challenge Time.
 *
 */
public class Transferor {

    //public static MapManager mapManager;
    public static Context context ;

    public static RunChallenge  getRunChallenge(){
        ArrayList<LatLng> track = new ArrayList<>();
        track.add(new LatLng(49.28722,7.11929));
        track.add(new LatLng(49.28765,7.11888));
        track.add(new LatLng(49.28785,7.11932));
        track.add(new LatLng(49.28845,7.11885));
        track.add(new LatLng(49.28854,7.11897));
        track.add(new LatLng(49.28865,7.11895));
        track.add(new LatLng(49.28897,7.11870));
        track.add(new LatLng(49.28606,7.12685));
        return new RunChallenge( track );
    }

    public static CheckpointChallenge getCheckpointChallenge(){
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(49.28715,7.11910));
        latLngs.add(new LatLng(49.28785,7.11932));
        latLngs.add(new LatLng(49.28845,7.11886));

        return new CheckpointChallenge( latLngs );
    }
}




