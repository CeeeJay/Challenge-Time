package com.ceejay.challengetime.challenge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CJay on 24.04.2015 for Challenge Time.
 */
public class Comparator {
    public final static String TAG = Comparator.class.getSimpleName();

    private String comparable;
    private int comparator;
    private String first , second;
    private String firstType = "null" , secondType = "null";
    private Challenge context;
    private Pattern pattern = Pattern.compile("(?:(\\S+)#)?(\\S+)\\s*([<||>||=||>=||<=])\\s*(?:(\\S+)#)?(\\S+)");//\s*(\+|\-|/\*|%)([0-9]*+)


    public Comparator(String comparable, Challenge context) {
        this.context = context;
        this.comparable = comparable;

        Matcher m = pattern.matcher(comparable);
        if(m.find()){

            this.first = m.group(2);
            this.second = m.group(5);
            this.firstType = m.group(1);
            this.secondType = m.group(4);

            if(secondType == null){
                if(second.equals("true") || second.equals("false")){
                    secondType = "boolean";
                }
            }

            switch(m.group(3)){
                case "<=": this.comparator = 3; break;
                case ">=": this.comparator = 2; break;
                case ">": this.comparator = 1; break;
                case "=": this.comparator = 0; break;
                case "<": this.comparator = -1; break;
            }
        }
    }

    public boolean compare(){
        if( firstType.equals("number") && secondType.equals("number") ) {
            return Integer.valueOf(first).compareTo(Integer.valueOf(second)) == comparator;
        }else if( firstType.equals("number") && secondType.equals("int") ){
            return Integer.valueOf(first).compareTo(context.getInteger(second)) == comparator;
        }else if( firstType.equals("int") && secondType.equals("number") ){
            return context.getInteger(first).compareTo(Integer.valueOf(second)) == comparator;
        }else if( firstType.equals("int") && secondType.equals("int") ){
            return context.getInteger(first).compareTo(context.getInteger(second)) == comparator;
        }else if( firstType.equals("bool") && secondType.equals("boolean") ){
            return context.getBooleanHolder(first).getValue().compareTo(Boolean.parseBoolean(second)) == comparator;
        }else if(comparable.equals("true")){
            return true;
        }

        return false;
    }

}




