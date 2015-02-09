package com.ceejay.challengetime.launcher;

import android.app.Activity;
import android.os.Bundle;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 09.02.2015 for Challenge Time.
 */
public class LauncherActivity extends Activity {
    public final static String TAG = LauncherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
    }

}




