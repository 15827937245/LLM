package com.example.weather.utils;

import android.util.Log;

import com.example.weather.pojo.WeatherInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonParsing {
    public static WeatherInfo jsonParsing(String string){
        try {
            JSONObject root = new JSONObject(string);
            JSONObject weatherinfo = root.getJSONObject("weatherinfo");
            Gson gson = new Gson();
           return gson.fromJson(weatherinfo.toString(), WeatherInfo.class);
           /* Iterator<String> keys = weatherinfo.keys();
           while (keys.hasNext()){
           }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
