package com.ceejay.challengetime.editor.CustomEditor.Loop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Trigger;
import com.ceejay.challengetime.challenge.helper.PatternType;
import com.ceejay.challengetime.editor.CustomEditor.CustomEditor;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by CJay on 09.05.2015 for Challenge Time.
 */
public class TriggerActivity extends Activity {
    public final static String TAG = TriggerActivity.class.getSimpleName();

    private ArrayList<TriggerLine> lines = new ArrayList<>();
    private TriggerAdapter adapter;
    private Trigger trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_list);

        trigger = CustomEditor.challenge.triggers.get(getIntent().getIntExtra("requestCode",0));
        if(trigger.trigger != null){
            Matcher m = PatternType.awl.matcher("U " + trigger.trigger);
            while(m.find()){
                lines.add(new TriggerLine(this, m.group(1), m.group(2)));
            }
        }

        findViewById(R.id.add_list_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lines.add(new TriggerLine(TriggerActivity.this,"U",""));
                if( adapter != null ){
                    adapter.notifyDataSetChanged();
                }
            }
        });

        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new TriggerAdapter(this,lines);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        String str = "";
        for( TriggerLine line : lines ){
            if(line.toString().replace(" ","").equals("")){
                return;
            }
            str += line.toString() + " ";
        }
        trigger.trigger = str.substring(1,str.length());
        super.onBackPressed();
    }
}




