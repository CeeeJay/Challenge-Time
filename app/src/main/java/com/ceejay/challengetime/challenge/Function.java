package com.ceejay.challengetime.challenge;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Function {
    public final static String TAG = Function.class.getSimpleName();

    public Challenge context;
    public String trigger;
    public String effect;
    public String back;

    public Function() {
    }

    public Function(Challenge context) {
        this.context = context;
    }

    public Function(String trigger, Challenge context) {
        this.trigger = trigger;
        this.context = context;
    }

    public void setTrigger( String trigger ){
        this.trigger = trigger;
    }

    public void call(){

    }

}




