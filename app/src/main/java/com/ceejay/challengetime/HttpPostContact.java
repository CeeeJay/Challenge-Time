package com.ceejay.challengetime;

import android.os.Bundle;
import android.os.StrictMode;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.helper.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Development on 10.01.2015.
 *
 */
public class HttpPostContact {
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpResponse response;
    private String url;

    public HttpPostContact(String url) {
        this.url = url;
        connect();
    }

    private void connect(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(url);
    }

    public InputStream send( Bundle bundle ){
        List<NameValuePair> pairs = new ArrayList<>();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            pairs.add(new BasicNameValuePair(key, value.toString()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return entity.getContent();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void reciveChallanges( ArrayList<Challenge> challenges ){

        HttpPostContact contact = new HttpPostContact("http://192.168.178.25/ChallengeTime/contact2.php");
        String[] strings = new String[]{"receive_run_challenges","receive_checkpoint_challenges"};

        for(String string : strings) {
            Bundle bundle = new Bundle();
            bundle.putString("method", string);
            for (Challenge.Builder builder : Stream.toChallenges(contact.send(bundle))) {
                switch (string){
                    case "receive_run_challenges":
                        challenges.add(builder.getRunChallenge());
                        break;
                    case "receive_checkpoint_challenges":
                        challenges.add(builder.getCheckpointChallenge());
                        break;
                }
            }
        }
    }

}
