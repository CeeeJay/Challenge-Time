package com.ceejay.challengetime.editor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.main.BaseActivity;

import java.util.Locale;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class EditorActivity extends BaseActivity{
    public final static String TAG = EditorActivity.class.getSimpleName();

    public static Challenge challenge = new Challenge();

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);

        context = this;
        challenge.addBool("Hallo",true);
        challenge.addBool("Hallo2", false);
        challenge.addBool("Hallo3", false);
        challenge.addBool("Hallo4",true);

        challenge.addInteger("Hallo", 1);
        challenge.addInteger("Hallo2", 2);
        challenge.addInteger("Hallo3", 3);
        challenge.addInteger("Hallo4", 4);

        challenge.addString("Hallo", "1");
        challenge.addString("Hallo2", "2");
        challenge.addString("Hallo3", "3");
        challenge.addString("Hallo4", "4");

        setContentView(R.layout.challenge_builder);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, position + "");
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
            View rootView = inflater.inflate(R.layout.editor_list_item, container, false);

            switch(this.getArguments().getInt(ARG_SECTION_NUMBER)){
                case 2:
                    ListView list = (ListView)rootView.findViewById(R.id.listView);
                    VarAdapter varAdapter = new VarAdapter(EditorActivity.getAppContext(),challenge);

                    list.setAdapter(varAdapter);
                    break;
            }



            return rootView;
        }
    }

}
