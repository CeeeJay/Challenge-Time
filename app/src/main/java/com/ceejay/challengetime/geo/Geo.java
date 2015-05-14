package com.ceejay.challengetime.geo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.challenge.ChallengeObserver;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.main.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class Geo extends Fragment {
    public static final String TAG = MainActivity.class.getSimpleName();

    private MainSlider slider;
    private ChallengeObserver challengeObserver;
    private Context context;
    boolean isBound = false;
    private GoogleMap googleMap;
    public ActionBarActivity activity;

    public Geo() {
        this.context = MainActivity.getAppContext();
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        this.activity = (ActionBarActivity)activity;
        ((MainActivity)activity).setObBackPressedListener(new MainActivity.OnBackPressedListener() {
            @Override
            public boolean onBackPressed() {
                if (ChallengeAdapter.focusedChallenge == null) {
                    MainActivity.getAppContext().unbindService(connection);
                    return false;
                } else if (ChallengeAdapter.focusedChallenge.status == Challenge.Status.STARTED) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setMessage("Moechtest du die Challenge abbrechen?");
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
                } else if (ChallengeAdapter.focusedChallenge.status == Challenge.Status.SHOWN) {
                    ChallengeAdapter.focusChallenge(null);
                }
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_geo, container, false);

        Intent i = new Intent(MainActivity.getAppContext() , ChallengeObserver.class);
        MainActivity.getAppContext().bindService(i, connection, Context.BIND_AUTO_CREATE);

        slider = (MainSlider) view.findViewById(R.id.slidingDrawer);
        slider.attachButton(new OptionButton(MainActivity.getAppContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            MapManager.setMap(MainActivity.getAppContext(), googleMap);
            for (Challenge challenge : ChallengeAdapter.challenges ) {
                MapManager.addMarker(challenge);
            }
            new LocationObserver(MainActivity.getAppContext());
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

