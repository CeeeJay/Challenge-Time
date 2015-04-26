package com.ceejay.challengetime.challenge;

import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Timer implements Runnable {
    public final static String TAG = Timer.class.getSimpleName();

    public long startTime;
    public long currentTime;
    public boolean reverse = false;
    private static Thread thread;
    private boolean isClockRunning = false;

    private ArrayList<Ticker> tickers;

    public Timer() {
        tickers = new ArrayList<>();
    }

    @Override
    public void run() {
        while (isClockRunning) {
            try {
                currentTime = System.currentTimeMillis() - startTime;
                for(Ticker ticker : tickers) {
                    if (ticker != null) {
                        ticker.tick(currentTime);
                    }
                }

                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTime( long time ){
        currentTime = time;
    }

    public void start(){
        if( thread == null ) {
            startTime = System.currentTimeMillis();
            isClockRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop(){
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

    public long getTime(){
        return currentTime;
    }


    public void addTicker( Ticker ticker ){
        tickers.add(ticker);
    }

    public interface Ticker{
        public void tick( long time );
    }

    @Override
    public String toString() {
        String returner = "";

        String milliSeconds = (currentTime / 10 ) % 100 + "";
        String seconds = ( currentTime / 1000 ) % 60 + "";
        String minutes = ( currentTime / 60000 ) % 60 + "";
        String hours = ( currentTime / 3600000 ) % 24 + "";

        if( !hours.equals("0") ) {
            if (hours.length() == 1) {
                returner += "0" + hours + ":";
            }else{
                returner += hours + ":";
            }
        }

        if( !(minutes.equals("0") && hours.equals("0")) ) {
            if (minutes.length() == 1) {
                returner += "0" + minutes + ":";
            }else{
                returner += minutes + ":";
            }
        }

        if( !(seconds.equals("0") && minutes.equals("0") && hours.equals("0")) ) {
            if (seconds.length() == 1) {
                returner += "0" + seconds + ":";
            }else{
                returner += seconds + ":";
            }
        }

        if (milliSeconds.length() == 1) {
            returner += "0" + milliSeconds;
        }else{
            returner += milliSeconds;
        }

        return returner;

    }
}




