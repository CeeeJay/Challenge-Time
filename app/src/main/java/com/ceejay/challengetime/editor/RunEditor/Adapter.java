package com.ceejay.challengetime.editor.RunEditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Area;

import java.util.ArrayList;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class Adapter extends ArrayAdapter {
    public final static String TAG = Adapter.class.getSimpleName();

    private ArrayList<Area> areas;

    public Adapter(Context context , ArrayList<Area> areas) {
        super(context, R.layout.loop_list_item);
        this.areas = areas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.loop_list_item,parent,false);
        ((TextView)view.findViewById(R.id.name)).setText(areas.get(position).title);
        ((TextView)view.findViewById(R.id.step_number)).setText(position+1+"");
        if( position == 0 ) {
            view.findViewById(R.id.loop_list_icon).setBackground(getContext().getResources().getDrawable(R.drawable.string_type_background));
        }else if( position == areas.size() - 1 ){
            view.findViewById(R.id.loop_list_icon).setBackground(getContext().getResources().getDrawable(R.drawable.integer_type_background));
        }else{
            view.findViewById(R.id.loop_list_icon).setBackground(getContext().getResources().getDrawable(R.drawable.bool_type_background));
        }

        return view;
    }

    @Override
    public int getCount() {
        return areas.size();
    }
}




