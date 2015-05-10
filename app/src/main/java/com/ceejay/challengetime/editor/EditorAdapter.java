package com.ceejay.challengetime.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.editor.CustomEditor.CustomEditor;

/**
 * Created by CJay on 07.05.2015 for Challenge Time.
 */
public class EditorAdapter extends ArrayAdapter {
    public final static String TAG = EditorAdapter.class.getSimpleName();
    public final String[] array;


    public EditorAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        array = getContext().getResources().getStringArray(R.array.builder_types);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.grid_view_item, parent, false);
        ((TextView)view.findViewById(R.id.grid_item_name)).setText(array[position]);
        return view;
    }

    @Override
    public int getCount() {
        return array.length;
    }
}




