package com.ceejay.challengetime.editor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 07.05.2015 for Challenge Time.
 */
public class Editor extends Activity{
    public final static String TAG = Editor.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new EditorAdapter(this));
    }
}




