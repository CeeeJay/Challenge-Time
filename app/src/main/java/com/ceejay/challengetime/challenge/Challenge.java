package com.ceejay.challengetime.challenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.geo.MapManager;
import com.ceejay.challengetime.helper.JSONMap;
import com.ceejay.challengetime.helper.Position;
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
    public ArrayList<Trigger> triggers = new ArrayList<>();
    public JSONMap<Translate> dictionary = new JSONMap<>();
    public JSONMap<Timer> timers = new JSONMap<>();
    public JSONMap<Area> areas = new JSONMap<>();
    public JSONMap<Polygon> polygons = new JSONMap<>();
    public JSONMap<Polyline> polylines = new JSONMap<>();
    public JSONMap<Function> functions = new JSONMap<>();
    public JSONMap<Int> integers = new JSONMap<>();
    public JSONMap<Str> strings = new JSONMap<>();
    public JSONMap<Bool> booleans = new JSONMap<>();


    private Thread thread;
    private boolean isRunning = false;
    private long startTime = 0;

    public Challenge(){

    }

    public void addTranslate( String key , Translate value ){
        dictionary.put(key, value);
    }
    public void addTimer( String key , Timer value ){
        timers.put(key, value);
    }
    public void addInteger( String key , Integer integer ){
        this.integers.put( key, new Int(key,integer) );
    }
    public void addString( String key , String string ){
        this.strings.put( key, new Str(key,string) );
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
        polylines.put(key, value);
    }
    public void addFunction( String key , Function value ){
        functions.put(key, value);
    }
    public void addTrigger( Trigger value ){
        triggers.add(value);
    }

    public void addBool(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a Bool");

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView name = (TextView)view.findViewById(R.id.name);
                        TextView worth = (TextView)view.findViewById(R.id.worth);
                        addBool(name.getText().toString(),Boolean.valueOf(worth.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    public void addInteger(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a Integer");

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView name = (TextView)view.findViewById(R.id.name);
                        TextView worth = (TextView)view.findViewById(R.id.worth);
                        addInteger(name.getText().toString(), Integer.valueOf(worth.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    public void addString(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a String");

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView name = (TextView)view.findViewById(R.id.name);
                        TextView worth = (TextView)view.findViewById(R.id.worth);
                        addString(name.getText().toString(), worth.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    public void addTrigger(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a String");

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView name = (TextView)view.findViewById(R.id.name);
                        TextView worth = (TextView)view.findViewById(R.id.worth);
                        Trigger trigger = new Trigger();
                        trigger.name = name.getText().toString();
                        trigger.title = worth.getText().toString();
                        addTrigger(trigger);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public void addArea(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a String");

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog2, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView name = (TextView)view.findViewById(R.id.name);
                        TextView worth = (TextView)view.findViewById(R.id.worth);
                        addString(name.getText().toString(), worth.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public String getTranslate( String name ) {
        return dictionary.get(name).getDefinition("de");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"dictionary\":" + dictionary + ",");
        sb.append("\"position\":" + Position.toStr(position) + ",");
        sb.append("\"name\":\"" + name + "\",");
        sb.append("\"publisher\":\"" + publisher + "\",");
        sb.append("\"publish_time\":" + publish_time + ",");
        //sb.append("\"challenge_id\":" + challenge_id + ",");
        //sb.append("\"type\":" + type + ",");
        //sb.append("\"description\":" + description + ",");
        //sb.append("\"record\":" + record + ",");
        sb.append("\"variables\":{");
            sb.append("\"bool\":" + booleans + ",");
            sb.append("\"integer\":" + integers + ",");
            sb.append("\"string\":" + strings );
            //sb.append("\"timer\":" + timers );
        sb.append("},");

        sb.append("\"geometry\":{");
            sb.append("\"areas\":" + areas + ",");
            sb.append("\"polygons\":" + polygons + ",");
            sb.append("\"polylines\":" + polylines );
            //sb.append("\"timer\":" + timers );
        sb.append("},");

        sb.append("\"functions\":" + functions + ",");
        sb.append("\"loop\":" + triggers );
        sb.append("}");

        return sb.toString();
    }
}
