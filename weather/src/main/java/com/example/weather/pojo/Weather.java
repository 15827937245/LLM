package com.example.weather.pojo;

import java.util.List;

public class Weather {
    private String city;
    private String upadtetime;
    private int wendu;
    private String fengli;
    private int shidu;
    private String fengxiang;
    private String sunrise_1;
    private String sunset_1;
    private List<Forecast> forecasts;
    private List<Zhishu> zhishus;

    public Weather() {
    }

    public Weather(String city, String upadtetime, int wendu, String fengli, int shidu, String fengxiang, String sunrise_1, String sunset_1, List<Forecast> forecasts, List<Zhishu> zhishus) {
        this.city = city;
        this.upadtetime = upadtetime;
        this.wendu = wendu;
        this.fengli = fengli;
        this.shidu = shidu;
        this.fengxiang = fengxiang;
        this.sunrise_1 = sunrise_1;
        this.sunset_1 = sunset_1;
        this.forecasts = forecasts;
        this.zhishus = zhishus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpadtetime() {
        return upadtetime;
    }

    public void setUpadtetime(String upadtetime) {
        this.upadtetime = upadtetime;
    }

    public int getWendu() {
        return wendu;
    }

    public void setWendu(int wendu) {
        this.wendu = wendu;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public int getShidu() {
        return shidu;
    }

    public void setShidu(int shidu) {
        this.shidu = shidu;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getSunrise_1() {
        return sunrise_1;
    }

    public void setSunrise_1(String sunrise_1) {
        this.sunrise_1 = sunrise_1;
    }

    public String getSunset_1() {
        return sunset_1;
    }

    public void setSunset_1(String sunset_1) {
        this.sunset_1 = sunset_1;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public List<Zhishu> getZhishus() {
        return zhishus;
    }

    public void setZhishus(List<Zhishu> zhishus) {
        this.zhishus = zhishus;
    }
}

class Forecast{
    private String date;
    private String hight;
    private String low;
    private String type;

    public Forecast(String date, String hight, String low, String type) {
        this.date = date;
        this.hight = hight;
        this.low = low;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
class Zhishu{
    private String name;
    private String value;
    private String detail;

    public Zhishu(String name, String value, String detail) {
        this.name = name;
        this.value = value;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}