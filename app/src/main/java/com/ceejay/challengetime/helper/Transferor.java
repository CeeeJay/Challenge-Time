package com.ceejay.challengetime.helper;

import com.ceejay.challengetime.challenge.CheckpointChallenge;
import com.ceejay.challengetime.challenge.RunChallenge;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 24.01.2015 for Challenge Time.
 *
 */
public class Transferor {

    public static RunChallenge  getRunChallenge(){
        ArrayList<LatLng> track = new ArrayList<>();
        track.add(new LatLng(49.28722,7.11929));
        track.add(new LatLng(49.32924,7.32152));
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




