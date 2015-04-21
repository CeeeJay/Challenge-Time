package com.ceejay.challengetime.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.User;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeLoader;
import com.ceejay.challengetime.geo.MainSlider;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.facebook.AppEventsLogger;
import com.google.android.gms.maps.GoogleMap;

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

        Challenge challenge = ChallengeLoader.load();
        Log.i(TAG,challenge.dictionary.getTranslate("Start","fr"));

        slider = (MainSlider) findViewById(R.id.slidingDrawer);
        slider.attachButton( new OptionButton(this) );

        context = this;
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        setUpMapIfNeeded();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

    }

    private void setUser(){
        ((TextView)findViewById(R.id.userName)).setText(User.name);
    }

    private void setUpMapIfNeeded() {

       /*

        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            MapManager mapManager = new MapManager( this , googleMap );
            slider.onMarkerFocus(mapManager);
            new LocationObserver(this);
        }*/

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

