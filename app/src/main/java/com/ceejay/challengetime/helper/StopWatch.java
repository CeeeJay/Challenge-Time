package com.ceejay.challengetime.helper;

/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class StopWatch {

    private long startTime;
    private long currentTime;
    private static Thread thread;
    private boolean isClockRunning = false;

    private void startClock(){
        if( thread == null ) {
            isClockRunning = true;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isClockRunning) {
                        currentTime = System.currentTimeMillis() - startTime;
                        try {
                            thread.sleep(1);
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

    public StopWatch() {
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
    }
    public void pause(){
        stopClock();
    }
    public void stop(){
        stopClock();
        startTime = 0;
        currentTime = 0;
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





}




