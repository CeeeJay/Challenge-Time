package com.ceejay.challengetime.challenge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 04.05.2015 for Challenge Time.
 */
public class Int {
    public final static String TAG = Int.class.getSimpleName();

    private Integer value;
    private String name;

    public Int(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public View getListView( LayoutInflater inflater , ViewGroup container){
        View boolView = inflater.inflate(R.layout.var_list_item, container, false);
        ((Button)boolView.findViewById(R.id.var_type)).setText("I");
        if(name != null){
            ((TextView)boolView.findViewById(R.id.var_name)).setText(name);
        }
        ((Button)boolView.findViewById(R.id.var_worth)).setText(value+"");
        return boolView;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}




