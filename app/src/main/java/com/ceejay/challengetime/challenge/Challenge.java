package com.ceejay.challengetime.challenge;

import android.graphics.Color;
import android.util.Log;

import com.ceejay.challengetime.geo.MapManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class Challenge{
    public final static String TAG = Challenge.class.getSimpleName();

    public String name ="Untitled";
    public LatLng position;
    public String publisher;
    public int publish_time;
    public Dictionary dictionary = new Dictionary();
    public ArrayList<Trigger> triggers = new ArrayList<>();
    public HashMap<String,Timer> timer = new HashMap<>();
    public HashMap<String,Area> areas = new HashMap<>();
    public HashMap<String,Function> functions = new HashMap<>();
    public HashMap<String,Holder<Integer>> integers = new HashMap<>();
    public HashMap<String,Holder<String>> strings = new HashMap<>();
    public HashMap<String,Holder<Boolean>> booleans = new HashMap<>();


    private Thread thread;
    private boolean isRunning = false;
    private long startTime = 0;

    public Challenge(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRunning) {
                        startTime = System.currentTimeMillis();
                        for( Trigger trigger : Challenge.this.triggers ){
                            trigger.execute();
                        }
                        Log.i(TAG, Challenge.this.getInteger("smaller") + "");
                        Thread.sleep(startTime - System.currentTimeMillis() + 5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addTranslate( String key , Translate value ){
        dictionary.addTranslate(key, value);
    }
    public void addTimer( String key , Timer value ){
        timer.put( key , value );
    }
    public void addInteger( String key , Integer integer ){
        this.integers.put(key, new Holder<>(integer) );
    }
    public void addString( String key , String string ){
        this.strings.put(key, new Holder<>(string) );
    }
    public void addBoolean( String key , Boolean bool ){
        this.booleans.put(key, new Holder<>(bool));
    }
    public void addArea( String key , Area value ){
        areas.put( key , value );
    }
    public void addFunction( String key , Function value ){
        functions.put( key , value );
    }
    public void addTrigger( Trigger value ){
        triggers.add( value );
    }

    public String getTranslate( String name ) {
        return dictionary.getTranslate(name, "de");
    }
    public Boolean getBoolean( String name ) {
        return booleans.get( name ).getValue();
    }
    public Integer getInteger( String name ) {
        return integers.get( name ).getValue();
    }
    public String getString( String name ) {
        return strings.get( name ).getValue();
    }
    public Function getFunction( String name ) {
        return functions.get( name );
    }

    public Holder<Boolean> getBooleanHolder( String name ) {
        return booleans.get( name );
    }
    public Holder<Integer> getIntegerHolder( String name ) {
        return integers.get( name );
    }
    public Holder<String> getStringHolder( String name ) {
        return strings.get( name );
    }

    public void show(){

        for( Area area : areas.values() ) {
            MapManager.addArea(area.position,area.size/2, Color.parseColor(area.color));
        }
    }

    public void start(){
        isRunning = true;
        thread.start();
    }

    public void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}



/*public void setDictionary( Dictionary dictionary ){
        this.dictionary = dictionary;
    }*/
