package com.ceejay.challengetime.main;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.User;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.challenge.ChallengeLoader;
import com.ceejay.challengetime.challenge.ChallengeObserver;
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

    GoogleMap googleMap;
    MainSlider slider;

    NavigationDrawerFragment mNavigationDrawerFragment;

    ChallengeObserver challengeObserver;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setUpMapIfNeeded();

        setUser();
        User.addUserDataChangedListener(new User.UserDataChangedListener() {
            @Override
            public void onChange() {
                setUser();
            }
        });

        ChallengeAdapter.addChallenge(ChallengeLoader.load( this , "brunnen" ));
        ChallengeAdapter.addChallenge(ChallengeLoader.load( this , "brunnen2" ));

        Intent i = new Intent(this , ChallengeObserver.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);

        slider = (MainSlider) findViewById(R.id.slidingDrawer);
        slider.attachButton( new OptionButton(this) );

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

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

    private void setUser(){
        ((TextView)findViewById(R.id.userName)).setText(User.name);
    }

    private void setUpMapIfNeeded() {
        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            MapManager.setMap(this, googleMap);
            new LocationObserver(this);
        }
    }

    @Override
    public void onBackPressed() {
        if( ChallengeAdapter.focusedChallenge == null ) {
            unbindService(connection);
            super.onBackPressed();
        }else if(ChallengeAdapter.focusedChallenge.status == Challenge.Status.STARTED){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Möchtest du die Challenge abbrechen?");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ChallengeAdapter.getFocusedChallenge().stop();
                    ChallengeAdapter.focusChallenge(null);
                    MapManager.showMarkerLayer();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }else if (ChallengeAdapter.focusedChallenge.status == Challenge.Status.SHOWN) {
            ChallengeAdapter.focusChallenge(null);
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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ChallengeObserver.ChallengeBinder binder = (ChallengeObserver.ChallengeBinder) service;
            challengeObserver = binder.getService();
            isBound = true;
            ChallengeAdapter.setChallengeObserver(challengeObserver);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


}

