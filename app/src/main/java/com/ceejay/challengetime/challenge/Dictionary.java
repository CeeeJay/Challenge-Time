package com.ceejay.challengetime.challenge;

import java.util.HashMap;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Dictionary {
    public final static String TAG = Dictionary.class.getSimpleName();

    private HashMap<String , Translate> dictionary = new HashMap<>();

    public void addTranslate( String name , Translate translate ){
        dictionary.put( name , translate );
    }

    public String getTranslate( String name , String language ){
        return dictionary.get(name).getDefinition(language);
    }

}




