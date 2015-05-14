package com.ceejay.challengetime.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.Settings;
import com.ceejay.challengetime.challenge.ChallengeObserver;
import com.ceejay.challengetime.editor.Editor;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.geo.MainSlider;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends ActionBarActivity implements OnItemClickListener{

    private ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    GoogleMap googleMap;
    MainSlider slider;

    ChallengeObserver challengeObserver;
    boolean isBound = false;

    private static Context context;
    public static Context getAppContext(){
        return context;
    }
    public static Activity getActivity(){
        return (ActionBarActivity)context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        moveDrawerToTop();
        initActionBar() ;
        initDrawer();
        //Quick cheat: Add Fragment 1 to default view
        onItemClick(null, null, 0, 0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor, null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout container = (LinearLayout) drawer.findViewById(R.id.drawer_content); // This is the container we defined just now.
        container.addView(child, 0);
        drawer.findViewById(R.id.drawer).setPadding(0, getStatusBarHeight(), 0, 0);

        // Make the drawer replace the first child
        decor.addView(drawer);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getContentIdResource() {
        return getResources().getIdentifier("content", "id", "android");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerToggle.syncState();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_settings: return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(new ColorDrawable(Color.TRANSPARENT));
        setTitle("Test");
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.drawer);
        mDrawerLayout.setDrawerListener(createDrawerToggle());
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nav_items));
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
    }

    private DrawerListener createDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int state) {
            }
        };
        return mDrawerToggle;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mDrawerLayout.closeDrawer(mDrawerList);
        switch (position){
            case 0:changeFragment(new Geo(), true);break;
            case 1:changeFragment(new Editor(), true);break;
            case 2:startActivity(new Intent(this, Settings.class));break;
        }
    }

    public void changeFragment( Fragment fragment, boolean direction ){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ftx = fragmentManager.beginTransaction();

        if( direction ) {
            ftx.setCustomAnimations(R.anim.fade_in_next, R.anim.fade_out_previous);
        }else{
            ftx.setCustomAnimations(R.anim.fade_in_previous, R.anim.fade_out_next);
        }

        ftx.replace(R.id.main_content, fragment);
        ftx.commit();
    }

    @Override
    public void onBackPressed() {
        if( listener == null || !listener.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public OnBackPressedListener listener;
    public interface OnBackPressedListener{
        boolean onBackPressed();
    }
    public void setObBackPressedListener(OnBackPressedListener listener) {
        this.listener = listener;
    }
    public void removeObBackPressedListener() {
        this.listener = null;
    }
}
