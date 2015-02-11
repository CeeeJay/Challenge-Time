package com.ceejay.challengetime.helper.slider;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ceejay.challengetime.R;

import java.util.ArrayList;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class SliderAdapter implements Slider.PanelSlideListener,View.OnClickListener{
    public final static String TAG = SliderAdapter.class.getSimpleName();

    public Context context;
    public Slider slider;
    public ArrayList<Attacher> attachers = new ArrayList<>();

    public SliderAdapter(Context context, Slider slider) {
        this.context = context;
        this.slider = slider;
        slider.setPanelSlideListener(this);
        slider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Log.i(TAG,"LOOOL");
        if( slider.getPanel().getTop() < 700 ){
            slider.setMaxTopPosition(0);
        }
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
    public void onPanelAnchored(View panel) {
    }

    @Override
    public void onPanelHidden(View panel) {}

    public void attachButton( OptionButton button , Point offset ){
        if ( attachers != null && offset != null && button != null ) {
            ((ViewGroup)slider.getParent()).addView(button);
            attachers.add(new Attacher(button,offset));
        }
    }

    public void setUpButtons( int offset ){
        for ( Attacher attacher : attachers ) {
           attacher.getOptionButton().setPosition(attacher.getOffset().x, offset - attacher.getOffset().y);
        }
    }

    protected class Attacher{

        private OptionButton optionButton;
        private Point offset;

        public Attacher(OptionButton optionButton, Point offset) {
            this.optionButton = optionButton;
            this.offset = offset;
        }

        public OptionButton getOptionButton() {
            return optionButton;
        }

        public void setOptionButton(OptionButton optionButton) {
            this.optionButton = optionButton;
        }

        public Point getOffset() {
            return offset;
        }

        public void setOffset(Point offset) {
            this.offset = offset;
        }
    }

}




