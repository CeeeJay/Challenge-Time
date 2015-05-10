package com.ceejay.challengetime.challenge;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Trigger {
    public final static String TAG = Trigger.class.getSimpleName();

    public Challenge context;
    public String trigger;
    public String effect;
    public String els;

    public String name = "";
    public String title = "";

    private Comparator comparator;
    private Executor executor;
    private Executor executorEls;

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

    public Trigger() {
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
    }

    @Override
    public String toString() {
        return "{" +
                "\"trigger\":\"" + trigger + '"' +
                ", \"effect\":\"" + effect + '"' +
                '}';
    }
}




