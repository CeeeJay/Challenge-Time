package com.ceejay.challengetime.editor.CustomEditor.Loop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ceejay.challengetime.R;

import java.util.ArrayList;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class EffectAdapter extends ArrayAdapter{
    public final static String TAG = EffectAdapter.class.getSimpleName();

    private ArrayList<String> lines;

    public EffectAdapter(Context context,ArrayList<String> lines) {
        super(context, R.layout.trigger_list_item);
        this.lines = lines;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view = super.getView(position, convertView, parent);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.trigger_list_item,parent,false);
        ((TextView) view.findViewById(R.id.name)).setText(lines.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return lines.size();
    }
}




