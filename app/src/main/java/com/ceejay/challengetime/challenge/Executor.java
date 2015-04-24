package com.ceejay.challengetime.challenge;

import java.util.ArrayList;

/**
 * Created by CJay on 24.04.2015 for Challenge Time.
 */
public class Executor {
    public final static String TAG = Executor.class.getSimpleName();

    private ArrayList<Effect> effects = new ArrayList<>();

    public Executor( String eff , Challenge context ) {
        for( String effect : eff.split(";") ){
            effects.add(new Effect( effect , context ));
        }
    }

    public void execute(){
        for( Effect effect : effects ){
            effect.execute();
        }
    }
}




