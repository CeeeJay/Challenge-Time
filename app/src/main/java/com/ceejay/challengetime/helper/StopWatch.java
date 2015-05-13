package com.ceejay.challengetime.helper;

import android.content.Context;
import android.os.Vibrator;

import com.ceejay.challengetime.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class StopWatch {

    private long startTime;
    private long currentTime;
    private static Thread thread;
    private boolean isClockRunning = false;
    private ArrayList<Ticker> tickers;
    private Vibrator vibrator;
    private int startVibrate = 0;
    private int pauseVibrate = 0;
    private int stopVibrate = 0;

    public StopWatch() {
        tickers = new ArrayList<>();
    }

    private void startClock(){
        if( thread == null ) {
            isClockRunning = true;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isClockRunning) {
                        currentTime = System.currentTimeMillis() - startTime;
                        for(Ticker ticker : tickers) {
                            if (ticker != null) {
                                ticker.tick(currentTime);
                            }
                        }
                        try {
                            Thread.sleep(1);
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
        startTime = System.currentTimeMillis() - time;
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
        tickers.clear();
        vibrate(stopVibrate);
    }

    public void vibrate( int time ){
        if(vibrator== null){
            //vibrator = (Vibrator) MainActivity.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        if(vibrator != null){
            vibrator.vibrate(time);
        }
    }

    public long getTime(){
        return currentTime;
    }
    public long getStartTime(){
        return startTime;
    }
    public boolean isClockRunning(){
        return isClockRunning;
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

    public void addTicker( Ticker ticker ){
        tickers.add(ticker);
    }

    public interface Ticker{
        public void tick( long time );
    }

}




