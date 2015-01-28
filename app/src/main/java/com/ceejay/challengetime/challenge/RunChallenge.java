package com.ceejay.challengetime.challenge;

import android.location.Location;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.LatLngConvert;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class RunChallenge extends Challenge {

    Location startLocation;
    Location stopLocation;
    PolylineOptions track = new PolylineOptions();

    private Circle startArea;
    private Circle stopArea;

    public RunChallenge( LatLng location , PolylineOptions track ) {
        super(location);
        this.track = track;
        setStartLocation(track.getPoints().get(0));
        setStopLocation(track.getPoints().get(track.getPoints().size()-1));
    }

    public RunChallenge( Location location ) {
        super(location);
        track.add(new LatLng(location.getLatitude(),location.getLongitude()));
        setStartLocation(new LatLng(location.getLatitude(),location.getLongitude()));
        setStopLocation(new LatLng(location.getLatitude(),location.getLongitude()));
    }

    public RunChallenge(){}

    public void setStartLocation(LatLng startLatLng) {
        this.startLocation = LatLngConvert.toLocation(startLatLng,"Start");
        this.track.add(startLatLng);
    }

    public void setStopLocation(LatLng stopLatLng) {
        this.stopLocation = LatLngConvert.toLocation(stopLatLng,"Stop");
        this.track.add(stopLatLng);
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
        ChallengeAdapter.getMapManager().addPolyline(track);
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
        if( userLocation.distanceTo(stopLocation) < sizeStopArea ) {
            stopArea.setFillColor(context.getResources().getColor(R.color.finished));
            super.finish();
        }
    }
}




