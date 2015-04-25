package com.ceejay.challengetime.challenge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CJay on 24.04.2015 for Challenge Time.
 */
public class Effect {
    public final static String TAG = Effect.class.getSimpleName();

    public String effect;
    public String first , second;
    public String firstType , secondType;
    public boolean invert = false;
    public String method;
    public int value = 1;
    public Challenge context;

    Pattern pattern = Pattern.compile("(?:(\\S+)#)(\\S+)\\s*(:=)\\s*([!])?\\s*(?:(\\S+)#)?(\\S+)");
    Pattern patternCount = Pattern.compile("(?:(\\S+)#(\\S+))\\s*(\\+\\+|\\-\\-)");
    Pattern patternCountValue = Pattern.compile("(?:(\\S+)#(\\S+))\\s*(\\+=|\\-=)\\s*(\\-?[0-9]+)");
    Pattern patternFunction = Pattern.compile("(\\S+)\\(\\)");
    Pattern patternNumber = Pattern.compile("^\\-?[0-9]+$");

    public Effect( String effect , Challenge context ) {
        this.context = context;
        this.effect = effect;
        Matcher m = pattern.matcher(effect);
        Matcher mC = patternCount.matcher(effect);
        Matcher mCV = patternCountValue.matcher(effect);
        Matcher mF = patternFunction.matcher(effect);
        if(m.find()){
            //Log.i(TAG, "Hab da was gefunden " + m.group(1) + " ; " + m.group(2) + " ; " + m.group(3) + " ; " + m.group(4) + " ; " + m.group(5) + " ; " + m.group(6));
            first = m.group(2);
            second = m.group(6);
            firstType = m.group(1);
            secondType = m.group(5);
            method = m.group(3);

            if(m.group(4) != null && m.group(4).equals("!")){
                invert = true;
            }

            if(firstType == null){
                if(first.equals("true") || first.equals("false")){
                    firstType = "boolean";
                }else{
                    Matcher mN = patternNumber.matcher(first);
                    if(mN.find()){
                        firstType = "number";
                    }
                }
            }

            if(secondType == null){
                if(second.equals("true") || second.equals("false")){
                    secondType = "boolean";
                }else{
                    Matcher mN = patternNumber.matcher(second);
                    if(mN.find()){
                        secondType = "number";
                    }
                }
            }

        }else if(mC.find()){

            firstType = mC.group(1);
            first = mC.group(2);
            method = mC.group(3);

        }else if(mCV.find()){

            firstType = mCV.group(1);
            first = mCV.group(2);
            method = mCV.group(3);
            value = Integer.parseInt(mCV.group(4));

        }else if(mF.find()){

            first = mF.group(1);
            firstType = "function";

        }
    }

    public void execute(){
        if(firstType != null) {
            switch (firstType) {
                case "bool":
                    if (secondType.equals("bool")) {
                        if(invert){
                            context.getBooleanHolder(first).setValue(!context.getBooleanHolder(second).getValue());
                        }else {
                            context.getBooleanHolder(first).setValue(context.getBooleanHolder(second).getValue());
                        }
                    }else if (secondType.equals("boolean")){
                        context.getBooleanHolder(first).setValue(Boolean.parseBoolean(second));

                    }
                    break;
                case "int":
                    switch(method){
                        case "+=": case "++": context.getIntegerHolder(first).setValue(context.getInteger(first)+value); break;
                        case "-=": case "--": context.getIntegerHolder(first).setValue(context.getInteger(first)-value); break;
                        case ":=":
                            if(secondType.equals("number")){
                                context.getIntegerHolder(first).setValue(Integer.valueOf(second));
                            }else{
                                context.getIntegerHolder(first).setValue(context.getInteger(second));
                            }
                            break;
                    }
                    break;
                case "function":
                    context.getFunction(first).call();
                    break;
            }
        }
    }
}




