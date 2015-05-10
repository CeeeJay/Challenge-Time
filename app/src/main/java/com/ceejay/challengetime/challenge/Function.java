package com.ceejay.challengetime.challenge;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ceejay.challengetime.R;

import java.io.IOException;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Function {
    public final static String TAG = Function.class.getSimpleName();

    public Challenge context;
    public String name;
    public String trigger;
    public String effect;
    public String back;
    private Executor executor;

    public Function() {
    }

    public Function(JsonReader jsonReader , Challenge context) {
        this.context = context;
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "trigger": this.trigger = jsonReader.nextString(); break;
                    case "effect": this.effect = jsonReader.nextString(); break;
                    case "return": this.back = jsonReader.nextString(); break;
                    default: jsonReader.skipValue(); break;
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor = new Executor(effect,context);
    }

    public void call(){
        if(executor != null) {
            executor.execute();
        }
    }

    public View getListView( LayoutInflater inflater , ViewGroup container){
        View view = inflater.inflate(R.layout.list_item, container, false);
        ((TextView)view.findViewById(R.id.type)).setText("");
        if(name != null){
            ((TextView)view.findViewById(R.id.name)).setText(name);
        }
        ((TextView)view.findViewById(R.id.worth)).setText(String.valueOf(effect));
        return view;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '"' +
                ", \"trigger\":\"" + trigger + '"' +
                ", \"effect\":\"" + effect + '"' +
                '}';
    }
}




