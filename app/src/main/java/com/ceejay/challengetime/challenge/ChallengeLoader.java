package com.ceejay.challengetime.challenge;

import android.os.Bundle;
import android.util.Log;

import com.ceejay.challengetime.helper.HttpPostContact;
import com.ceejay.challengetime.helper.Stream;

import java.io.InputStream;

/**
 * Created by CJay on 18.04.2015 for Challenge Time.
 */
public class ChallengeLoader {
    public final static String TAG = ChallengeLoader.class.getSimpleName();

    public ChallengeLoader() {
        Log.i(TAG, "Test");
        HttpPostContact contact = new HttpPostContact("http://192.168.178.55/challanges/brunnen.challenge");
        InputStream stream = contact.send(new Bundle());
        Stream.test(stream);
    }
}




