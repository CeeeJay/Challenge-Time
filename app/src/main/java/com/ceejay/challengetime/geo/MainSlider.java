package com.ceejay.challengetime.geo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.OptionButtonMode;
import com.ceejay.challengetime.helper.slider.Slider;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class MainSlider extends Slider implements Slider.PanelSlideListener{
    public final static String TAG = MainSlider.class.getSimpleName();

    public MainSlider( Context context ) {
        super(context);
        init(context);
    }

    public MainSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init( Context context ){
        setMaxTopPosition((int) (context.getResources().getDimension(R.dimen.map_header)));
        //slider.setTouchEnabled(false);
        setPanelSlideListener(this);
        /*Challenge.addOnFocusChangeListener(new Challenge.OnFocusChangeListener() {

            @Override
            public void onFocusChange(Challenge focus) {
            if(focus != null) {
                focus.addOnChallengeStateChangeListener(new Challenge.OnChallengeStateChangeListener() {
                    @Override
                    public void onStateChange(Challenge.ChallengeState challengeState) {
                    Log.i(TAG, challengeState.toString());
                    switch (challengeState) {
                        case isFocused:
                            changeButtonMode(OptionButtonMode.WATCH);
                            break;
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
        });*/
    }

    @Override
    public void onPanelCollapsed(View panel) {
        /*if (ChallengeAdapter.getMapManager() != null) {
            ChallengeAdapter.getMapManager()
                    .unLock();
        }*/
    }

    @Override
    public void onPanelAnchored(View panel) {
       /* if (ChallengeAdapter.getMapManager() != null) {
            if (Challenge.getFocus() != null) {
                ChallengeAdapter.getMapManager()
                        .zoom(Challenge.getFocus().getMarker())
                        .lock();
            }
        }*/
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {}

    @Override
    public void onPanelExpanded(View panel) {}

    @Override
    public void onPanelHidden(View panel) {}

    public void attachButton( final OptionButton button ){
      /*  super.attachView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch (button.getButtonMode()) {
                case REFRESH:
                    changeButtonMode( OptionButtonMode.REFRESH_LOAD );
                    break;
                case REFRESH_LOAD:
                    changeButtonMode( OptionButtonMode.REFRESH );
                    break;
                case WATCH:
                    if(Challenge.getFocus() != null){
                        Challenge.getFocus().show();
                        changeButtonMode( OptionButtonMode.LOCATION );
                    }
                    break;
                case LOCATION:
                    if(Challenge.getUserLatLng() != null) {
                        smoothSlideTo(0, 2);
                        ChallengeAdapter.getMapManager().zoom( Challenge.getUserLatLng() );
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
        changeButtonMode( button.getButtonMode() );*/
    }

    public void onMarkerFocus( MapManager mapManager ){
       /* mapManager.addOnMarkerFocusChangeListener(new MapManager.OnMarkerFocusChangeListener() {
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
        });*/
    }

    public void clearChallengeEquipment(){
        changeButtonMode( OptionButtonMode.REFRESH );
        setTouchEnabled(false);
        smoothSlideTo(0, 2);
    }

    public void initChallengeEquipment(){
        setTouchEnabled(true);
    }

    public void changeButtonMode( OptionButtonMode buttonMode ){
        if(attachers.size() > 0) {
            changeButtonMode((OptionButton) (attachers.get(0).getView()), buttonMode);
        }
    }

    public void changeButtonMode( OptionButton button , OptionButtonMode bM ){
        button.clearAnimation();
        button.changeType(bM);
    }
}




