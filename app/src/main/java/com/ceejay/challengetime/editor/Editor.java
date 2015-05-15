package com.ceejay.challengetime.editor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.editor.CustomEditor.CustomEditor;
import com.ceejay.challengetime.editor.RunEditor.RunEditor;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.main.MainActivity;

/**
 * Created by CJay on 07.05.2015 for Challenge Time.
 */
public class Editor extends Fragment{
    public final static String TAG = Editor.class.getSimpleName();

    public Context context;

    public Editor() {
        this.context = MainActivity.getAppContext();
    }

    @Override
    public void onAttach(final Activity activity) {
        ((MainActivity)activity).setObBackPressedListener(new MainActivity.OnBackPressedListener() {
            @Override
            public boolean onBackPressed() {
                ((MainActivity)activity).changeFragment(new Geo(),false);
                return true;
            }
        });
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor, container, false);

        GridView gridView = (GridView)view.findViewById(R.id.gridview);
        gridView.setAdapter(new EditorAdapter(context));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: ((MainActivity)context).changeFragment(new RunEditor(),true); break;
                    case 1: ((MainActivity)context).changeFragment(new CustomEditor(), true); break;
                }
            }
        });
        return view;
    }
}




