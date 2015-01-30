package com.ceejay.challengetime;

import android.util.JsonReader;
import android.widget.Toast;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.PointD;

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

    public static String toString(InputStream is){
        if(is == null){
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

    public static ArrayList toHashMap(InputStream is){
        ArrayList<HashMap<String , String>> json= new ArrayList<>();

        if(is == null){
            return null;
        }

        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is,"UTF-8"));
            jsonReader.setLenient(true);
            jsonReader.beginArray();
            while(jsonReader.hasNext()){
                json.add(new HashMap<String, String>());
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    json.get(json.size()-1).put(jsonReader.nextName(),jsonReader.nextName());
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


    public static ArrayList<Challenge.Builder> toChallenges(InputStream is){
        ArrayList<Challenge.Builder> challenges = new ArrayList<>();
        Challenge.Builder builder;

        if(is == null){
            return null;
        }

        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is,"UTF-8"));
            PointD location;
            jsonReader.setLenient(true);
            jsonReader.beginArray();
            while(jsonReader.hasNext()){
                jsonReader.beginObject();
                builder = new Challenge.Builder();
                while (jsonReader.hasNext()) {
                    switch(jsonReader.nextName()){
                        case "challenge_name":
                            builder.setChallengeName(jsonReader.nextString());
                            break;
                        case "start_point":
                            location = new PointD(jsonReader.nextString());
                            builder.setStartLocation(location.toLatLng());
                            break;
                        case "check_points":
                            builder.setCheckpointLocations(PointD.getPoints(jsonReader.nextString()));
                            break;
                        case "finish_point":
                            location = new PointD(jsonReader.nextString());
                            builder.setStopLocation(location.toLatLng());
                            break;
                        default:
                            Toast.makeText(Transferor.context,"WTFFFFF",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                challenges.add(builder);
                jsonReader.endObject();
            }
            jsonReader.endArray();
            jsonReader.close();

        } catch (IOException e) {
            Toast.makeText(Transferor.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        return challenges;
    }
}
