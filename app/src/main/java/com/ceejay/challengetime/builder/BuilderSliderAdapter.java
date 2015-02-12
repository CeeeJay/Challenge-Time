package com.ceejay.challengetime.builder;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.helper.slider.SliderAdapter;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class BuilderSliderAdapter extends SliderAdapter{
    public final static String TAG = BuilderSliderAdapter.class.getSimpleName();

    public int optionButtonWidth = (int)context.getResources().getDimension(R.dimen.option_button);
    public int optionButtonMargin = (int)context.getResources().getDimension(R.dimen.option_button_margin);

    public BuilderSliderAdapter(Context context, Slider slider) {
        super(context,slider);
        slider.setMaxTopPosition((int)(context.getResources().getDimension(R.dimen.map_header)));
        attachButton( new OptionButton(context) , 2 );
        attachButton( new OptionButton(context) , 3);
    }

    public void attachButton(OptionButton button , int test) {
        button.changeType(OptionButton.ButtonMode.STOPLOCATION);
        super.attachButton(button, new Point(
            1080 - test*optionButtonMargin - test*optionButtonWidth,
                optionButtonWidth/2
        ));
    }

    public void attachButton(OptionButton button) {
        super.attachButton(button, new Point(
                1080 - optionButtonMargin - optionButtonWidth,
                optionButtonWidth/2
        ));
    }
}




