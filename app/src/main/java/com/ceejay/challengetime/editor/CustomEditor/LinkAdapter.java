package com.ceejay.challengetime.editor.CustomEditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 09.05.2015 for Challenge Time.
 */
public class LinkAdapter extends ArrayAdapter{
    public final static String TAG = LinkAdapter.class.getSimpleName();

    private static final String[] LinkTypes = {"U","O","X","U(","O(","X("};
    private Context context;


    public LinkAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1, new String[0]);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.link_grid_item, parent, false);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.comperator_type_background);
        if(LinkTypes[position].substring(1).equals("(")){
            linearLayout.setBackground(getContext().getResources().getDrawable(R.drawable.comperator_type_bracket));
        }else{
            linearLayout.setBackground(getContext().getResources().getDrawable(R.drawable.comperator_type));
        }
        TextView textView = (TextView)view.findViewById(R.id.comperator_type);
        textView.setText(LinkTypes[position].substring(0,1));
        textView.setContentDescription(LinkTypes[position]);
        return view;
    }

    @Override
    public int getCount() {
        return LinkTypes.length;
    }
}




