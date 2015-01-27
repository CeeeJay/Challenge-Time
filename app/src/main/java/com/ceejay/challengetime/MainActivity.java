package com.ceejay.challengetime;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.RunChallenge;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity {

    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transferor.context = this;
        setContentView(R.layout.activity_maps);

        Button startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Challenge.getFocus().activate();
            }
        });

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        if(Challenge.isActivated || Challenge.isStarted) {
             AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Möchtest du die Challenge abbrechen?");
            alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Challenge.getFocus().stop();
                    Challenge.setFocus(null);
                    Transferor.mapManager.refreshMarker();
                }
            });
            alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }else{
            Challenge.setFocus(null);
            Transferor.mapManager.refreshMarker();
        }
    }



    private void setUpMapIfNeeded() {

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                Transferor.mapManager = new MapManager( googleMap );
                //new LocationObserver(this);
                PolylineOptions track = new PolylineOptions();
                track.add(new LatLng(49.28722,7.11929));
                track.add(new LatLng(49.28765,7.11888));
                track.add(new LatLng(49.28785,7.11932));
                track.add(new LatLng(49.28845,7.11885));
                track.add(new LatLng(49.28854,7.11897));
                track.add(new LatLng(49.28865,7.11895));
                track.add(new LatLng(49.28897,7.11870));
                new RunChallenge( new LatLng(49.28722,7.11929) , track );


            }
        }
    }




}

 /*stopWatch.addTicker(new StopWatch.Ticker() {
                        @Override
                        public void tick(long time) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView textView = (TextView)findViewById(R.id.textView);
                                    textView.setText(stopWatch.getTime()+"");
                                }
                            });

                        }
                    });*/