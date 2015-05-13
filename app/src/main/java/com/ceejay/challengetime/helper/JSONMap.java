package com.ceejay.challengetime.helper;

import java.util.HashMap;

/**
 * Created by CJay on 10.05.2015 for Challenge Time.
 */
public class JSONMap<V> extends HashMap<String,V> {
    public final static String TAG = JSONMap.class.getSimpleName();

    public JSONMap() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (!this.isEmpty()) {
            for (String key : this.keySet()) {
                sb.append('"' + key + "\" :");
                if (this.get(key) instanceof String) {
                    sb.append('"' + this.get(key).toString() + "\",");
                } else {
                    sb.append(this.get(key).toString() + ',');
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}




