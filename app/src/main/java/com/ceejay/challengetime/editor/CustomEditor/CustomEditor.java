package com.ceejay.challengetime.editor.CustomEditor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Area;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.Function;
import com.ceejay.challengetime.main.BaseActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class CustomEditor extends BaseActivity implements View.OnClickListener{
    public final static String TAG = CustomEditor.class.getSimpleName();

    public static Challenge challenge;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    private int position = 0;

    private int[] tabs = {R.id.allgemein,R.id.var,R.id.geo,R.id.function,R.id.loop};
    public static ArrayAdapter varArrayAdapter, geometryArrayAdapter, functionArrayAdapter, loopArrayAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);

        context = this;

        challenge = new Challenge();

        challenge.addBool("test", true);
        challenge.addBool("test2", false);
        challenge.addBool("test3", true);

        challenge.addInteger("lol", 123);

        challenge.addString("Penis", "lol");


        Area area = new Area();
        area.position = new LatLng(2,3);
        area.title = "lol";
        challenge.addArea("test", area);

        Function function = new Function();
        function.effect = "area#start.color := green";
        challenge.addFunction( "hmm" , function );

        setContentView(R.layout.challenge_builder);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        for( int tab : tabs ){
            findViewById(tab).setOnClickListener(this);
        }
        highlightTab(findViewById(tabs[0]));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                highlightTab(findViewById(tabs[position]));
                if (position + 1 < tabs.length) {
                    findViewById(tabs[position]).setBackgroundColor(Color.argb((int) (50 * positionOffset), 0, 0, 0));
                    findViewById(tabs[position + 1]).setBackgroundColor(Color.argb((int) (50 - 50 * positionOffset), 0, 0, 0));
                }
            }

            @Override
            public void onPageSelected(int pos) {
                highlightTab(findViewById(tabs[pos]));
                position = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        super.startNavigationDrawer();

        Button button = (Button)findViewById(R.id.add_list_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomEditor.this);
                switch(position) {
                    case 1:
                        builder.setTitle("Pick a DataType")
                            .setItems(R.array.var_types, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0: challenge.addBool(context); break;
                                        case 1: challenge.addInteger(context); break;
                                        case 2: challenge.addString(context); break;
                                    }
                                    refresh();
                                }
                            });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        break;
                    case 2:
                        builder.setTitle("Pick a DataType")
                                .setItems(R.array.area_types, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                challenge.addArea(context);
                                                //challenge.addBool("lol" + Math.random(), Math.random() > 0.5);
                                                break;
                                            case 1:
                                                challenge.addInteger("lol" + Math.random(), 34);
                                                break;
                                            case 2:
                                                challenge.addString("lol" + Math.random(), Math.random() + "");
                                                break;
                                        }
                                        refresh();
                                    }
                                });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        break;
                    case 3:
                        //challenge.addFunction(this);
                    case 4:
                        LinkDialog linkDialog = new LinkDialog(context);
                        linkDialog.show();
                        //challenge.addTrigger(this);
                }
            }
        });
    }

    @Override
    public void onClick( View v ) {
        highlightTab(v);
        switch (v.getId()){
            case R.id.allgemein:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.var:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.geo:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.function:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.loop:
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    public void refresh(){
        if(varArrayAdapter != null){
            varArrayAdapter.notifyDataSetChanged();
        }
        if(geometryArrayAdapter != null){
            geometryArrayAdapter.notifyDataSetChanged();
        }
        if(functionArrayAdapter != null){
            functionArrayAdapter.notifyDataSetChanged();
        }
        if(loopArrayAdapter != null){
            loopArrayAdapter.notifyDataSetChanged();
        }
    }

    public void highlightTab( View v ){
        for( int tab : tabs ){
            findViewById(tab).setBackgroundColor(Color.argb(50, 0, 0, 0));
        }
        v.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Bundle bundle = data.getExtras();
            Area area = new Area();
            area.position = new LatLng(Double.valueOf(bundle.getString("position").split(",")[0]),Double.valueOf(bundle.getString("position").split(",")[1]));
            area.title = bundle.getString("name");
            challenge.addArea(bundle.getString("name"),area);
            if(geometryArrayAdapter != null){
                geometryArrayAdapter.notifyDataSetChanged();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0: return getString(R.string.title_section1).toUpperCase(l);
                case 1: return getString(R.string.title_section2).toUpperCase(l);
                case 2: return getString(R.string.title_section3).toUpperCase(l);
            }
            return "sss";
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.var_list, container, false);

            ListView list = (ListView)rootView.findViewById(R.id.listView);
            ArrayAdapter arrayAdapter;

            switch(this.getArguments().getInt(ARG_SECTION_NUMBER)){
                case 2:
                    arrayAdapter = new VarAdapter(CustomEditor.getAppContext(),challenge);
                    varArrayAdapter = arrayAdapter;
                    break;
                case 3:
                    arrayAdapter = new GeometryAdapter(CustomEditor.getAppContext(),challenge);
                    geometryArrayAdapter = arrayAdapter;
                    break;
                case 4:
                    arrayAdapter = new FunctionAdapter(CustomEditor.getAppContext(),challenge);
                    functionArrayAdapter = arrayAdapter;
                    break;
                case 5:
                    arrayAdapter = new LoopAdapter(CustomEditor.getAppContext(),challenge);
                    loopArrayAdapter = arrayAdapter;
                    break;
                default:
                    return rootView;
            }
            list.setAdapter(arrayAdapter);
            return rootView;
        }
    }

}
