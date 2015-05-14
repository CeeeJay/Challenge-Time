package com.ceejay.challengetime.challenge;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.ceejay.challengetime.challenge.helper.PatternType;
import com.ceejay.challengetime.helper.HttpPostContact;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class ChallengeLoader {
    public final static String TAG = ChallengeLoader.class.getSimpleName();

    public ChallengeLoader() {}

    public static Challenge load( Context context , String name ){
        HttpPostContact contact = new HttpPostContact("http://192.168.178.55/challanges/" + name + ".challenge");
        InputStream stream = contact.send(new Bundle());
        return StreamToChallenge(stream);
    }

    public static void load( Context context ){
        HttpPostContact contact = new HttpPostContact("http://192.168.178.55/script/php/load_challenge.php");
        InputStream stream = contact.send(new Bundle());
        Log.i(TAG, StreamToArray(stream).toString());
    }

    public static ArrayList<String> StreamToArray( InputStream is ){
        ArrayList<String> list = new ArrayList<>();
        if (is == null) {
            return null;
        }
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            jsonReader.setLenient(true);
            jsonReader.beginArray();
            while (jsonReader.hasNext()){
                list.add(jsonReader.nextString());
            }
            jsonReader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void readDictionary(JsonReader jsonReader,Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Translate translate = new Translate();
            challenge.addTranslate(jsonReader.nextName(), translate);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                translate.addDefinition(jsonReader.nextName(),jsonReader.nextString());
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static void readTimer(JsonReader jsonReader,Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Timer timer = new Timer();
            challenge.addTimer(jsonReader.nextName(), timer);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    /*case "startTime":
                        timer.startTime = jsonReader.nextInt();
                        break;*/
                    case "reverse":
                        timer.reverse = jsonReader.nextBoolean();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static void readArea(JsonReader jsonReader,Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Area area = new Area();
            challenge.addArea(jsonReader.nextName(), area);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "title":
                        area.title = jsonReader.nextString();
                        break;
                    case "description":
                        area.description = jsonReader.nextString();
                        break;
                    case "fillColor":
                        if( jsonReader.peek() == JsonToken.STRING ) {
                            String color = jsonReader.nextString();
                            switch (color) {
                                case "start":
                                    color = "#7700FF00";
                                    break;
                                case "point":
                                    color = "#77777777";
                                    break;
                                case "finish":
                                    color = "#77FF0000";
                                    break;
                            }
                            area.fillColor = Color.parseColor(color);
                        }else if( jsonReader.peek() == JsonToken.NUMBER ){
                            area.fillColor = jsonReader.nextInt();
                        }
                        break;
                    case "radius":
                        area.radius = jsonReader.nextInt();
                        break;
                    case "position":
                        area.position = readPosition(jsonReader);
                        break;
                    case "focus":
                        area.focus = jsonReader.nextBoolean();
                        break;
                    case "visible":
                        area.visible = jsonReader.nextBoolean();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static void readFunction(JsonReader jsonReader,Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            challenge.addFunction(jsonReader.nextName(), new Function(jsonReader,challenge));
        }
        jsonReader.endObject();
    }

    public static void readString(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            challenge.addString(jsonReader.nextName(), jsonReader.nextString());
        }
        jsonReader.endObject();
    }

    public static void readInteger(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            challenge.addInteger(jsonReader.nextName(), jsonReader.nextInt());
        }
        jsonReader.endObject();
    }

    public static void readBool(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            challenge.addBool(jsonReader.nextName(), jsonReader.nextBoolean());
        }
        jsonReader.endObject();
    }

    public static void readLoop(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            challenge.addTrigger( new Trigger( jsonReader , challenge ) );
        }
        jsonReader.endArray();
    }

    public static void readPolygon( JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Polygon polygon = new Polygon();
            challenge.addPolygon(jsonReader.nextName(), polygon);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "title":
                        polygon.title = jsonReader.nextString();
                        break;
                    case "description":
                        polygon.description = jsonReader.nextString();
                        break;
                    case "strokeColor":
                        polygon.strokeColor = Color.parseColor(jsonReader.nextString());
                        break;
                    case "strokeWidth":
                        polygon.strokeWidth = jsonReader.nextInt();
                        break;
                    case "fillColor":
                        polygon.fillColor = Color.parseColor(jsonReader.nextString());
                        break;
                    case "points":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()){
                            polygon.points.add(readPosition(jsonReader));
                        }
                        jsonReader.endArray();
                        break;
                    case "visible":
                        polygon.visible = jsonReader.nextBoolean();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static void readPolyline( JsonReader jsonReader , Challenge challenge ) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Polyline polyline = new Polyline();
            challenge.addPolyline(jsonReader.nextName(), polyline);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "title":
                        polyline.title = jsonReader.nextString();
                        break;
                    case "description":
                        polyline.description = jsonReader.nextString();
                        break;
                    case "color":
                        polyline.color = Color.parseColor(jsonReader.nextString());
                        break;
                    case "width":
                        polyline.width = jsonReader.nextInt();
                        break;
                    case "points":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()){
                            polyline.points.add(readPosition(jsonReader));
                        }
                        jsonReader.endArray();
                        break;
                    case "visible":
                        polyline.visible = jsonReader.nextBoolean();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static void readGeometry(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch(jsonReader.nextName()){
                case "areas":   readArea(jsonReader ,challenge); break;
                case "polygons": readPolygon(jsonReader, challenge); break;
                case "polylines": readPolyline(jsonReader, challenge); break;
                default: jsonReader.skipValue(); break;
            }
        }
        jsonReader.endObject();
    }

    public static void readVariables(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch(jsonReader.nextName()){
                case "integer": readInteger(jsonReader, challenge); break;
                case "string":  readString(jsonReader, challenge); break;
                case "bool":    readBool(jsonReader, challenge); break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
    }

    public static LatLng readPosition(JsonReader jsonReader) throws IOException{
        Matcher m= PatternType.latLng.matcher(jsonReader.nextString());
        if(m.find()){
            return new LatLng(Double.parseDouble(m.group(1)),Double.parseDouble(m.group(2)));
        }
        return null;
    }

    public static Challenge readJson(JsonReader jsonReader) throws IOException{
        Challenge challenge = new Challenge();
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()) {
                case "name":            challenge.name = jsonReader.nextString(); break;
                case "position":        challenge.position = readPosition(jsonReader); break;
                case "publisher":       challenge.publisher = jsonReader.nextString();break;
                case "publish_time":    challenge.publish_time = jsonReader.nextInt();break;
                case "interval":        challenge.interval = jsonReader.nextInt();break;
                case "dictionary":      readDictionary(jsonReader, challenge); break;
                case "variables":       readVariables(jsonReader, challenge); break;
                case "timer":           readTimer(jsonReader, challenge);break;
                case "geometry":        readGeometry(jsonReader, challenge);break;
                case "functions":       readFunction(jsonReader, challenge);break;
                case "loop":            readLoop(jsonReader, challenge);break;
                default:                jsonReader.skipValue();break;
            }
        }
        jsonReader.endObject();
        return challenge;
    }


    public static Challenge StreamToChallenge(InputStream is) {
        Challenge challenge = new Challenge();
        if (is == null) {
            return null;
        }
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            jsonReader.setLenient(true);
            challenge = readJson(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return challenge;
    }




}




