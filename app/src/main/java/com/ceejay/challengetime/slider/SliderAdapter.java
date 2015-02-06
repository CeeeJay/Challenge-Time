package com.ceejay.challengetime.slider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ceejay.challengetime.MapManager;
import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class SliderAdapter {
    public final static String TAG = SliderAdapter.class.getSimpleName();

    public Context context;
    public Slider slider;
    public Button button;

    public enum ButtonMode{
        WATCH,ACTIVATE
    }

    public SliderAdapter( Context context , Slider slider) {
        this.context = context;
        this.slider = slider;
        slider.setMaxTopPosition((int)(context.getResources().getDimension(R.dimen.map_header)));
        slider.setPanelSlideListener(new Slider.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if(button != null) {
                    setUpButton(panel.getTop());
                }
            }
            @Override
            public void onPanelExpanded(View panel) {}
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
            public void onPanelHidden(View panel) {}
        });
    }

    public void addButton( Button button ){
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
                            changeButtonMode(ButtonMode.ACTIVATE);
                        }
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
    ButtonMode buttonMode = ButtonMode.WATCH;
    public void changeButtonMode( ButtonMode buttonMode ){
        this.buttonMode = buttonMode;
        switch (buttonMode) {
            case WATCH:
                button.setBackground(context.getResources().getDrawable(R.drawable.watch_button));
                break;
            case ACTIVATE:
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




