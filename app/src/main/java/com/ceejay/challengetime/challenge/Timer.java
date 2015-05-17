package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.os.Vibrator;

import com.ceejay.challengetime.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Timer implements Runnable {
    public final static String TAG = Timer.class.getSimpleName();

    public long currentTime = 0;
    public long lastTime;
    public long sleepTime = 10;
    public long circleTime = 10;
    public boolean reverse = false;
    private static Thread thread;
    private boolean isClockRunning = false;

    private ArrayList<Ticker> tickers;

    public Timer() {
        tickers = new ArrayList<>();
    }

    @Override
    public void run() {
        lastTime = System.currentTimeMillis();
        while (isClockRunning) {
            try {
                circleTime = System.currentTimeMillis() - lastTime;
                currentTime += circleTime;
                lastTime = System.currentTimeMillis();
                Thread.sleep(Math.max(2 * sleepTime - circleTime,0));

                for(Ticker ticker : tickers) {
                    if (ticker != null) {
                        ticker.tick(currentTime);
                    }
                }
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
            currentTime = 0;
            isClockRunning = true;
            thread = new Thread(this);
            thread.start();
            Vibrator vibrator = (Vibrator) MainActivity.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
        }
    }

    public void stop(){
        if( thread != null  ) {
            try {
                isClockRunning = false;
                thread.join();
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public String parseTime() {
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
                returner += "0" + seconds + ".";
            }else{
                returner += seconds + ".";
            }
        }

        if (milliSeconds.length() == 1) {
            returner += "0" + milliSeconds;
        }else{
            returner += milliSeconds;
        }

        return returner;
    }

    @Override
    public String toString() {
        return "{" +
                "\"reverse\":" + reverse +
                '}';
    }
}




