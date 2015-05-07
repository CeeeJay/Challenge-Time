package com.ceejay.challengetime.editor.CustomEditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 07.05.2015 for Challenge Time.
 */
public class NewArea extends FragmentActivity {
    public final static String TAG = NewArea.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_area);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("name", ((TextView)findViewById(R.id.name)).getText().toString());
                b.putString("position", ((TextView)findViewById(R.id.position)).getText().toString());
                Intent i = getIntent();
                i.putExtras(b);
                setResult(NewArea.this.RESULT_OK, i);
                finish();
            }
        });
    }
}




