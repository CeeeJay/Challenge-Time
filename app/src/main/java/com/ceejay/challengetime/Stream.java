package com.ceejay.challengetime;

import android.util.JsonReader;
import android.widget.Toast;

import com.ceejay.challengetime.challenge.RunChallenge;
import com.ceejay.challengetime.helper.PointD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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


    public static ArrayList<RunChallenge> toChallenges(InputStream is){
        ArrayList<RunChallenge> challenges = new ArrayList<>();
        RunChallenge challenge;

        if(is == null){
            return null;
        }

        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is,"UTF-8"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PointD location;
            jsonReader.setLenient(true);
            jsonReader.beginArray();
            while(jsonReader.hasNext()){
                jsonReader.beginObject();
                challenge = new RunChallenge();
                while (jsonReader.hasNext()) {
                    switch(jsonReader.nextName()){
                        case "challenge_name":
                            challenge.setChallengeName(jsonReader.nextString());
                            break;
                        case "start_point":
                            location = new PointD(jsonReader.nextString());
                            challenge.setStartLocation(location.toLatLng());
                            break;
                        case "finish_point":
                            location = new PointD(jsonReader.nextString());
                            challenge.setStopLocation(location.toLatLng());
                            break;
                        default:
                            Toast.makeText(Transferor.context,"WTFFFFF",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                challenges.add(challenge);
                jsonReader.endObject();
            }
            jsonReader.endArray();
            jsonReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return challenges;
    }
}
