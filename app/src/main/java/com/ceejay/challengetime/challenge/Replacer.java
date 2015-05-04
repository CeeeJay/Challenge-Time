package com.ceejay.challengetime.challenge;

import com.ceejay.challengetime.geo.LocationObserver;
import com.ceejay.challengetime.helper.Distance;

import java.util.regex.Matcher;

/**
 * Created by CJay on 02.05.2015 for Challenge Time.
 */
public class Replacer {
    public final static String TAG = Replacer.class.getSimpleName();

    public static String replace( String string , Challenge context ){

        //Replace User in Area statement
        Matcher m = PatternType.userInArea.matcher(string);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, userInArea(m.group(1),m.group(2),m.group(3),context));
        }
        m.appendTail(sb);

        //Replace Int , Bool , Timer Variables with worth
        m = PatternType.variable.matcher(m.toString());
        sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, get(m.group(1), m.group(2), context));
        }
        m.appendTail(sb);

        return sb.toString();
    }

    public static String userInArea( String user , String area , String type , Challenge context ){
        if( type.equals( "->" ) ){
            return String.valueOf(Distance.between( LocationObserver.position , context.getArea(area).position ) <= context.getArea(area).radius);
        }else if( type.equals( "<-" ) ){
            return String.valueOf(Distance.between(LocationObserver.position, context.getArea(area).position) > context.getArea(area).radius);
        }
        return "";
    }

    public static String get( String type , String key , Challenge context ){
        try {
            switch(type){
                case "int": return String.valueOf(context.getInt(key).getValue());
                case "bool": return String.valueOf(context.getBool(key).getValue());
                case "timer":
                    if(key.split(".")[1].equals("time")) {
                        return String.valueOf(context.getTimer(key.split(".")[0]).getTime());
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}




