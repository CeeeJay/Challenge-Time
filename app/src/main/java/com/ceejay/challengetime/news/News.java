package com.ceejay.challengetime.news;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class News extends Activity {
    public final static String TAG = News.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news);

        ListView list = (ListView)findViewById(R.id.home_list);
        NewsAdapter adapter = new NewsAdapter(this);
        list.setAdapter(adapter);
    }
}




