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
    public HashMap<String,Holder<Integer>> integers = new HashMap<>();
    public HashMap<String,Holder<String>> strings = new HashMap<>();
    public HashMap<String,Holder<Boolean>> booleans = new HashMap<>();
    public ArrayList<Trigger> triggers = new ArrayList<>();

    public Thread thread;

    public Challenge() {
        thread = new Thread(this);
        //this.start();
    }

    public void addTranslate( String key , Translate value ){
        dictionary.addTranslate(key,value);
    }
    public void setDictionary( Dictionary dictionary ){
        this.dictionary = dictionary;
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
        area.put( key , value );
    }
    public void addFunction( String key , Function value ){
        functions.put( key , value );
    }
    public void addTrigger( Trigger value ){
        triggers.add( value );
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

    public Holder<Boolean> getBooleanHolder( String name ) {
        return booleans.get( name );
    }
    public Holder<Integer> getIntegerHolder( String name ) {
        return integers.get( name );
    }
    public Holder<String> getStringHolder( String name ) {
        return strings.get( name );
    }

    public void start(){
        thread.start();
    }

    private boolean isRunning = true;

    @Override
    public void run(){
        try {
            while (isRunning){
                for( Trigger trigger : triggers ){
                    trigger.execute();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRunning(boolean bool){
        this.isRunning = bool;
    }
}




