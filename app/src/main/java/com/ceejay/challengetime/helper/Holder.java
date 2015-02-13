package com.ceejay.challengetime.helper;

/**
 * Created by CJay on 13.02.2015 for Challenge Time.
 */
public class Holder<E>{

    private E holder;

    public Holder( E holder ) {
        this.holder = holder;
    }

    public void setValue( E holder ){
        this.holder = holder;
    }

    public E getValue(){
        return holder;
    }
}




