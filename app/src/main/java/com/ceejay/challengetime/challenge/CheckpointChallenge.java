package com.ceejay.challengetime.challenge;

import android.graphics.Color;

import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.LatLngConvert;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 27.01.2015 for Challenge Time.
 *
 */
public class CheckpointChallenge extends Challenge {

    private ArrayList<LatLng> checkpoints;

    public CheckpointChallenge(LatLng latLng , ArrayList<LatLng> checkpoints) {
        super(latLng);
        this.checkpoints = checkpoints;
        location = LatLngConvert.toLocation(checkpoints.get(0),"Start");
    }

    @Override
    public void focus() {
        super.focus();
        for( LatLng checkpoint : checkpoints ){
            Transferor.mapManager.addArea(checkpoint, sizeStartArea, Color.argb(70, 0, 255, 0));
        }
    }

    @Override
    public void finish() {

    }
}




