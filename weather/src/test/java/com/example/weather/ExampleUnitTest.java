package com.example.weather;

import com.example.weather.pojo.City;
import com.example.weather.utils.JsonParsing;
import com.example.weather.utils.XmlParsing;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        //Map map = XmlParsing.getInstance().cityParsing("C:\\Users\\lmlip\\AndroidStudioProjects\\LLMDemo\\city.xml");
        //City city = new City(map);
        //System.out.println(city.getProvinceList().toString());
//         System.out.println(city.getCityList("湖北").toString());
//         System.out.println(city.getCountyList("北京", "北京").toString());
//         System.out.println(city.getCountyList("湖北", "武汉").toString());
//         System.out.println(city.getWeatherCode("湖北", "仙桃", "仙桃"));
         //System.out.println(city.getWeatherCode("广东", "深圳", "深圳"));
         //System.out.println(city.getCountyList("广东", "深圳"));
//        http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100
         //XmlParsing.getInstance().weatherParsing("C:\\Users\\lmlip\\AndroidStudioProjects\\LLMDemo\\weather.xml");
        //XmlParsing.getInstance().weatherParsing("C:\\Users\\lmlip\\AndroidStudioProjects\\LLMDemo\\city.xml");
        String string = "{\"weatherinfo\":{\"city\":\"北京\",\"cityid\":\"101010100\",\"temp1\":\"18℃\",\"temp2\":\"31℃\",\"weather\":\"多云转阴\",\"img1\":\"n1.gif\",\"img2\":\"d2.gif\",\"ptime\":\"18:00\"}}\n";
        JsonParsing.jsonParsing(string);
    }
}