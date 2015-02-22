package com.ceejay.challengetime.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.helper.ChallengeAdapter;
import com.ceejay.challengetime.helper.HttpPostContact;
import com.ceejay.challengetime.main.MainActivity;
import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.TokenCachingStrategy;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by CJay on 09.02.2015 for Challenge Time.
 */
public class LauncherActivity extends FragmentActivity implements Runnable{
    public final static String TAG = LauncherActivity.class.getSimpleName();

    private LauncherActivity mainFragment;
    private LoginMethod loginMethod = LoginMethod.NONE;

    public final String API_KEY = "1550352745219115";
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    private UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);

        loginMethod = getLoginMethod();

        switch (loginMethod){
            case FACEBOOK:
                Session.openActiveSession(this,true,new Session.StatusCallback() {
                    @Override
                    public void call(Session session, SessionState sessionState, Exception e) {

                    }
                });
                break;
        }

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        findViewById(R.id.launcherIcon).startAnimation(AnimationUtils.loadAnimation(this, R.anim.launch_animation));
        findViewById(R.id.launcherIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.launcherIcon).startAnimation(AnimationUtils.loadAnimation(LauncherActivity.this, R.anim.spin));
            }
        });
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            setLoginMethod(LoginMethod.FACEBOOK);
            startMainActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void run() {
        Challenge.setContext(this);
        try {
            HttpPostContact.reciveChallanges(ChallengeAdapter.challenges);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMainActivity(){
        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        finish();
    }

    enum LoginMethod{
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

    public void setLoginMethod( LoginMethod loginMethod ){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("login_data", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("login_method", loginMethod.toString() );
        editor.apply();
    }

    public LoginMethod getLoginMethod(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("login_data", 0);
        return LoginMethod.getLoginMethod(settings.getString("login_method", "none"));
    }
}




