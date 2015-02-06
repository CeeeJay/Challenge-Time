package com.ceejay.challengetime.slider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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




