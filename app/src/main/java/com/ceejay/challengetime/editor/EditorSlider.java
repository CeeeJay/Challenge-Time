package com.ceejay.challengetime.editor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.slider.Slider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class EditorSlider extends Slider implements Slider.PanelSlideListener {
    public final static String TAG = EditorSlider.class.getSimpleName();

    public GoogleMap googleMap;


    public EditorSlider(Context context) {
        super(context);
        init(context);
    }

    public EditorSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditorSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        setMaxTopPosition((int) (context.getResources().getDimension(R.dimen.map_header)));
        setPanelSlideListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        googleMap = ((SupportMapFragment) ((FragmentActivity)EditorActivity.getAppContext()).getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

       /* ChallengeEditor editor;
        if(true){
            editor = new RunChallenge.Editor(googleMap);
        }else{
            editor = new RunChallenge.Editor(googleMap);
        }

        editor.setEditorLayer(new Layer(googleMap));
        attachButtons(editor.getEditorButtons());*/

        super.onAttachedToWindow();
    }

    @Override
    public void onPanelAnchored(View panel) {
        if( googleMap != null ) {
            googleMap.getUiSettings().setAllGesturesEnabled(false);
        }
    }

    @Override
    public void onPanelCollapsed(View panel) {
        if( googleMap != null ) {
            googleMap.getUiSettings().setAllGesturesEnabled(true);
        }
    }

    @Override
    public void onPanelExpanded(View panel) {}

    @Override
    public void onPanelHidden(View panel) {}

    @Override
    public void onPanelSlide(View panel, float slideOffset) {}


    public void attachButtons(ArrayList<EditorButton> editorButtons){
        for(EditorButton editorButton : editorButtons){
           attachButton(editorButton);
        }
    }

    /*public void attachButton( EditorButton button ) {
        if ( attachers != null && button != null ) {
            ((ViewGroup)getParent()).addView(button);
            attachers.add(new Attacher(linearLayout,new Point(
                    0,
                    optionButtonWidth / 2
            )));
            ((ViewGroup.MarginLayoutParams) button.getLayoutParams()).setMargins(optionButtonMargin, 0, optionButtonMargin, 0);
            button.requestLayout();
        }
        setUpButtons(getHeight() - (int)getResources().getDimension(R.dimen.panel_size));
    }*/

    public void attachButton( final EditorButton button ) {
        super.attachView(button);
    }
}




