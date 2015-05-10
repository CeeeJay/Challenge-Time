package com.ceejay.challengetime.editor.CustomEditor.Loop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Trigger;
import com.ceejay.challengetime.editor.CustomEditor.CustomEditor;

import java.util.ArrayList;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class EffectActivity extends Activity{
    public final static String TAG = EffectActivity.class.getSimpleName();

    private Trigger trigger;
    private ArrayList<String> lines = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_list);

        trigger = CustomEditor.challenge.triggers.get(getIntent().getIntExtra("requestCode",0));
        if(trigger.effect != null){
            String[] lines = trigger.effect.split(";");
            for( String line : lines ){
                this.lines.add(line);
            }
        }

        findViewById(R.id.add_list_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lines.add("test");
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new EffectAdapter(this,lines);
        listView.setAdapter(adapter);
    }
}




