package com.ceejay.challengetime.editor.CustomEditor.Loop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by CJay on 09.05.2015 for Challenge Time.
 */
public class TriggerAdapter extends ArrayAdapter{
    public final static String TAG = TriggerAdapter.class.getSimpleName();

    private ArrayList<TriggerLine> lines;

    public TriggerAdapter(Context context,ArrayList<TriggerLine> lines) {
        super(context, android.R.layout.simple_list_item_1);
        this.lines = lines;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        return lines.get(position).getView();
    }

    @Override
    public int getCount() {
        return lines.size();
    }
}




