package com.ceejay.challengetime.challenge;

import android.app.Activity;

import com.ceejay.challengetime.main.MainActivity;
import com.google.android.gms.maps.model.LatLng;

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
                                    case "position":
                                        context.getArea(m.group(1)).changePosition(readPosition(second));
                                        break;
                                    case "radius":
                                        context.getArea(m.group(1)).changeRadius(Integer.parseInt(second));
                                        break;
                                    case "fillColor":
                                        context.getArea(m.group(1)).changeFillColor(second);
                                        break;
                                    case "strokeColor":
                                        context.getArea(m.group(1)).changeStrokeColor(second);
                                        break;
                                    case "strokeWidth":
                                        context.getArea(m.group(1)).changeStrokeWidth(Integer.parseInt(second));
                                        break;
                                    case "focus":
                                        context.getArea(m.group(1)).changeFocus(Boolean.parseBoolean(second));
                                        break;
                                    case "visible":
                                        context.getArea(m.group(1)).changeVisible(Boolean.parseBoolean(second));
                                        break;
                                }
                                }
                            });
                        }
                    }
                    break;
                case "polygon":
                    if( method.equals(":=") ){
                        final Matcher m = PatternType.object.matcher(first);
                        if( m.find() ){
                            ((Activity)MainActivity.getAppContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                switch (m.group(2)) {
                                    case "fillColor":
                                        context.getPolygon(m.group(1)).changeFillColor(second);
                                        break;
                                    case "strokeColor":
                                        context.getPolygon(m.group(1)).changeStrokeColor(second);
                                        break;
                                    case "strokeWidth":
                                        context.getPolygon(m.group(1)).changeStrokeWidth(Integer.parseInt(second));
                                        break;
                                    case "visible":
                                        context.getPolygon(m.group(1)).changeVisible(Boolean.parseBoolean(second));
                                        break;
                                }
                                }
                            });
                        }
                    }
                    break;
                case "polyline":
                    if( method.equals(":=") ){
                        final Matcher m = PatternType.object.matcher(first);
                        if( m.find() ){
                            ((Activity)MainActivity.getAppContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                switch (m.group(2)) {
                                    case "color":
                                        context.getPolyline(m.group(1)).changeColor(second);
                                        break;
                                    case "width":
                                        context.getPolyline(m.group(1)).changeColor(second);
                                        break;
                                    case "visible":
                                        context.getPolyline(m.group(1)).changeVisible(Boolean.parseBoolean(second));
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
                        switch (m.group(2).toLowerCase()){
                            case "start":
                                context.getTimer(m.group(1)).start();
                                break;
                            case "stop":
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

    public static LatLng readPosition(String string){
        Matcher m= PatternType.latLng.matcher(string);
        if(m.find()){
            return new LatLng(Double.parseDouble(m.group(1)),Double.parseDouble(m.group(2)));
        }
        return null;
    }

}




