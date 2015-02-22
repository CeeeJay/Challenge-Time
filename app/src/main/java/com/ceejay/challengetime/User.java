package com.ceejay.challengetime;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.ceejay.challengetime.launcher.LauncherActivity;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by CJay on 22.02.2015 for Challenge Time.
 */
public class User {
    public final static String TAG = User.class.getSimpleName();

    private static Context context = LauncherActivity.getAppContext();

    public static int id = -1;

    public static String firstName;
    public static String lastName;
    public static String name;

    public static Bitmap picture;

    public enum LoginMethod{
        NONE,EMAIL,GOOGLE,FACEBOOK;

        public static LoginMethod getLoginMethod( String method ){
            method = method.toUpperCase();
            switch (method){
                case "EMAIL":
                    return LoginMethod.EMAIL;
                case "GOOGLE":
                    return LoginMethod.GOOGLE;
                case "FACEBOOK":
                    return LoginMethod.FACEBOOK;
                default:
                    return LoginMethod.NONE;
            }
        }
    }

    public static void fetch(){
        switch (getLoginMethod()) {
            case FACEBOOK:
                new Request(
                    Session.getActiveSession(),
                    "/me",
                    null,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {
                        try {
                            GraphObject list = response.getGraphObject();
                            JSONObject json = list.getInnerJSONObject();
                            User.id = json.getInt("id");
                            User.name = json.getString("name");
                            User.firstName = json.getString("first_name");
                            User.lastName = json.getString("last_name");
                            User.save();

                            for (UserDataChangedListener l : listeners) {
                                l.onChange();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                    }
                ).executeAsync();
                break;
        }
    }

    public static void read(){
        SharedPreferences settings = context.getSharedPreferences("user_data",0);
        id = settings.getInt("id", -1 );
        firstName = settings.getString("first_name", null);
        lastName = settings.getString("last_name", null);
        if( firstName != null && lastName != null ) {
            name = firstName + " " + lastName;
        }else{
            name = settings.getString("name", null);
        }
    }

    public static void save(){
        SharedPreferences settings = context.getSharedPreferences("user_data", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", id);
        editor.putString("first_name", User.firstName);
        editor.putString("last_name", User.lastName);
        editor.putString("name", User.name);
        editor.apply();
    }

    public static void setLoginMethod( LoginMethod loginMethod ){
        SharedPreferences settings = context.getSharedPreferences("user_data", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("login_method", loginMethod.toString() );
        editor.apply();
    }

    public static LoginMethod getLoginMethod(){
        SharedPreferences settings = context.getSharedPreferences("user_data", 0);
        return LoginMethod.getLoginMethod(settings.getString("login_method", "none"));
    }

    public static ArrayList<UserDataChangedListener> listeners = new ArrayList<>();
    public static interface UserDataChangedListener{
        public void onChange();
    }
    public static void addUserDataChangedListener( UserDataChangedListener l ){
        listeners.add(l);
    }
    public static void removeUserDataChangedListener( UserDataChangedListener l ){
        listeners.remove(l);
    }
    public static void clearUserDataChangedListener( ){
        listeners.clear();
    }
}




