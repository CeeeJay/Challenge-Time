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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(!translate.isEmpty()){
            sb.append("\"name\":\"" + name + "\",");
            for (String key : translate.keySet()) {
                sb.append("\"" + key + "\":\"" + translate.get(key) + "\",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}




