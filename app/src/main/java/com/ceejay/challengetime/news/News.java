package com.ceejay.challengetime.news;

import android.os.Bundle;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.main.BaseActivity;
import com.ceejay.challengetime.main.NavigationDrawerFragment;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class News extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public final static String TAG = News.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news);

        ListView list = (ListView)findViewById(R.id.home_list);
        NewsAdapter adapter = new NewsAdapter(this);
        list.setAdapter(adapter);

        super.startNavigationDrawer();
    }
}




