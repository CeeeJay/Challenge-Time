package com.ceejay.challengetime.challenge;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ChallengeObserver extends Service {
    public final static String TAG = ChallengeObserver.class.getSimpleName();

    private final Binder challengeBinder = new ChallengeBinder();
    public Challenge challenge;

    public ChallengeObserver() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    public void setChallenge( Challenge challenge ){
        this.challenge = challenge;
    }
    public Challenge getChallenge( ){
        return challenge;
    }

    public void start(){
        if(challenge != null){
            challenge.start();
        }
    }

    public void stop(){
        if(challenge != null){
            challenge.close();
            challenge.stop();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return challengeBinder;
    }

    public class ChallengeBinder extends Binder{
        public ChallengeObserver getService(){
            return ChallengeObserver.this;
        }
    }
}
