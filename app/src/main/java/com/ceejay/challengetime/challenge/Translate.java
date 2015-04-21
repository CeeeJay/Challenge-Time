package com.ceejay.challengetime.challenge;

import java.util.HashMap;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Translate {
    public final static String TAG = Translate.class.getSimpleName();

    public String name;

    public HashMap<String,String> translate = new HashMap<>();

    public void addDefinition( String language , String definition ){
        translate.put(language,definition);
    }

    public String getDefinition( String language ){
        return translate.get(language);
    }

}




