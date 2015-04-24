package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.os.Vibrator;

import com.ceejay.challengetime.main.MainActivity;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Timer {
    public final static String TAG = Timer.class.getSimpleName();

    public double weight = 1;
    public long startTime;
    public long currentTime;
    public boolean reverse = false;
    private static Thread thread;
    private boolean isClockRunning = false;
    private Vibrator vibrator;
    private int startVibrate = 0;
    private int pauseVibrate = 0;
    private int stopVibrate = 0;

    private void startClock(){
        if( thread == null ) {
            isClockRunning = true;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isClockRunning) {
                        try {
                            Thread.sleep(1);
                            if(reverse){
                                currentTime -= weight;
                            }else{
                                currentTime += weight;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
    }

    private void stopClock(){
        if( thread != null  ) {
            try {
                isClockRunning = false;
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    public void setTime( long time ){
        currentTime = time;
    }

    public void start(){
        if(startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        startClock();
        vibrate(startVibrate);
    }
    public void pause(){
        stopClock();
        vibrate(pauseVibrate);
    }
    public void stop(){
        stopClock();
        startTime = 0;
        currentTime = 0;
        vibrate(stopVibrate);
    }

    public void vibrate( int time ){
        if(vibrator== null){
            vibrator = (Vibrator) MainActivity.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        if(vibrator != null){
            vibrator.vibrate(time);
        }
    }
    public long getTime(){
        return currentTime;
    }

    public void setStartVibrate( int startVibrate ){
        this.startVibrate = startVibrate;
    }
    public void setStopVibrate( int stopVibrate ) {
        this.stopVibrate = stopVibrate;
    }
    public void setPauseVibrate( int pauseVibrate ) {
        this.pauseVibrate = pauseVibrate;
    }

}




