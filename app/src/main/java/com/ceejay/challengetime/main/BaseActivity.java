package com.ceejay.challengetime.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.editor.Editor;
import com.ceejay.challengetime.generallys.Generallys;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.news.News;

/**
 * Created by CJay on 02.05.2015 for Challenge Time.
 */
public class BaseActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    public final static String TAG = BaseActivity.class.getSimpleName();

    NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    public void startNavigationDrawer(){
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0: startActivity(new Intent(this, Geo.class)); break;
            case 1: startActivity(new Intent(this, Editor.class)); return;
            case 2: startActivity(new Intent(this, Generallys.class)); break;
            case 3: startActivity(new Intent(this, News.class)); break;
            default: return;
        }
        overridePendingTransition(0, 0);
        finish();
    }

}




