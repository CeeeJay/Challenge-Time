package com.ceejay.challengetime.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.helper.HttpPostContact;
import com.ceejay.challengetime.main.MainActivity;

/**
 * Created by CJay on 09.02.2015 for Challenge Time.
 */
public class LauncherActivity extends Activity implements Runnable{
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
            }
        });
    }

    @Override
    public void run() {
        Challenge.setContext(this);
        try {
            HttpPostContact.reciveChallanges(ChallengeAdapter.challenges);
            receiveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveData(){
        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        finish();
    }
}




