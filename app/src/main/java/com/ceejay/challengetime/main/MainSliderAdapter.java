package com.ceejay.challengetime.main;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.helper.Transferor;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.helper.slider.SliderAdapter;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class MainSliderAdapter extends SliderAdapter{
    public final static String TAG = MainSliderAdapter.class.getSimpleName();

    public static ButtonMode buttonMode = ButtonMode.REFRESH;

    public enum ButtonMode{
        REFRESH,WATCH,LOCATION,ACTIVATE,STOP
    }

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
                            changeButtonMode(ButtonMode.LOCATION);
                            break;
                        case isReady:
                            changeButtonMode(ButtonMode.ACTIVATE);
                            break;
                        case isActivated:
                            changeButtonMode(ButtonMode.STOP);
                            break;
                        case isStopped:
                            changeButtonMode(ButtonMode.ACTIVATE);
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

    public void attachButton(Button button){
        super.attachButton(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch (buttonMode) {
                case WATCH:
                    if(Challenge.getFocus() != null){
                        Challenge.getFocus().show();
                        changeButtonMode( ButtonMode.LOCATION );
                    }
                    break;
                case LOCATION:
                    slider.smoothSlideTo(0,2);
                    ChallengeAdapter.getMapManager().zoom(Challenge.getUserLatLng());
                    break;
                case ACTIVATE:
                    if(Challenge.getFocus() != null) {
                        Challenge.getFocus().activate();
                    }
                    break;
                case STOP:
                    if(Challenge.getFocus() != null) {
                        Challenge.getFocus().stop();
                        changeButtonMode( ButtonMode.LOCATION);
                    }
                    break;
            }

            }
        });
        changeButtonMode( buttonMode );
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
        changeButtonMode( ButtonMode.REFRESH );
        slider.setTouchEnabled(false);
        slider.smoothSlideTo(0,2);
    }

    public void initChallengeEquipment(){
        changeButtonMode( ButtonMode.WATCH );
        slider.setTouchEnabled(true);
    }

    public void changeButtonMode( ButtonMode buttonMode ){
        if(buttons.size() > 0) {
            changeButtonMode(buttons.get(0), buttonMode);
        }
    }

    public void changeButtonMode( Button button , ButtonMode bM ){
        button.clearAnimation();
        switch (bM) {
            case REFRESH:
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context, R.anim.refresh_rotate));
                button.setBackground(context.getResources().getDrawable(R.drawable.refresh_button));
                break;
            case WATCH:
                button.setBackground(context.getResources().getDrawable(R.drawable.watch_button));
                break;
            case LOCATION:
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context, R.anim.location_rotate));
                button.setBackground(context.getResources().getDrawable(R.drawable.location_button));
                break;
            case ACTIVATE:
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context, R.anim.zoom_blink));
                button.setBackground(context.getResources().getDrawable(R.drawable.activate_button));
                break;
            case STOP:
                button.setBackground(context.getResources().getDrawable(R.drawable.stop_button));
                break;
        }
        buttonMode = bM;
    }
}




