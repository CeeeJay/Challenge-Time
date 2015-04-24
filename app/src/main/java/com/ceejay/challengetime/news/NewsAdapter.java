package com.ceejay.challengetime.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class NewsAdapter extends ArrayAdapter{
    public final static String TAG = NewsAdapter.class.getSimpleName();

    View customView;

    public NewsAdapter(Context context) {
        super(context, R.layout.info_window, new String[2]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        customView = inflater.inflate(R.layout.challenge_news, parent, false);

        return customView;
    }

    @Override
    public int getCount() {
        return 2;
    }
}


