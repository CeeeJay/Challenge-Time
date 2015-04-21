package com.ceejay.challengetime.launcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.User;
import com.ceejay.challengetime.helper.HttpPostContact;
import com.ceejay.challengetime.main.MainActivity;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import java.io.IOException;
import java.net.URL;

/**
 * Created by CJay on 09.02.2015 for Challenge Time.
 */
public class LauncherActivity extends FragmentActivity implements Runnable{
    public final static String TAG = LauncherActivity.class.getSimpleName();

    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    //public final String API_KEY = "1550352745219115";
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

        context = this;

        User.read();

        if( User.id == -1 ){
            findViewById(R.id.authFacebook).setVisibility(View.VISIBLE);
            findViewById(R.id.authGoogle).setVisibility(View.VISIBLE);
        }else{
            startMainActivity();
        }

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        findViewById(R.id.launcherIcon).startAnimation(AnimationUtils.loadAnimation(this, R.anim.launch_animation));
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            User.setLoginMethod(User.LoginMethod.FACEBOOK);

            if(User.id == -1) {
                User.addUserDataChangedListener( new User.UserDataChangedListener() {
                    @Override
                    public void onChange() {
                        if( User.id > 0 ) {
                            startMainActivity();
                            User.removeUserDataChangedListener(this);
                        }
                    }
                });
                User.fetch();
            }
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
    protected void onStop() {
        super.onStop();
        uiHelper.onStop();
    }

    @Override
    public void run() {
        try {
            //HttpPostContact.reciveChallanges(ChallengeAdapter.challenges);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMainActivity(){
        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        finish();
    }

    private synchronized void downloadAvatar() {
        AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {

            @Override
            public Bitmap doInBackground(Void... params) {
                URL fbAvatarUrl = null;
                Bitmap fbAvatarBitmap = null;
                try {
                    fbAvatarUrl = new URL("http://graph.facebook.com/4/picture?type=large");
                    fbAvatarBitmap = BitmapFactory.decodeStream(fbAvatarUrl.openConnection().getInputStream());
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return fbAvatarBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                User.picture = result;
            }

        };
        task.execute();
    }
}




