package com.ceejay.challengetime.main;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.ceejay.challengetime.geo.MainSlider;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    private GoogleMap googleMap;
    public MainSlider slider2;

    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        context = this;
    }
}

