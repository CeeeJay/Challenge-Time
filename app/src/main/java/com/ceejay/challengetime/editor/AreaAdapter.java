package com.ceejay.challengetime.editor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Area;
import com.ceejay.challengetime.challenge.Challenge;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


/**
 * Created by CJay on 05.05.2015 for Challenge Time.
 */
public class AreaAdapter extends ArrayAdapter {
    public final static String TAG = AreaAdapter.class.getSimpleName();

    public Challenge challenge;
    public boolean areaExpanded = true;

    public AreaAdapter(Context context , Challenge challenge ) {
        super(context, android.R.layout.simple_list_item_1, new String[0]);
        this.challenge = challenge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if(position == 0){
            View view = inflater.inflate(R.layout.heading_list_item,parent,false);
            ((TextView)view.findViewById(R.id.header_name)).setText("Area");
            if(areaExpanded){
                ((TextView)view.findViewById(R.id.visible_list_icon)).setText(getContext().getResources().getString(R.string.triangle_down));
            }else{
                ((TextView)view.findViewById(R.id.visible_list_icon)).setText(getContext().getResources().getString(R.string.triangle_up));
            }
            view.findViewById(R.id.add_list_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Area area = new Area();
                    area.position = new LatLng(2,3);
                    area.title = "test";
                    challenge.addArea(Math.random() + "", area);
                    AreaAdapter.this.notifyDataSetChanged();
                }
            });
            view.findViewById(R.id.visible_list_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    areaExpanded = !areaExpanded;
                    AreaAdapter.this.notifyDataSetChanged();
                }
            });
            return view;
        }

        if( challenge.areas.size() > position - 1 && areaExpanded ) {
            return new ArrayList<>( challenge.areas.values()).get(position - 1).getListView(inflater, parent);
        }
        return null;
    }

    @Override
    public int getCount() {
        return
                (areaExpanded ? challenge.areas.size() : 0) +
                challenge.polygons.size() +
                challenge.polylines.size() + 1;
    }
}




