package com.ceejay.challengetime.editor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.RunChallenge;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.Slider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class EditorActivity extends FragmentActivity{
    public final static String TAG = EditorActivity.class.getSimpleName();

    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    private GoogleMap googleMap;
    public EditorSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.builder_activity);
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpMapIfNeeded() {
        slider = (EditorSlider) findViewById(R.id.slidingDrawer);
        //slider.attachButton( new EditorButton(this) );
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }
}
