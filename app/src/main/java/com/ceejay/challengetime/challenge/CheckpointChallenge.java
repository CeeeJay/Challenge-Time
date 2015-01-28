package com.ceejay.challengetime.challenge;

import android.graphics.Color;

import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.LatLngConvert;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 27.01.2015 for Challenge Time.
 *
 */
public class CheckpointChallenge extends Challenge {

    private ArrayList<LatLng> checkpoints;
    protected int sizeCheckpointArea = 40;
    protected int whichCheckpoint = 0;
    protected ArrayList<Circle> circles;

    public CheckpointChallenge(LatLng latLng , ArrayList<LatLng> checkpoints) {
        super(latLng);
        this.checkpoints = new ArrayList<>(checkpoints);
        location = LatLngConvert.toLocation(checkpoints.get(0),"Start");
        circles = new ArrayList<>();
    }

    @Override
    public void focus() {
        super.focus();
        circles.clear();
        for( LatLng checkpoint : checkpoints ){
           circles.add(Transferor.mapManager.addArea(checkpoint, sizeStartArea, Color.argb(70, 0, 0, 255)));
        }
    }

    @Override
    public void userLocationChanged() {
        super.userLocationChanged();
        if(isActivated && isStarted){
            if( checkpoints.size() > 1 && userLocation.distanceTo(LatLngConvert.toLocation(checkpoints.get(whichCheckpoint),"Checkpoint")) < sizeCheckpointArea ){
                circles.get(whichCheckpoint).setFillColor(Color.argb(70, 0, 255, 0));
                whichCheckpoint++;
            }
        }
    }

    @Override
    public void start() {
        if ( userLocation.distanceTo(LatLngConvert.toLocation(checkpoints.get(whichCheckpoint),"StartCheckpoint")) > sizeStartArea ) {
            circles.get(0).setFillColor(Color.argb(70, 0, 255, 0));
            whichCheckpoint = 1;
        }
    }

    @Override
    public void finish() {
        if (checkpoints.size() >= whichCheckpoint - 1 && userLocation.distanceTo(LatLngConvert.toLocation(checkpoints.get(whichCheckpoint), "Checkpoint")) < sizeCheckpointArea) {
            circles.get(whichCheckpoint).setFillColor(Color.argb(70, 0, 255, 0));
            super.finish();
        }
    }

    @Override
    public void stop() {
        super.stop();
        whichCheckpoint = 0;
    }
}




