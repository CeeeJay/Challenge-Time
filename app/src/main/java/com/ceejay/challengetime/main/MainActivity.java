package com.ceejay.challengetime.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.User;
import com.ceejay.challengetime.challenge.ChallengeLoader;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.geo.LocationObserver;
import com.ceejay.challengetime.geo.MainSlider;
import com.ceejay.challengetime.geo.MapManager;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.facebook.AppEventsLogger;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static final String TAG = MainActivity.class.getSimpleName();

    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    private GoogleMap googleMap;
    public MainSlider slider;

    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setUser();
        User.addUserDataChangedListener(new User.UserDataChangedListener() {
            @Override
            public void onChange() {
                setUser();
            }
        });

        new ChallengeLoader();

        context = this;
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        setUpMapIfNeeded();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ChallengeAdapter.refreshMapManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
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
                        slider.clearChallengeEquipment();
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
                    slider.clearChallengeEquipment();
                }
            }
        }else{
            super.onBackPressed();
        }
    }

    private void setUser(){
        ((TextView)findViewById(R.id.userName)).setText(User.name);
    }

    private void setUpMapIfNeeded() {

        slider = (MainSlider) findViewById(R.id.slidingDrawer);
        slider.attachButton( new OptionButton(this) );

        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            MapManager mapManager = new MapManager( this , googleMap );
            ChallengeAdapter.setMapManager( mapManager );
            slider.onMarkerFocus(mapManager);
            new LocationObserver(this);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                try {
                    Class classe = Class.forName("com.ceejay.challengetime.editor.EditorActivity");
                    Intent intent = new Intent(this, classe);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
        }

    }


}

