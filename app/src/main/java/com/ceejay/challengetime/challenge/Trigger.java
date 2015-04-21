package com.ceejay.challengetime.challenge;

/**
 * Created by CJay on 21.04.2015 for Challenge Time.
 */
public class Trigger {
    public final static String TAG = Trigger.class.getSimpleName();

    public Challenge context;
    public String trigger;
    public String effect;

    public Trigger(  ) {
    }

    public Trigger( Challenge context  ) {
        this.context = context;
    }

    public Trigger( String trigger , Challenge context  ) {
        this.trigger = trigger;
        this.context = context;
    }

    public void setTrigger( String trigger ){
        this.trigger = trigger;
    }

    public void trigger(){

    }

}




