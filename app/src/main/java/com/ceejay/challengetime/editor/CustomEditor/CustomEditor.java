package com.ceejay.challengetime.editor.CustomEditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.editor.CustomEditor.Loop.EffectActivity;
import com.ceejay.challengetime.editor.CustomEditor.Loop.TriggerActivity;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.main.MainActivity;

import java.util.Locale;

/**
 * Created by CJay on 11.02.2015 for Challenge Time.
 */
public class CustomEditor extends Fragment implements View.OnClickListener{
    public final static String TAG = CustomEditor.class.getSimpleName();

    public static Challenge challenge;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private Context context;
    private View view;

    private int position = 0;

    private int[] tabs = {R.id.allgemein,R.id.var,R.id.geo,R.id.function,R.id.loop};
    public static ArrayAdapter varArrayAdapter, geometryArrayAdapter, functionArrayAdapter, loopArrayAdapter ;

    public CustomEditor() {
        this.context = MainActivity.getAppContext();
    }

    @Override
    public void onAttach(final Activity activity) {
        ((MainActivity)activity).setObBackPressedListener(new MainActivity.OnBackPressedListener() {
            @Override
            public boolean onBackPressed() {
                ((MainActivity)activity).changeFragment(new Geo(),false);
                return true;
            }
        });
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.challenge_builder, container, false);
        this.view = view;

        challenge = new Challenge();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        for( int tab : tabs ){
            view.findViewById(tab).setOnClickListener(this);
        }
        highlightTab(view.findViewById(tabs[0]));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                highlightTab(view.findViewById(tabs[position]));
                if (position + 1 < tabs.length) {
                    Drawable first = getResources().getDrawable(R.drawable.border_bottom);
                    Drawable second = getResources().getDrawable(R.drawable.border_bottom);
                    first.setAlpha((int) (255 * positionOffset));
                    second.setAlpha((int) (255 * ( 1 - positionOffset)));
                    view.findViewById(tabs[position +1]).setBackground(first);
                    view.findViewById(tabs[position]).setBackground(second);
                }
            }

            @Override
            public void onPageSelected(int pos) {
                //highlightTab(view.findViewById(tabs[pos]));
                position = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Button button = (Button)view.findViewById(R.id.add_list_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                        challenge.addFunction(context);
                    case 4:
                        challenge.addTrigger(context);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick( View v ) {
        //highlightTab(v);
        switch (v.getId()){
            case R.id.allgemein:mViewPager.setCurrentItem(0); break;
            case R.id.var:      mViewPager.setCurrentItem(1); break;
            case R.id.geo:      mViewPager.setCurrentItem(2); break;
            case R.id.function: mViewPager.setCurrentItem(3); break;
            case R.id.loop:     mViewPager.setCurrentItem(4); break;
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
            view.findViewById(tab).setBackgroundColor(Color.TRANSPARENT);
        }
        v.setBackground(getResources().getDrawable(R.drawable.border_bottom));
    }

    @Override
    public void onDestroy() {
        ChallengeAdapter.addChallenge(challenge);
        super.onDestroy();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG,challenge.triggers.get(0).trigger);
        if(resultCode == 2) {
            try {
                Bundle bundle = data.getExtras();
                Area area = new Area();
                area.position = new LatLng(Double.valueOf(bundle.getString("position").split(",")[0]), Double.valueOf(bundle.getString("position").split(",")[1]));
                area.title = bundle.getString("name");
                challenge.addArea(bundle.getString("name"), area);
                if (geometryArrayAdapter != null) {
                    geometryArrayAdapter.notifyDataSetChanged();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }*/

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
                case 0: return "TEST";
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
            View rootView = inflater.inflate(R.layout.list, container, false);

            ListView list = (ListView)rootView.findViewById(R.id.listView);
            ArrayAdapter arrayAdapter;

            switch(this.getArguments().getInt(ARG_SECTION_NUMBER)){
                case 2:
                    arrayAdapter = new VarAdapter(MainActivity.getAppContext(),challenge);
                    varArrayAdapter = arrayAdapter;
                    break;
                case 3:
                    arrayAdapter = new GeometryAdapter(MainActivity.getAppContext(),challenge);
                    geometryArrayAdapter = arrayAdapter;
                    break;
                case 4:
                    arrayAdapter = new FunctionAdapter(MainActivity.getAppContext(),challenge);
                    functionArrayAdapter = arrayAdapter;
                    break;
                case 5:
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getAppContext());
                        builder.setTitle("Pick a LOOOL")
                            .setItems(R.array.loop, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent;
                                    switch (which) {
                                        case 0:
                                            intent = new Intent(MainActivity.getAppContext(), TriggerActivity.class);
                                            startActivityForResult(intent, position);
                                            break;
                                        case 1:
                                            intent = new Intent(MainActivity.getAppContext(), EffectActivity.class);
                                            startActivityForResult(intent, position);
                                            break;
                                    }
                                }
                            });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        }
                    });
                    arrayAdapter = new LoopAdapter(MainActivity.getAppContext(),challenge);
                    loopArrayAdapter = arrayAdapter;
                    break;
                default:
                    return rootView;
            }
            list.setAdapter(arrayAdapter);
            return rootView;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

}
