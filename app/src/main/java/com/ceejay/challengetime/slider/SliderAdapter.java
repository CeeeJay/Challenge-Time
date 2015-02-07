package com.ceejay.challengetime.slider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.ceejay.challengetime.MapManager;
import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.helper.Transferor;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class SliderAdapter {
    public final static String TAG = SliderAdapter.class.getSimpleName();

    public Context context;
    public Slider slider;
    public Button button;

    public enum ButtonMode{
        INVISIBLE,WATCH,LOCATION,ACTIVATE
    }

    private Challenge lastFocus;
    private Challenge.OnChallengeStateChangeListener lastOnChallengeStateListener;

    public SliderAdapter( Context context , Slider slider) {
        this.context = context;
        this.slider = slider;
        slider.setMaxTopPosition((int)(context.getResources().getDimension(R.dimen.map_header)));
        slider.setPanelSlideListener(new Slider.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (button != null) {
                    setUpButton(panel.getTop());
                }
            }

            @Override
            public void onPanelExpanded(View panel) {
            }

            @Override
            public void onPanelCollapsed(View panel) {
                if (ChallengeAdapter.getMapManager() != null) {
                    ChallengeAdapter.getMapManager()
                            .unLock();
                }
            }

            @Override
            public void onPanelAnchored(View panel) {
                if (ChallengeAdapter.getMapManager() != null) {
                    if (Challenge.getFocus() != null) {
                        ChallengeAdapter.getMapManager()
                                .zoom(Challenge.getFocus().getMarker())
                                .lock();
                    }
                }
            }

            @Override
            public void onPanelHidden(View panel) {
            }
        });

        Challenge.addOnFocusChangeListener(new Challenge.OnFocusChangeListener() {
            @Override
            public void onFocusChange(Challenge focus) {
                if(lastFocus != null && lastOnChallengeStateListener != null){
                    lastFocus.removeOnChallengeStateChangeListener(lastOnChallengeStateListener);
                }
                if (focus != null) {
                    lastFocus = focus;
                    lastOnChallengeStateListener = new Challenge.OnChallengeStateChangeListener() {
                        @Override
                        public void onStateChange(Challenge.ChallengeState challengeState) {
                            Toast.makeText(Transferor.context,challengeState.toString(),Toast.LENGTH_SHORT).show();
                            switch (challengeState) {
                                case isNotReady:
                                    changeButtonMode(ButtonMode.LOCATION);
                                    break;
                                case isReady:
                                    changeButtonMode(ButtonMode.ACTIVATE);
                                    break;
                            }
                        }
                    };
                    focus.addOnChallengeStateChangeListener(lastOnChallengeStateListener);
                }
            }
        });

    }

    public void attachButton(Button button){
        this.button = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch (buttonMode) {
                case WATCH:
                    if (MapManager.focusedMarker != null) {
                        MapManager mm = ChallengeAdapter.getMapManager();

                        mm.clear();
                        mm.markerAdapter.get(MapManager.focusedMarker).focus();
                        mm.markerAdapter.clear();
                    }
                    changeButtonMode(ButtonMode.LOCATION);
                    break;
                case LOCATION:
                    break;
                case ACTIVATE:
                    if(Challenge.getFocus() != null) {
                        Challenge.getFocus().activate();
                    }
                    break;
            }

            }
        });
    }

    public void onMarkerFocus( MapManager mapManager ){
        mapManager.addOnMarkerFocusChangeListener(new MapManager.OnMarkerFocusChangeListener() {
            @Override
            public void onMarkerFocusChange(Marker marker) {
                if( marker == null ){
                    clearChallengeEquipment();
                }else{
                    initChallengeEquipment();
                }
            }
        });
    }

    public void clearChallengeEquipment(){
        changeButtonMode(ButtonMode.INVISIBLE);
        slider.setTouchEnabled(false);
        slider.smoothSlideTo(0,2);
    }

    public void initChallengeEquipment(){
        changeButtonMode(ButtonMode.WATCH);
        slider.setTouchEnabled(true);
    }

    ButtonMode buttonMode = ButtonMode.WATCH;
    public void changeButtonMode( ButtonMode buttonMode ){
        this.buttonMode = buttonMode;
        button.setVisibility(View.VISIBLE);
        button.clearAnimation();
        switch (buttonMode) {
            case INVISIBLE:
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context,R.anim.fade_out));
                button.setVisibility(View.INVISIBLE);
                break;
            case WATCH:
                button.setVisibility(View.VISIBLE);
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context,R.anim.fade_in));
                button.setBackground(context.getResources().getDrawable(R.drawable.watch_button));
                break;
            case LOCATION:
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context, R.anim.rotate));
                button.setBackground(context.getResources().getDrawable(R.drawable.location_button));
                break;
            case ACTIVATE:
                button.startAnimation(AnimationUtils.loadAnimation(Transferor.context,R.anim.zoom_blink));
                button.setBackground(context.getResources().getDrawable(R.drawable.activate_button));
                break;
        }
    }

    public void setUpButton( int offset ){
        ((ViewGroup.MarginLayoutParams) button.getLayoutParams()).setMargins(
                0,
                0,
                (int) (context.getResources().getDimension(R.dimen.start_margin_end)),
                slider.getHeight() - offset - button.getHeight()/2
        );
        button.requestLayout();
    }
}




