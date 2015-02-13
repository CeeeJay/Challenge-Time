package com.ceejay.challengetime.helper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by CJay on 13.02.2015 for Challenge Time.
 */
public class ExpandedList<E> extends ArrayList<E>{
    public final static String TAG = ExpandedList.class.getSimpleName();

    public ExpandedList(int capacity) {
        super(capacity);
    }

    public ExpandedList() {
    }

    public ExpandedList(Collection<? extends E> collection) {
        super(collection);
    }

    public E getLast(){
        return get(size()-1);
    }
}




