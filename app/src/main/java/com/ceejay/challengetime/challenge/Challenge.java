package com.ceejay.challengetime.challenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.geo.MapManager;
import com.ceejay.challengetime.main.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class Challenge implements Runnable{
    public final static String TAG = Challenge.class.getSimpleName();

    public enum Status{
        HIDDEN,SHOWN,STARTED
    }

    public Status status = Status.HIDDEN;

    public String name ="Untitled";
    public LatLng position;
    public String publisher;
    public int publish_time;
    public int interval = 1000;
    public Dictionary dictionary = new Dictionary();
    public ArrayList<Trigger> triggers = new ArrayList<>();
    public HashMap<String,Timer> timers = new HashMap<>();
    public HashMap<String,Area> areas = new HashMap<>();
    public HashMap<String,Polygon> polygons = new HashMap<>();
    public HashMap<String,Polyline> polylines = new HashMap<>();
    public HashMap<String,Function> functions = new HashMap<>();
    public HashMap<String,Int> integers = new HashMap<>();
    public HashMap<String,Str> strings = new HashMap<>();
    public HashMap<String,Bool> booleans = new HashMap<>();


    private Thread thread;
    private boolean isRunning = false;
    private long startTime = 0;

    public Challenge(){

    }

    public void addTranslate( String key , Translate value ){
        dictionary.addTranslate(key, value);
    }
    public void addTimer( String key , Timer value ){
        timers.put(key, value);
    }
    public void addInteger( String key , Integer integer ){
        this.integers.put( key, new Int(integer) );
    }
    public void addString( String key , String string ){
        this.strings.put( key, new Str(string) );
    }
    public void addBool(String key, Boolean bool){
        this.booleans.put(key, new Bool(key,bool));
    }
    public void addArea( String key , Area value ){
        areas.put( key , value );
    }
    public void addPolygon( String key , Polygon value ){
        polygons.put( key , value );
    }
    public void addPolyline( String key , Polyline value ){
        polylines.put( key , value);
    }
    public void addFunction( String key , Function value ){
        functions.put(key, value);
    }
    public void addTrigger( Trigger value ){
        triggers.add(value);
    }

    public String getTranslate( String name ) {
        return dictionary.getTranslate(name, "de");
    }
    public Bool getBool(String name) {
        return booleans.get(name);
    }
    public Timer getTimer( String name ) {
        return timers.get(name);
    }
    public Area getArea( String name ) {
        return areas.get( name );
    }
    public Polygon getPolygon( String name ) {
        return polygons.get( name );
    }
    public Polyline getPolyline( String name ) {
        return polylines.get( name );
    }
    public Function getFunction( String name ) {
        return functions.get( name );
    }
    public Int getInt(String name) {
        return integers.get( name );
    }
    public Str getStr(String name) {
        return strings.get( name );
    }

    public int getVarLength() {
        return booleans.size() + integers.size() + strings.size();
    }

    public void show(){
        for( Area area : areas.values() ) {
            area.show();
        }
        for( Polygon polygon : polygons.values() ) {
            polygon.show();
        }
        for( Polyline polyline : polylines.values() ) {
            polyline.show();
        }

        status = Status.SHOWN;
    }
    public void close(){
        for( Area area : areas.values() ) {
            area.close();
        }
    }
    public void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
        status = Status.STARTED;
    }
    public void stop(){
        isRunning = false;
        for( Timer timer : timers.values() ){
            timer.stop();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        status = Status.HIDDEN;
    }
    public void finish(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Geo.getAppContext());
        alertDialog.setMessage("Challenge abgeschlossen in" + timers.get("Stoppuhr1"));
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

        MapManager.showMarkerLayer();
        stop();
        status = Status.HIDDEN;
    }

    @Override
    public void run() {
        try {
            long sleepTime;
            while (isRunning) {
                startTime = System.currentTimeMillis();
                ((Activity) Geo.getAppContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)((Activity) Geo.getAppContext()).findViewById(R.id.challengeRecord)).setText(Challenge.this.getTimer("Stoppuhr1").toString());
                    }
                });
                for (Trigger trigger : Challenge.this.triggers) {
                    trigger.execute();
                }
                sleepTime = startTime - System.currentTimeMillis() + interval;
                if(sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
