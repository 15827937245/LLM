package com.example.weather.pojo;

import com.example.weather.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class City {
    Map provinceMap;
    Map cityMap;
    Map countyMap;

    public City(Map map) {
        this.provinceMap = map;
    }

    public List getProvinceList(){
        List<String> provinces = new ArrayList<String>();
        for (Object obj:this.provinceMap.keySet()) {
            provinces.add((String) obj);
        }
        return provinces;
    }

    public List getCityList(String province){
        List<String> cities = new ArrayList<String>();
        cityMap = (Map)provinceMap.get((Object)province);
        //System.out.println(cityMap.size());
        for (Object obj:cityMap.keySet()) {
            cities.add((String) obj);
        }
        return cities;
    }

    public List getCountyList(String province,String city){
        List<String> counties = new ArrayList<String>();
        cityMap = (Map)provinceMap.get((Object)province);
        countyMap = (Map)cityMap.get((Object)city);
        for (Object obj:countyMap.keySet()) {
            counties.add((String) obj);
        }
        return counties;
    }

    public String  getWeatherCode(String province,String city,String county){
        cityMap = (Map)provinceMap.get((Object)province);
        countyMap = (Map)cityMap.get((Object)city);
       return (String) countyMap.get((Object)county);
    }

    public String getRandomWeatherCode(){
        String province = (String) getProvinceList().get(new Random().nextInt(getProvinceList().size()));
        String city = (String)getCityList(province).get(new Random().nextInt(getCityList(province).size()));
        String county = (String) getCountyList(province,city).get(new Random().nextInt(getCountyList(province,city).size()));
        return getWeatherCode(province,city,county);
    }
}
