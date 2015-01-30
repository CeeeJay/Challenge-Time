package com.ceejay.challengetime.challenge;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.Distance;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class RunChallenge extends Challenge {

    LatLng startLocation;
    LatLng stopLocation;
    PolylineOptions track = new PolylineOptions();

    private Circle startArea;
    private Circle stopArea;

    public RunChallenge( ArrayList<LatLng> track ) {
        super(track.get(0));
        this.track.addAll(track);
        startLocation = track.get(0);
        stopLocation = track.get(track.size() - 1);
    }


    @Override
    public void focus() {
        super.focus();
        if(isStarted) {
            startArea = ChallengeAdapter.getMapManager().addArea(startLocation, sizeStartArea, context.getResources().getColor(R.color.started));
        }else if(isActivated){
            startArea = ChallengeAdapter.getMapManager().addArea(startLocation, sizeStartArea, context.getResources().getColor(R.color.activated));
        }else{
            startArea = ChallengeAdapter.getMapManager().addArea(startLocation, sizeStartArea, context.getResources().getColor(R.color.notstarted));
        }
        if(isFinished) {
            stopArea = ChallengeAdapter.getMapManager().addArea(stopLocation, sizeStopArea, context.getResources().getColor(R.color.finished));
        }else{
            stopArea = ChallengeAdapter.getMapManager().addArea(stopLocation, sizeStopArea, context.getResources().getColor(R.color.notfinished));
        }
        if(track.getPoints().size() > 0) {
            ChallengeAdapter.getMapManager().addPolyline(track);
        }
    }

    @Override
    public void activate() {
        super.activate();
        if(isActivated){
            startArea.setFillColor(context.getResources().getColor(R.color.activated));
        }
    }

    @Override
    protected void start() {
        super.start();
        startArea.setFillColor(context.getResources().getColor(R.color.started));
    }

    @Override
    public void finish() {
        if( Distance.between(userLocation,stopLocation) < sizeStopArea ) {
            stopArea.setFillColor(context.getResources().getColor(R.color.finished));
            super.finish();
        }
    }
}




