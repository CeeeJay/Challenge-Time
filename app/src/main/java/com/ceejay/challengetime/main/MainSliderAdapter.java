package com.ceejay.challengetime.main;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.OptionButtonMode;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.helper.slider.SliderAdapter;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class MainSliderAdapter extends SliderAdapter{
    public final static String TAG = MainSliderAdapter.class.getSimpleName();

    public MainSliderAdapter(Context context, Slider slider) {
        super(context,slider);
        slider.setMaxTopPosition((int) (context.getResources().getDimension(R.dimen.map_header)));

        Challenge.addOnFocusChangeListener(new Challenge.OnFocusChangeListener() {
            @Override
            public void onFocusChange(Challenge focus) {
            if (focus != null) {
                focus.addOnChallengeStateChangeListener(new Challenge.OnChallengeStateChangeListener() {
                    @Override
                    public void onStateChange(Challenge.ChallengeState challengeState) {
                    switch (challengeState) {
                        case isShown:
                            changeButtonMode(OptionButtonMode.LOCATION);
                            break;
                        case isReady:
                            changeButtonMode(OptionButtonMode.ACTIVATE);
                            break;
                        case isActivated:
                            changeButtonMode(OptionButtonMode.STOP);
                            break;
                        case isStopped:
                            changeButtonMode(OptionButtonMode.ACTIVATE);
                    }
                    }
                });
            }
            }
        });
    }

    @Override
    public void onPanelCollapsed(View panel) {
        super.onPanelCollapsed(panel);
        if (ChallengeAdapter.getMapManager() != null) {
            ChallengeAdapter.getMapManager()
                    .unLock();
        }
    }

    @Override
    public void onPanelAnchored(View panel) {
        super.onPanelAnchored(panel);
        if (ChallengeAdapter.getMapManager() != null) {
            if (Challenge.getFocus() != null) {
                ChallengeAdapter.getMapManager()
                        .zoom(Challenge.getFocus().getMarker())
                        .lock();
            }
        }
    }

    public void attachButton(final OptionButton button){
        Log.i(TAG, slider.getWidth() + "");
        super.attachButton(button,new Point(
            1080 - (int)context.getResources().getDimension(R.dimen.option_button_margin) - (int)context.getResources().getDimension(R.dimen.option_button),
            (int)context.getResources().getDimension(R.dimen.option_button)/2)
        );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch (button.getButtonMode()) {
                case WATCH:
                    if(Challenge.getFocus() != null){
                        Challenge.getFocus().show();
                        changeButtonMode( OptionButtonMode.LOCATION );
                    }
                    break;
                case LOCATION:
                    if(Challenge.getUserLatLng() != null) {
                        slider.smoothSlideTo(0,2);
                        ChallengeAdapter.getMapManager().zoom(Challenge.getUserLatLng());
                    }
                    break;
                case ACTIVATE:
                    if(Challenge.getFocus() != null) {
                        Challenge.getFocus().activate();
                    }
                    break;
                case STOP:
                    if(Challenge.getFocus() != null) {
                        Challenge.getFocus().stop();
                        changeButtonMode( OptionButtonMode.LOCATION );
                    }
                    break;
            }

            }
        });
        changeButtonMode( button.getButtonMode() );
    }

    public void onMarkerFocus( MapManager mapManager ){
        mapManager.addOnMarkerFocusChangeListener(new MapManager.OnMarkerFocusChangeListener() {
            @Override
            public void onMarkerFocusChange(Marker marker) {
            if ( Challenge.getFocus() != null && Challenge.getFocus().getChallengeState().isFocused()) {
                if (marker == null) {
                    clearChallengeEquipment();
                } else {
                    initChallengeEquipment();
                }
            }
            }
        });
    }

    public void clearChallengeEquipment(){
        changeButtonMode( OptionButtonMode.REFRESH );
        slider.setTouchEnabled(false);
        slider.smoothSlideTo(0,2);
    }

    public void initChallengeEquipment(){
        changeButtonMode( OptionButtonMode.WATCH );
        slider.setTouchEnabled(true);
    }

    public void changeButtonMode( OptionButtonMode buttonMode ){
        if(attachers.size() > 0) {
            changeButtonMode((OptionButton)(attachers.get(0).getView()), buttonMode);
        }
    }

    public void changeButtonMode( OptionButton button , OptionButtonMode bM ){
        button.clearAnimation();
        button.changeType(bM);
    }
}




