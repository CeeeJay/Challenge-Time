package com.ceejay.challengetime.builder;

import android.content.Context;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.helper.slider.SliderAdapter;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class BuilderSliderAdapter extends SliderAdapter{
    public final static String TAG = BuilderSliderAdapter.class.getSimpleName();

    public BuilderSliderAdapter(Context context, Slider slider) {
        super(context,slider);
        slider.setMaxTopPosition((int)(context.getResources().getDimension(R.dimen.map_header)));

    }

}




