package com.ceejay.challengetime.challenge;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Trigger {
    public final static String TAG = Trigger.class.getSimpleName();

    public Challenge context;
    public String trigger;
    public String effect;
    public String els;

    private ArrayList<Comparator> statement = new ArrayList<>();
    private Comparator comparator;
    private Executor executor;
    private Executor executorEls;

    Pattern userAreaPattern = Pattern.compile("user#(\\S+) (<||>||->||<-) area#(\\S+)");
    Pattern triggerPattern = Pattern.compile("(?:(\\S+)#)(\\S+)(<||>||->||<-||&&||\\|\\|) (?:(\\S+)#)(\\S+)");
    Pattern pattern = Pattern.compile("(\\S+)\\s?(<||>||->||<-||&&||\\|\\|)\\s?(\\S+)");
    Pattern boolCompPattern = Pattern.compile("bool#(\\S+) (<||>||=||<=||>=) (:bool#)?(\\S+)");
    Pattern boolAllocPattern = Pattern.compile("bool#(\\S+) (:=) (:bool#)?(\\S+)");

    public Trigger( JsonReader jsonReader , Challenge context ) {
        try {
            this.context = context;
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "trigger":
                        this.trigger = jsonReader.nextString();
                        break;
                    case "effect":
                        this.effect = jsonReader.nextString();
                        break;
                    case "else":
                        this.els = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            Log.e(TAG, "Trigger cant read content", e);
        }
        if(trigger == null) {
            comparator = new Comparator("true", context);
        }else{
            comparator = new Comparator(trigger, context);
        }
        executor = new Executor(effect,context);
        if( els != null ){
            executorEls = new Executor(els , context);
        }
    }

    public void setTrigger( String trigger ){
        this.trigger = trigger;
    }

    public void execute(){
        if(comparator != null) {
            if(comparator.compare()){
                executor.execute();
            }else if(executorEls != null){
                executorEls.execute();
            }
        }


/*

        Log.i(TAG,System.currentTimeMillis()+"");
        for(String anweisung : trigger.split(";")) {
            Matcher m = triggerPattern.matcher(anweisung);
            while (m.find()) {
                Log.i(TAG,m.group(1) + ";" + m.group(2) + ";" + m.group(3) + ";" + m.group(4) + ";" + m.group(5) );
                if(m.group(1) == null){
                    if(m.group(4) == null){
                        if(m.group(3).equals("<")){
                            Log.i(TAG,Boolean.parseBoolean("2<3")+"");
                        }
                    }
                }
            }
        }
        Log.i(TAG,Parse.parseBoolean("(true && false || ( false && true ) ) && ( false )"));*/



    }

}




