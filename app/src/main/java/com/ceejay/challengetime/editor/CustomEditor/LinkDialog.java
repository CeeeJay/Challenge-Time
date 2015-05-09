package com.ceejay.challengetime.editor.CustomEditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 09.05.2015 for Challenge Time.
 */
public class LinkDialog {
    public final static String TAG = LinkDialog.class.getSimpleName();

    private AlertDialog alertDialog;

    public LinkDialog( Context context ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a Link");

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.link_grid_view, null);
        GridView gridView = (GridView)view.findViewById(R.id.link_gridView);
        gridView.setAdapter(new LinkAdapter(context));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, view.findViewById(R.id.comperator_type).getContentDescription().toString());
                alertDialog.cancel();
            }
        });
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog = builder.create();
    }

    public void show(){
        alertDialog.show();
    }
}




