package com.ceejay.challengetime;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
        Transferor.mapManager.drawMarker();
    }

    private void setUpMapIfNeeded() {
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
