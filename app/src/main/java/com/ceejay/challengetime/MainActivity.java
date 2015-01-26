package com.ceejay.challengetime;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.RunChallenge;
import com.ceejay.challengetime.helper.StopWatch;
import com.ceejay.challengetime.views.HexagonButton;
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
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        if(!Challenge.isActivated && !Challenge.isStarted) {
            Challenge.focusedChallenge = null;
            Transferor.mapManager.refreshMarker();
        }else{
            //Dialog dialog = new Dialog(this);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Challenge.focusedChallenge = null;
                    Transferor.mapManager.refreshMarker();
                }
            });
            alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    }


    StopWatch stopWatch;
    private void setUpMapIfNeeded() {

        HexagonButton hexagonButton = (HexagonButton) findViewById(R.id.hex);
        stopWatch = new StopWatch();

        hexagonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stopWatch.isClockRunning()){
                    stopWatch.stop();
                    stopWatch.start();
                    stopWatch.addTicker(new StopWatch.Ticker() {
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
                    });
                }else{
                    stopWatch.stop();
                }

            }
        });

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                new MyLocationManager( this , googleMap );
                Transferor.mapManager = new MapManager( this , googleMap );
                PolylineOptions track = new PolylineOptions();
                track.add(new LatLng(49.28722,7.11929));
                track.add(new LatLng(49.28765,7.11888));
                track.add(new LatLng(49.28785,7.11932));
                track.add(new LatLng(49.28845,7.11885));
                track.add(new LatLng(49.28854,7.11897));
                track.add(new LatLng(49.28865,7.11895));
                track.add(new LatLng(49.28897,7.11870));
                new RunChallenge( Transferor.User , track );

                new RunChallenge( new LatLng(49.28854,7.11897) , track );


            }
        }
    }




}
