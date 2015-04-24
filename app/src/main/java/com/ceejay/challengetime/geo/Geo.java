package com.ceejay.challengetime.geo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.User;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.main.NavigationDrawerFragment;
import com.facebook.AppEventsLogger;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class Geo extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static final String TAG = Geo.class.getSimpleName();

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

        slider = (MainSlider) findViewById(R.id.slidingDrawer);
        slider.attachButton( new OptionButton(this) );

        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            MapManager mapManager = new MapManager( this , googleMap );
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



