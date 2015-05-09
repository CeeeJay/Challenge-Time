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
 * Created by CJay on 05.05.2015 for Challenge Time.
 */
public class GeometryAdapter extends ArrayAdapter {
    public final static String TAG = GeometryAdapter.class.getSimpleName();

    public Challenge challenge;
    public boolean areaExpanded = true;

    public GeometryAdapter(Context context, Challenge challenge) {
        super(context, android.R.layout.simple_list_item_1, new String[0]);
        this.challenge = challenge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if( challenge.areas.size() > position  && areaExpanded ) {
            View view = new ArrayList<>( challenge.areas.values()).get(position).getListView(inflater, parent);
            view.findViewById(R.id.type_background).setBackground(getContext().getResources().getDrawable(R.drawable.area_type_background));
            return view;

        }
        return null;
    }

    @Override
    public int getCount() {
        return
                (areaExpanded ? challenge.areas.size() : 0) +
                challenge.polygons.size() +
                challenge.polylines.size();
    }
}




