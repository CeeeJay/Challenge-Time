package com.ceejay.challengetime.challenge;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class Challenge2 {
    public final static String TAG = Challenge2.class.getSimpleName();

    public String name ="loli";
    public String publisher;
    public int publish_time;
    public HashMap<String,Timer> timer = new HashMap<>();
    public HashMap<String,Area> area = new HashMap<>();
    public HashMap<String,Function> functions = new HashMap<>();
    public ArrayList<Trigger> trigger = new ArrayList<>();

    public Challenge2() {

    }

    public void addTimer(){

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
}




