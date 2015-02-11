package com.ceejay.challengetime.main;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.helper.Transferor;
import com.ceejay.challengetime.helper.slider.Slider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private GoogleMap googleMap;
    public Slider slider;
    public Button button;
    public MainSliderAdapter mainSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transferor.context = this;
        Challenge.setContext(this);
        try {
            startActivity(new Intent(this, Class.forName("com.ceejay.challengetime.builder.BuildActivity")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(!Transferor.launched) {
            setContentView(R.layout.launcher_activity);
            findViewById(R.id.launcherIcon).startAnimation(AnimationUtils.loadAnimation(this,R.anim.launch_animation));
            findViewById(R.id.launcherIcon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_maps);
                    setUpMapIfNeeded();
                    Transferor.launched = true;
                }
            });
        }else{
            setContentView(R.layout.activity_maps);
            setUpMapIfNeeded();
        }

    }

    @Override
    public void onBackPressed() {
        if(Challenge.getFocus() != null) {
            if ( Challenge.getFocus().getChallengeState().getValence() > 3) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Möchtest du die Challenge abbrechen?");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Challenge.getFocus().stop();
                        Challenge.setFocus(null);
                        ChallengeAdapter.getMapManager().refreshMarker();
                        mainSliderAdapter.clearChallengeEquipment();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }else{
                Challenge.setFocus(null);
                if (ChallengeAdapter.getMapManager() != null) {
                    ChallengeAdapter.getMapManager().refreshMarker();
                    mainSliderAdapter.clearChallengeEquipment();
                }
            }
        }else{
            super.onBackPressed();
        }
    }

    private void setUpMapIfNeeded() {

        slider = (Slider) findViewById(R.id.slidingDrawer);
        button = (Button) findViewById(R.id.start);

        mainSliderAdapter = new MainSliderAdapter( MainActivity.this , slider );
        mainSliderAdapter.attachButton(button);

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                MapManager mapManager = new MapManager( this , googleMap );
                ChallengeAdapter.setMapManager( mapManager );
                mainSliderAdapter.onMarkerFocus( mapManager );
                new LocationObserver(this);
            }
        }
    }
}

