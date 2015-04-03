package com.ceejay.challengetime.generallys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class GenerallysAdapter extends ArrayAdapter{
    public final static String TAG = GenerallysAdapter.class.getSimpleName();

    View customView;
    TextView message;

    public GenerallysAdapter(Context context) {
        super(context, R.layout.info_window, new String[2]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
       /* if (messages.get(position).getSendId() == App.userID) {
            customView = inflater.inflate(R.layout.send_chat_item, parent, false);
            message = (TextView) customView.findViewById(R.id.sendChatItem);
        }else{
            customView = inflater.inflate(R.layout.receive_chat_item, parent, false);
            message = (TextView) customView.findViewById(R.id.receiveChatItem);
        }
        message.setText(messages.get(position).getMessage());*/
        return customView;
    }

    @Override
    public int getCount() {
        return 0;
    }
}


