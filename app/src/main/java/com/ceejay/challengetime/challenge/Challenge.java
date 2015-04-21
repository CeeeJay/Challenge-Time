package com.ceejay.challengetime.challenge;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class Challenge implements Runnable {
    public final static String TAG = Challenge.class.getSimpleName();

    public String name ="Untitled";
    public String publisher;
    public int publish_time;
    public Dictionary dictionary = new Dictionary();
    public HashMap<String,Timer> timer = new HashMap<>();
    public HashMap<String,Area> area = new HashMap<>();
    public HashMap<String,Function> functions = new HashMap<>();
    public ArrayList<Trigger> trigger = new ArrayList<>();

    public Thread thread;

    public Challenge() {
        thread = new Thread(this);
    }

    public void addTranslate( String key , Translate value ){
        dictionary.addTranslate(key,value);
    }

    public void addTimer( String key , Timer value ){
        timer.put( key , value );
    }

    public void addArea( String key , Area value ){
        area.put( key , value );
    }

    public void addFunction( String key , Function value ){
        functions.put( key , value );
    }

    public void addTrigger( Trigger value ){
        trigger.add( value );
    }

    public void start(){
        thread.start();
    }

    @Override
    public void run(){
        try {
            while (true){

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




