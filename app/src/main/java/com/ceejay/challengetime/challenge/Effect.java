package com.ceejay.challengetime.challenge;

import android.app.Activity;

import com.ceejay.challengetime.main.MainActivity;

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

    public Effect( String effect , Challenge context ) {
        this.context = context;
        this.effect = effect;
        Matcher m = pattern.matcher(effect);
        Matcher mC = patternCount.matcher(effect);
        Matcher mCV = patternCountValue.matcher(effect);
        Matcher mF = PatternType.function.matcher(effect);
        if(m.find()){
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
                    Matcher mN = PatternType.number.matcher(first);
                    if(mN.find()){
                        firstType = "number";
                    }
                }
            }

            if(secondType == null){
                if(second.equals("true") || second.equals("false")){
                    secondType = "boolean";
                }else{
                    Matcher mN = PatternType.number.matcher(second);
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
            firstType = mF.group(1);
            first = mF.group(2);
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
                case "area":
                    if( method.equals(":=") ){
                        final Matcher m = PatternType.object.matcher(first);
                        if( m.find() ){
                            ((Activity)MainActivity.getAppContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (m.group(2)) {
                                        case "color":
                                            context.getArea(m.group(1)).changeColor(second);
                                            break;
                                    }
                                }
                            });

                        }
                    }
                    break;
                case "timer":
                    final Matcher m = PatternType.object.matcher(first);
                    if( m.find() ) {
                        switch (m.group(2)){
                            case "start":case "Start":
                                context.getTimer(m.group(1)).start();
                                break;
                            case "stop":case "Stop":
                                context.getTimer(m.group(1)).stop();
                                break;
                        }
                    }
                    break;
                case "function":case "func":
                    context.getFunction(first).call();
                    break;
            }
        }
    }
}




