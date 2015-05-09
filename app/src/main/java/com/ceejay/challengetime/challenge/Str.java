package com.ceejay.challengetime.challenge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 04.05.2015 for Challenge Time.
 */
public class Str {
    public final static String TAG = Str.class.getSimpleName();

    private String value;
    private String name;

    public Str(String value) {
        this.value = value;
    }
    public Str(String name , String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public View getListView( LayoutInflater inflater , ViewGroup container){
        View view = inflater.inflate(R.layout.list_item, container, false);
        ((TextView)view.findViewById(R.id.type)).setText("S");
        if(name != null){
            ((TextView)view.findViewById(R.id.name)).setText(name);
        }
        ((TextView)view.findViewById(R.id.worth)).setText(value);
        return view;
    }

    @Override
    public String toString() {
        return value;
    }
}




