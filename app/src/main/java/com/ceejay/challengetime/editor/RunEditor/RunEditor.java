package com.ceejay.challengetime.editor.RunEditor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Area;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.challenge.Timer;
import com.ceejay.challengetime.challenge.Trigger;
import com.ceejay.challengetime.geo.Geo;
import com.ceejay.challengetime.main.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class RunEditor extends Fragment {
    public final static String TAG = RunEditor.class.getSimpleName();

    private ArrayList<Area> areas = new ArrayList<>();
    private Challenge challenge;
    private ArrayAdapter adapter;
    public Context context;

    public RunEditor() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trigger_list, container, false);
        challenge = new Challenge();
        challenge.position = new LatLng(49.28722,7.11829);

        view.findViewById(R.id.add_list_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Area area = new Area();
                area.title = "test";
                area.radius = 50;
                areas.add(area);
                area.position = new LatLng(49.28722,Double.parseDouble("7.11" + areas.size() + "29"));
                adapter.notifyDataSetChanged();
            }
        });

        ListView listView = (ListView)view.findViewById(R.id.listView);
        adapter = new Adapter(context,areas);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parseChallenge();
        ChallengeAdapter.addChallenge(challenge);
    }

    public void parseChallenge(){
        try {
            Timer timer = new Timer();
            challenge.addTimer("timer", timer);
            Area area = areas.get(0);
            area.fillColor = Color.parseColor("#7700FF00");
            challenge.addBool(area.title + 0, false);
            challenge.addArea(area.title + 0, area);
            Trigger trigger = new Trigger(challenge);
            trigger.setTrigger("user#this -> area#" + areas.get(0).title + areas.indexOf(areas.get(0)) + "");
            trigger.setEffect("timer#timer.start();bool#" + areas.get(0).title + areas.indexOf(areas.get(0)) + " := true");
            challenge.addTrigger(trigger);

            for( int i = 1 ; i < areas.size() - 1 ; i++ ){
                area = areas.get(i);
                area.fillColor = Color.parseColor("#77333333");
                challenge.addArea(area.title + i, area);
                challenge.addBool(area.title + i, false);
                trigger = new Trigger(challenge);
                trigger.setTrigger("user#this -> area#" + area.title + i + " && bool#" + area.title + (i-1) + " == true ");
                trigger.setEffect("bool#" + areas.get(1).title + i + " := true");
                challenge.addTrigger(trigger);
            }

            area = areas.get(areas.size() - 1);
            area.fillColor = Color.parseColor("#77FF0000");
            challenge.addArea(area.title + areas.indexOf(area) + "", area);
            trigger = new Trigger(challenge);
            trigger.setTrigger("user#this -> area#" + area.title + (areas.size()-1) + " && bool#" + areas.get(areas.size() - 2).title + (areas.size() - 2) + " == true ");
            trigger.setEffect("timer#timer.stop()");
            challenge.addTrigger(trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




