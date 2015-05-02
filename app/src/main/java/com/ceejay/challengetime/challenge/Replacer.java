package com.ceejay.challengetime.challenge;

import android.util.Log;

import java.util.regex.Matcher;

/**
 * Created by CJay on 02.05.2015 for Challenge Time.
 */
public class Replacer {
    public final static String TAG = Replacer.class.getSimpleName();

    public static String replace( String string , Challenge context ){
        Matcher m = PatternType.variable.matcher(string);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, get(m.group(1),m.group(2),context));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String get( String type , String key , Challenge context ){
        try {
            switch(type){
                case "int": return String.valueOf(context.getInteger(key));
                case "bool": return String.valueOf(context.getBoolean(key));
                case "timer":
                    if(key.split(".")[1].equals("time")) {
                        return String.valueOf(context.getTimer(key.split(".")[0]).getTime());
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}




