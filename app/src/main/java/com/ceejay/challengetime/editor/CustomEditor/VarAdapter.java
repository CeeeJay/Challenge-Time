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
public class VarAdapter extends ArrayAdapter {
    public final static String TAG = VarAdapter.class.getSimpleName();

    public Challenge challenge;
    public boolean boolExpanded = true , intExpanded = true , stringExpanded = true;

    public VarAdapter(Context context , Challenge challenge ) {
        super(context, android.R.layout.simple_list_item_1, new String[0]);
        this.challenge = challenge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        int pos = position;

        if (challenge.booleans.size() > pos && boolExpanded ) {
            View view = new ArrayList<>(challenge.booleans.values()).get(pos).getListView(inflater, parent);
            view.findViewById(R.id.type_background).setBackground(getContext().getResources().getDrawable(R.drawable.bool_type_background));
            return view;
        }

        pos -= challenge.booleans.size();

        if (challenge.integers.size() > pos && intExpanded ) {
            View view = new ArrayList<>(challenge.integers.values()).get(pos).getListView(inflater, parent);
            view.findViewById(R.id.type_background).setBackground(getContext().getResources().getDrawable(R.drawable.integer_type_background));
            return view;
        }

        pos -= challenge.integers.size();

        if (challenge.strings.size() > pos && stringExpanded) {
            View view = new ArrayList<>(challenge.strings.values()).get(pos).getListView(inflater, parent);
            view.findViewById(R.id.type_background).setBackground(getContext().getResources().getDrawable(R.drawable.string_type_background));
            return view;
        }

        return null;
    }

    @Override
    public int getCount() {
        return
                (boolExpanded ? challenge.booleans.size() : 0) +
                (intExpanded ? challenge.integers.size() : 0) +
                (stringExpanded ? challenge.strings.size() : 0);
    }

}


