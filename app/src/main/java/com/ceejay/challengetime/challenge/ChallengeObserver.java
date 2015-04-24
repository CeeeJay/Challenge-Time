package com.ceejay.challengetime.challenge;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ChallengeObserver extends Service {
    public final static String TAG = ChallengeObserver.class.getSimpleName();

    private final Binder challengeBinder = new ChallengeBinder();

    private Thread thread;
    private boolean isRunning = true;
    public Challenge challenge;

    public ChallengeObserver() {
    }


    public long startTime = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRunning) {
                        startTime = System.currentTimeMillis();
                        for( Trigger trigger : challenge.triggers ){
                            trigger.execute();
                        }
                        Log.i(TAG,challenge.getInteger("smaller")+"");
                        Thread.sleep(startTime - System.currentTimeMillis() + 5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.i(TAG, "onCreate");
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
        thread.start();
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
