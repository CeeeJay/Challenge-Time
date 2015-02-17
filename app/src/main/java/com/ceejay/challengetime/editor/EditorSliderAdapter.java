package com.ceejay.challengetime.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.RunChallenge;
import com.ceejay.challengetime.challenge.helper.ChallengeEditor;
import com.ceejay.challengetime.helper.Layer;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.helper.slider.SliderAdapter;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class EditorSliderAdapter extends SliderAdapter{
    public final static String TAG = EditorSliderAdapter.class.getSimpleName();

    public int optionButtonWidth = (int)context.getResources().getDimension(R.dimen.option_button);
    public int optionButtonMargin = (int)context.getResources().getDimension(R.dimen.option_button_margin);
    public GoogleMap googleMap;

    private LinearLayout linearLayout;

    public EditorSliderAdapter(Context context , GoogleMap gMap , Slider slider) {
        super(context, slider);
        googleMap = gMap;
        slider.setMaxTopPosition((int) (context.getResources().getDimension(R.dimen.map_header)));

        linearLayout = (LinearLayout) ((Activity)context).findViewById(R.id.optionButtons);

        attachers.add(new Attacher(linearLayout,new Point(
                0,
                optionButtonWidth / 2
        )));

        ChallengeEditor editor;

        if(true){
            editor = new RunChallenge.Editor(context,googleMap);
        }else{
            editor = new RunChallenge.Editor(context,googleMap);
        }

        editor.setEditorLayer(new Layer(googleMap));
        attachButtons(editor.getEditorButtons());
    }

    @Override
    public void onPanelAnchored(View panel) {
        super.onPanelAnchored(panel);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    public void onPanelCollapsed(View panel) {
        super.onPanelCollapsed(panel);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
    }

    public void attachButton(EditorButton button , View.OnTouchListener listener) {
        if ( attachers != null && button != null ) {
            attachButton(button);
            button.setOnTouchListener(listener);
        }
    }

    public void attachButtons(ArrayList<EditorButton> editorButtons){
        for(EditorButton editorButton : editorButtons){
            attachButton(editorButton);
        }
    }

    public void attachButton( EditorButton button ) {
        if ( attachers != null && button != null ) {
            linearLayout.addView(button);
            ((ViewGroup.MarginLayoutParams) button.getLayoutParams()).setMargins(optionButtonMargin, 0, optionButtonMargin, 0);
            button.requestLayout();
        }
        setUpButtons(slider.getHeight() - (int)context.getResources().getDimension(R.dimen.panel_size));
    }

    @Override
    public void attachButton(OptionButton button, Point offset) {}
}




