package com.ceejay.challengetime.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.helper.HttpPostContact;
import com.ceejay.challengetime.main.MainActivity;
import com.facebook.AppEventsLogger;

/**
 * Created by CJay on 09.02.2015 for Challenge Time.
 */
public class LauncherActivity extends FragmentActivity implements Runnable{
    public final static String TAG = LauncherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);

        findViewById(R.id.launcherIcon).startAnimation(AnimationUtils.loadAnimation(this, R.anim.launch_animation));
        findViewById(R.id.launcherIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.launcherIcon).startAnimation(AnimationUtils.loadAnimation(LauncherActivity.this, R.anim.spin));
                Thread thread = new Thread(LauncherActivity.this);
                thread.start();
                receiveData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void run() {
        Challenge.setContext(this);
        try {
            HttpPostContact.reciveChallanges(ChallengeAdapter.challenges);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveData(){
        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        finish();
    }
}




