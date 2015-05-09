package com.ceejay.challengetime.editor.CustomEditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;

import java.util.ArrayList;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class FunctionAdapter extends ArrayAdapter {
    public final static String TAG = FunctionAdapter.class.getSimpleName();

    public Challenge challenge;
    public boolean boolExpanded = true , intExpanded = true , stringExpanded = true;

    public FunctionAdapter(Context context, Challenge challenge) {
        super(context, android.R.layout.simple_list_item_1, new String[0]);
        this.challenge = challenge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (challenge.functions.size() > position) {
            View view = new ArrayList<>(challenge.functions.values()).get(position).getListView(inflater, parent);
            view.findViewById(R.id.type_background).setBackground(getContext().getResources().getDrawable(R.drawable.function_type_background));
            return view;
        }

        return null;
    }

    @Override
    public int getCount() {
        return challenge.functions.size();
    }

}


