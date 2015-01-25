package com.ceejay.challengetime;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
public class MainActivity extends ActionBarActivity {

    private GoogleMap mMap;

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



    boolean isRunning = false;
    long startTime = 0;
    Thread testThread;
    private void setUpMapIfNeeded() {


        Button buttonStart = (Button) findViewById(R.id.start);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Track.getStartLocation().distanceTo(Track.getUserLocation()) < 50 && !isRunning ){

                    testThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isRunning){
                                if(Track.getStopLocation().distanceTo(Track.getUserLocation()) < 50){
                                    isRunning = false;
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView textView = (TextView) findViewById(R.id.textView);
                                            textView.setText(System.currentTimeMillis() - startTime + "");
                                        }

                                    });
                                }
                            }
                        }
                    });

                    isRunning = true;
                    startTime = System.currentTimeMillis();
                    testThread.start();
                }
            }
        });

        if (mMap == null) {

            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new MyLocationManager(this,mMap);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
