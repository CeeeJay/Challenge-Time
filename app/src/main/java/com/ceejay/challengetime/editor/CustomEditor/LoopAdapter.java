package com.ceejay.challengetime.editor.CustomEditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;


/**
 * Created by CJay on 05.05.2015 for Challenge Time.
 */
public class LoopAdapter extends ArrayAdapter {
    public final static String TAG = LoopAdapter.class.getSimpleName();
    public Challenge challenge;

    public LoopAdapter(Context context, Challenge challenge) {
        super(context, android.R.layout.simple_list_item_1, new String[0]);
        this.challenge = challenge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.loop_list_item,parent,false);
        ((TextView)view.findViewById(R.id.step_number)).setText(position+1+"");
        ((TextView)view.findViewById(R.id.name)).setText(challenge.triggers.get(position).name);
        ((TextView)view.findViewById(R.id.worth)).setText(challenge.triggers.get(position).title);
        return view;
    }

    @Override
    public int getCount() {
        return challenge.triggers.size();
    }
}




