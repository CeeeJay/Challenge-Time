package com.ceejay.challengetime.main;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.helper.Transferor;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.Slider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static final String TAG = MainActivity.class.getSimpleName();

    private GoogleMap googleMap;
    public Slider slider;
    public MainSliderAdapter sliderAdapter;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Transferor.context = this;
        Challenge.setContext(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        ChallengeAdapter.getMapManager().showMarker();
                        ChallengeAdapter.getMapManager().clearChallengeLayer();
                        sliderAdapter.clearChallengeEquipment();
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
                    ChallengeAdapter.getMapManager().showMarker();
                    ChallengeAdapter.getMapManager().clearChallengeLayer();
                    sliderAdapter.clearChallengeEquipment();
                }
            }
        }else{
            super.onBackPressed();
        }
    }

    private void setUpMapIfNeeded() {

        slider = (Slider) findViewById(R.id.slidingDrawer);

        sliderAdapter = new MainSliderAdapter( MainActivity.this , slider );
        sliderAdapter.attachButton( new OptionButton(this) );

        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            MapManager mapManager = new MapManager( this , googleMap );
            ChallengeAdapter.setMapManager( mapManager );
            sliderAdapter.onMarkerFocus( mapManager );
            new LocationObserver(this);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {}


}

