package com.ceejay.challengetime.editor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Bool;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.challenge.Int;
import com.ceejay.challengetime.challenge.Str;
import com.ceejay.challengetime.geo.MapManager;

import java.util.ArrayList;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class VarAdapter extends ArrayAdapter {
    public final static String TAG = VarAdapter.class.getSimpleName();

    public Challenge challenge;

    public VarAdapter(Context context , Challenge challenge ) {
        super(context, android.R.layout.simple_list_item_1, new String[3]);
        this.challenge = challenge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        int pos = position;

        if(pos == 0){
            View view = inflater.inflate(R.layout.heading_list_item,parent,false);
            ((TextView)view.findViewById(R.id.text1)).setText("Bool");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    challenge.addBool(Math.random()+"",Math.round(Math.random()*2) != 0);
                    VarAdapter.this.notifyDataSetChanged();
                }
            });
            return view;
        }

        if( challenge.booleans.size() > pos - 1 ) {
            return new ArrayList<>( challenge.booleans.values()).get(pos - 1).getListView(inflater, parent);

        }
        pos -= challenge.booleans.size() + 1;

        if(pos == 0){
            View view = inflater.inflate(R.layout.heading_list_item,parent,false);
            ((TextView)view.findViewById(R.id.text1)).setText("Integer");
            return view;
        }

        if( challenge.integers.size() > pos - 1 ) {
            return  new ArrayList<>(challenge.integers.values()).get(pos - 1).getListView(inflater, parent);
        }
        pos -= challenge.integers.size() + 1;

        if(pos == 0){
            View view = inflater.inflate(R.layout.heading_list_item,parent,false);
            ((TextView)view.findViewById(R.id.text1)).setText("String");
            return view;
        }

        if( challenge.strings.size() > pos - 1 ) {
             return  new ArrayList<>(challenge.strings.values()).get(pos - 1).getListView(inflater, parent);
        }
        return null;
    }

    @Override
    public int getCount() {
        return challenge.getVarLength() + 3;
    }

}


