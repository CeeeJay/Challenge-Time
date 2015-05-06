package com.ceejay.challengetime.editor;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.main.BaseActivity;

import java.util.Locale;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class EditorActivity extends BaseActivity implements View.OnClickListener{
    public final static String TAG = EditorActivity.class.getSimpleName();

    public static Challenge challenge = new Challenge();

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    private int[] tabs = {R.id.allgemein,R.id.var,R.id.geo,R.id.function,R.id.loop};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);

        context = this;

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
            public void onPageSelected(int position) {
                highlightTab(findViewById(tabs[position]));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        super.startNavigationDrawer();
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

    public void highlightTab( View v ){
        for( int tab : tabs ){
            findViewById(tab).setBackgroundColor(Color.argb(50, 0, 0, 0));
        }
        v.setBackgroundColor(Color.TRANSPARENT);
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
        private static final String[] LIST = {"Allgemein","Variabeln","Geometry","Loop","Functionen"};

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

            ListView list = (ListView)rootView.findViewById(R.id.listView);;
            ListAdapter listAdapter;

            switch(this.getArguments().getInt(ARG_SECTION_NUMBER)){
                case 2:
                    listAdapter = new VarAdapter(EditorActivity.getAppContext(),challenge);
                    break;
                case 3:
                    listAdapter = new AreaAdapter(EditorActivity.getAppContext(),challenge);
                    break;
                case 5:
                    listAdapter = new LoopAdapter(EditorActivity.getAppContext(),challenge);
                    break;
                default:
                    return rootView;
            }
            list.setAdapter(listAdapter);
            return rootView;
        }
    }

}
