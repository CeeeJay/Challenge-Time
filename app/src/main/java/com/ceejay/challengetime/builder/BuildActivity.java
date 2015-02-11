package com.ceejay.challengetime.builder;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.Transferor;
import com.ceejay.challengetime.helper.slider.Slider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class BuildActivity extends FragmentActivity{
    public final static String TAG = BuildActivity.class.getSimpleName();

    private GoogleMap googleMap;
    public Slider slider;
    public Button button;
    public BuilderSliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transferor.context = this;
        Challenge.setContext(this);

        setContentView(R.layout.builder_activity);
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpMapIfNeeded() {
        slider = (Slider) findViewById(R.id.slidingDrawer);

        sliderAdapter = new BuilderSliderAdapter( BuildActivity.this , slider );
        sliderAdapter.attachButton( new OptionButton(this) );

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                new BuilderMapManager( this , googleMap );
            }
        }
    }
}
