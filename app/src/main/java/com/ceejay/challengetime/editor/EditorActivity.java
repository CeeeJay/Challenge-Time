package com.ceejay.challengetime.editor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.slider.Slider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class EditorActivity extends FragmentActivity{
    public final static String TAG = EditorActivity.class.getSimpleName();

    private GoogleMap googleMap;
    public Slider slider;
    public EditorSliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.builder_activity);
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpMapIfNeeded() {
        slider = (Slider) findViewById(R.id.slidingDrawer);

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                sliderAdapter = new EditorSliderAdapter( this , googleMap , slider );
            }
        }
    }
}
