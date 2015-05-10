package com.ceejay.challengetime.editor.CustomEditor.Loop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 09.05.2015 for Challenge Time.
 */
public class TriggerLine {
    public final static String TAG = TriggerLine.class.getSimpleName();

    public String link;
    public String value;
    private Context context;
    private View view;

    public TriggerLine( Context context, String link , String value ) {
        this.link = link;
        this.value = value;
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.trigger_list_item, null, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.type_background);
        TextView textView = (TextView) view.findViewById(R.id.type);
        if (link.substring(0, 1).equals(")")) {
            linearLayout.setBackground(context.getResources().getDrawable(R.drawable.comperator_type_bracket_close));
            textView.setText("");
        } else {
            if (link.substring(1).equals("(")) {
                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.comperator_type_bracket));
            } else {
                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.comperator_type));
            }
            textView.setText(link.substring(0, 1));
        }

        ((TextView) view.findViewById(R.id.name)).setText(value);
    }

    public View getView() {
        view.findViewById(R.id.type_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkDialog dialog = new LinkDialog(context);
                dialog.setOnItemClickListener(new LinkDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(String content) {
                        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.type_background);
                        TextView textView = (TextView) view.findViewById(R.id.type);
                        if (content.substring(0, 1).equals(")")) {
                            linearLayout.setBackground(context.getResources().getDrawable(R.drawable.comperator_type_bracket_close));
                            textView.setText("");
                        } else {
                            if (content.substring(1).equals("(")) {
                                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.comperator_type_bracket));
                            } else {
                                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.comperator_type));
                            }
                            textView.setText(content.substring(0, 1));
                        }
                        textView.setContentDescription(content);
                        link = content;
                    }
                });
                dialog.show();
            }
        });
        view.findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (link.equals(")")) return;
                final View view1 = LayoutInflater.from(context).inflate(R.layout.singel_line_edit_text, null, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pick a Link");
                builder.setView(view1)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        value = ((EditText) view1.findViewById(R.id.edit_text1)).getText().toString();
                        ((TextView) view.findViewById(R.id.name)).setText(value);
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        return view;
    }


    @Override
    public String toString() {
        return link + " " + (!link.equals(")") ? value : "");
    }
}





