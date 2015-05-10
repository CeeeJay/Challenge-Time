package com.ceejay.challengetime.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.editor.CustomEditor.CustomEditor;
import com.ceejay.challengetime.editor.RunEditor.RunEditor;

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(Editor.this, RunEditor.class));
                        break;
                    case 1:
                        startActivity(new Intent(Editor.this, CustomEditor.class));
                        break;
                    default:
                        return;
                }
                finish();
            }
        });
    }
}




