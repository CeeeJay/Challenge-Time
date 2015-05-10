package com.ceejay.challengetime.challenge;

import java.util.regex.Pattern;

/**
 * Created by CJay on 25.04.2015 for Challenge Time.
 */
public class PatternType {
    public final static String TAG = PatternType.class.getSimpleName();

    public static Pattern
            latLng = Pattern.compile("^(?:LatLng|Latlng|latLng|latlng)\\(([0-9]+[.]*[0-9]*+)\\s*(?:\\s+|,)\\s*([0-9]+[.]*[0-9]*+)\\)$"),
            number = Pattern.compile("^\\-?[0-9]+$"),
            object = Pattern.compile("(\\S+)\\.(\\S+)"),
            function = Pattern.compile("(?:(\\S+)#)(\\S+)\\(\\)"),
            variable = Pattern.compile("(\\S+)#(\\S+)"),
            userInArea = Pattern.compile("user#(\\S+)\\s+(->|<-)\\s+area#(\\S+)"),
            awl2 = Pattern.compile("(&&|\\|\\||\\|&|&&\\(|\\|\\|\\()\\s*(\\s+)"),
            awl = Pattern.compile("\\S*(U|O|X|U\\(|O\\(|X\\()\\s+(\\S+)\\s+"),
            time = Pattern.compile("(?:(?:(?:(\\d):)?(\\d):(\\d))|(?:(\\d)h)?(?:(\\d)m)?(?:(\\d)s)?(?:(\\d)ms)?)");

}




