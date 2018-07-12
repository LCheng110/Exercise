package com.inno.home.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class JsonFactory {
    public static String getJson(Map<String, String> map) {
        JSONObject object = new JSONObject();
        try {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                object.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
