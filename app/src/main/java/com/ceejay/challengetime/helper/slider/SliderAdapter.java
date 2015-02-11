package com.ceejay.challengetime.helper.slider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ceejay.challengetime.R;

import java.util.ArrayList;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class SliderAdapter implements Slider.PanelSlideListener{
    public final static String TAG = SliderAdapter.class.getSimpleName();

    public Context context;
    public Slider slider;
    public ArrayList<Button> buttons = new ArrayList<>();

    public SliderAdapter(Context context, Slider slider) {
        this.context = context;
        this.slider = slider;
        slider.setPanelSlideListener(this);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        setUpButtons(panel.getTop());
    }

    @Override
    public void onPanelCollapsed(View panel) {}

    @Override
    public void onPanelExpanded(View panel) {}

    @Override
    public void onPanelAnchored(View panel) {}

    @Override
    public void onPanelHidden(View panel) {}

    public void attachButton(Button button){
        if ( buttons != null && button != null ) {
            buttons.add(button);
        }
    }


    public void setUpButtons( int offset ){
        for ( Button button : buttons ) {
            ((ViewGroup.MarginLayoutParams) button.getLayoutParams()).setMargins(
                    0,
                    0,
                    (int) (context.getResources().getDimension(R.dimen.start_margin_end)),
                    slider.getHeight() - offset - button.getHeight() / 2
            );
            button.requestLayout();

        }
    }
}




