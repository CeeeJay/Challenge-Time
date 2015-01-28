package com.ceejay.challengetime;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transferor.context = this;
        Challenge.setContext(this);
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
                    ChallengeAdapter.getMapManager().refreshMarker();
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
            ChallengeAdapter.getMapManager().refreshMarker();
        }
    }

    private void setUpMapIfNeeded() {

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                ChallengeAdapter.setMapManager(new MapManager( googleMap ));

            }
            HttpPostContact contact = new HttpPostContact("http://192.168.178.25/ChallengeTime/contact2.php");
            Bundle bundle = new Bundle();
            bundle.putString("method","receive_new_challenges");

            Stream.toChallenges(contact.send(bundle));
        }
    }
}

