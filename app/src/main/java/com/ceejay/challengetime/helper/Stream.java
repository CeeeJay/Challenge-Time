package com.ceejay.challengetime.helper;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.Toast;

import com.ceejay.challengetime.challenge.Area;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.Function;
import com.ceejay.challengetime.challenge.Timer;
import com.ceejay.challengetime.challenge.Trigger;
import com.ceejay.challengetime.helper.math.PointD;
import com.ceejay.challengetime.main.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Development on 10.01.2015.
 * Method to convert a Stream into a String
 *
 */
public class Stream {
    public final static String TAG = Stream.class.getSimpleName();

    public static String toString(InputStream is) {
        if (is == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static ArrayList toHashMap(InputStream is) {
        ArrayList<HashMap<String, String>> json = new ArrayList<>();

        if (is == null) {
            return null;
        }

        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            jsonReader.setLenient(true);
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                json.add(new HashMap<String, String>());
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    json.get(json.size() - 1).put(jsonReader.nextName(), jsonReader.nextName());
                }
                jsonReader.endObject();
            }
            jsonReader.endArray();
            jsonReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void next(JsonReader jsonReader) throws IOException{
        if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                next(jsonReader);
            }
            jsonReader.endObject();
        } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                next(jsonReader);
            }
            jsonReader.endArray();
        } else if (jsonReader.peek() == JsonToken.STRING) {
            jsonReader.nextString();
        } else if (jsonReader.peek() == JsonToken.NUMBER) {
            jsonReader.nextInt();
        } else if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
        } else if (jsonReader.peek() == JsonToken.BOOLEAN) {
            jsonReader.nextBoolean();
        } else if (jsonReader.peek() == JsonToken.NAME) {
            jsonReader.nextName();
        }
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
                        next(jsonReader);
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static Area readArea(JsonReader jsonReader) throws IOException{
        Area area = new Area();
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
                    break;
                case "size":
                    area.size = jsonReader.nextInt();
                    break;
                case "focus":
                    area.focus = jsonReader.nextBoolean();
                    break;
                case "position":
                    //area.position =
                    jsonReader.nextString();
                    break;
                default:
                    next(jsonReader);
                    break;
            }
        }
        jsonReader.endObject();
        return area;
    }

    public static void readFunction(JsonReader jsonReader,Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Function function = new Function();
            challenge.addFunction(jsonReader.nextName(), function);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "trigger":
                        function.trigger = jsonReader.nextString();
                        break;
                    case "effect":
                        function.effect = jsonReader.nextString();
                        break;
                    case "return":
                        function.back = jsonReader.nextString();
                        break;
                    default:
                        next(jsonReader);
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();
    }

    public static Trigger readTrigger(JsonReader jsonReader) throws IOException{
        Trigger trigger = new Trigger();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "trigger":
                    trigger.trigger = jsonReader.nextString();
                    break;
                case "effect":
                    trigger.effect = jsonReader.nextString();
                    break;
                default:
                    next(jsonReader);
                    break;
            }
        }
        jsonReader.endObject();
        return trigger;
    }

    public static void readTrigger(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            Trigger trigger = new Trigger();
            challenge.addTrigger( trigger );
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "trigger":
                        trigger.trigger = jsonReader.nextString();
                        break;
                    case "effect":
                        trigger.effect = jsonReader.nextString();
                        break;
                    default:
                        next(jsonReader);
                        break;
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endArray();
    }

    public static void readGeometry(JsonReader jsonReader , Challenge challenge) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch(jsonReader.nextName()){
                case "areas":
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        challenge.addArea(jsonReader.nextName(), readArea(jsonReader));
                    }
                    jsonReader.endObject();
                    break;
                default:
                    next(jsonReader);
                    break;
            }
        }
        jsonReader.endObject();
    }

    public static Challenge readJson(JsonReader jsonReader) throws IOException{
            Challenge challenge = new Challenge();
            jsonReader.beginObject();
            while (jsonReader.hasNext()){
                switch (jsonReader.nextName()) {
                    case "name":  challenge.name = jsonReader.nextString(); break;
                    case "publisher": challenge.publisher = jsonReader.nextString();break;
                    case "publish_time": challenge.publish_time = jsonReader.nextInt();break;
                    case "timer": readTimer(jsonReader, challenge);break;
                    case "geometry": readGeometry(jsonReader, challenge);break;
                    case "functions": readFunction(jsonReader, challenge);break;
                    case "trigger": readTrigger(jsonReader, challenge);break;
                    default: next(jsonReader);break;
                }
            }
            jsonReader.endObject();
            return challenge;
    }


    public static Challenge test(InputStream is) {
        Challenge challenge = new Challenge();
        if (is == null) {
            return null;
        }
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            jsonReader.setLenient(true);
            challenge = readJson(jsonReader);
            Log.i(TAG, challenge.functions.get("Start").effect+"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return challenge;
    }


}
