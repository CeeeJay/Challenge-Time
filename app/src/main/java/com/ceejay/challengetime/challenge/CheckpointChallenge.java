package com.ceejay.challengetime.challenge;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.helper.Distance;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 27.01.2015 for Challenge Time.
 *
 */
public class CheckpointChallenge extends Challenge {

    protected int sizeCheckpointArea = 40;
    private int whichCheckpoint = 0;
    private ArrayList<LatLng> checkpoints;
    private ArrayList<Circle> circles;

    public CheckpointChallenge(ArrayList<LatLng> checkpoints) {
        super(checkpoints.get(0) , "Untitled Checkpoint Challenge");
        this.checkpoints = checkpoints;
        circles = new ArrayList<>();
    }
    public CheckpointChallenge( ArrayList<LatLng> checkpoints , String challengeName ) {
        super(checkpoints.get(0) , challengeName);
        this.checkpoints = checkpoints;
        circles = new ArrayList<>();
    }

    @Override
    public void show() {
        super.show();
        circles.clear();
        ChallengeAdapter.getMapManager().challangeLayer.clear();
        for( LatLng checkpoint : checkpoints ){
            if( whichCheckpoint < checkpoints.indexOf(checkpoint)) {
                circles.add(ChallengeAdapter.getMapManager().addArea(checkpoint, sizeStartArea, context.getResources().getColor(R.color.focused)));
            }else{
                circles.add(ChallengeAdapter.getMapManager().addArea(checkpoint, sizeStartArea, context.getResources().getColor(R.color.checked)));
            }
        }
        if(challengeState.isStarted()) {
            circles.get(0).setFillColor(context.getResources().getColor(R.color.started));
        }else if(challengeState.isActivated()){
            circles.get(0).setFillColor(context.getResources().getColor(R.color.activated));
        }else{
            circles.get(0).setFillColor(context.getResources().getColor(R.color.notstarted));
        }
        if(challengeState.isFinished()){
            circles.get(circles.size()-1).setFillColor(context.getResources().getColor(R.color.finished));
        }else{
            circles.get(circles.size()-1).setFillColor(context.getResources().getColor(R.color.notfinished));
        }
    }
    @Override
    public void userLocationChanged() {
        switch (challengeState){
            case isStarted:
                if(whichCheckpoint < checkpoints.size() - 1) {
                    if( Distance.between(userLocation, checkpoints.get(whichCheckpoint)) < sizeCheckpointArea) {
                        circles.get(whichCheckpoint).setFillColor(context.getResources().getColor(R.color.checked));
                        whichCheckpoint++;
                    }
                }else if(whichCheckpoint == checkpoints.size() - 1){
                    if ( Distance.between(userLocation, checkpoints.get(checkpoints.size() - 1)) < sizeCheckpointArea) {
                        finish();
                    }
                }
                break;
            case isActivated:
                if ( Distance.between(userLocation,checkpoints.get(0)) > sizeStartArea ) {
                    start();
                }
                break;
            case isShown:
                if ( Distance.between(userLocation,checkpoints.get(0)) < sizeStartArea ) {
                    ready();
                }
                break;
        }
    }
    @Override
    public void activate() {
        super.activate();
        if( challengeState.isActivated() ){
            circles.get(0).setFillColor(context.getResources().getColor(R.color.activated));
        }
    }
    @Override
    public void start() {
        circles.get(0).setFillColor(context.getResources().getColor(R.color.started));
        whichCheckpoint = 1;
    }
    @Override
    public void finish() {
        circles.get(whichCheckpoint).setFillColor(context.getResources().getColor(R.color.finished));
        super.finish();
    }
    @Override
    public void stop() {
        super.stop();
        whichCheckpoint = 0;
    }
}




