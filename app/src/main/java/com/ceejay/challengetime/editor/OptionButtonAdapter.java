package com.ceejay.challengetime.editor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ceejay.challengetime.R;



/**
 * Created by CJay on 14.02.2015 for Challenge Time.
 */

public class OptionButtonAdapter extends ArrayAdapter<String> {

    Context context;

    public OptionButtonAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1, new String[4]);
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.option_button,parent,false);
        return customView;
    }
}






