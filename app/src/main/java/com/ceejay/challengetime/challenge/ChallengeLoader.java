package com.ceejay.challengetime.challenge;

import android.os.Bundle;
import android.util.JsonReader;

import com.ceejay.challengetime.helper.HttpPostContact;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class ChallengeLoader {
    public final static String TAG = ChallengeLoader.class.getSimpleName();

    public ChallengeLoader() {}

    public static Challenge load(){
        HttpPostContact contact = new HttpPostContact("http://192.168.178.55/challanges/brunnen.challenge");
        InputStream stream = contact.send(new Bundle());
        return StreamToChallenge(stream);
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
                    case "weight":
                        timer.weight = jsonReader.nextDouble();
                        break;
                    case "startTime":
                        timer.startTime = jsonReader.nextInt();
                        break;
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
                    case "color":
                        area.color = jsonReader.nextString();
                        switch (area.color){
                            case "start":area.color="#7700FF00";break;
                            case "point":area.color="#77777777";break;
                            case "finish":area.color="#77FF0000";break;
                        }
                        break;
                    case "size":
                        area.size = jsonReader.nextInt();
                        break;
                    case "focus":
                        area.focus = jsonReader.nextBoolean();
                        break;
                    case "position":
                        area.position = readPosition(jsonReader);
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
            challenge.addBoolean(jsonReader.nextName(), jsonReader.nextBoolean());
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

    public static void readGeometry(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch(jsonReader.nextName()){
                case "areas": readArea(jsonReader ,challenge); break;
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
                case "dictionary":      readDictionary(jsonReader, challenge); break;
                case "variables":       readVariables(jsonReader, challenge); break;
                case "name":            challenge.name = jsonReader.nextString(); break;
                case "position":        challenge.position = readPosition(jsonReader); break;
                case "publisher":       challenge.publisher = jsonReader.nextString();break;
                case "publish_time":    challenge.publish_time = jsonReader.nextInt();break;
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




