package com.example.weather.utils;


import com.example.weather.pojo.Weather;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParsing {
    private static final XmlParsing instance = new XmlParsing();

    private XmlParsing(){}

    public static XmlParsing getInstance(){
        return instance;
    }

    public Map cityParsing(File file){
        Map countyMap = new HashMap();
        Map cityMap = new HashMap();
        Map provinceMap = new HashMap();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList province = root.getElementsByTagName("province");
            for (int i = 0; i <province.getLength() ; i++) {
            //for (int i = 0; i <1 ; i++) {
                Element cityElement = (Element)province.item(i);
                NodeList city = cityElement.getElementsByTagName("city");
                //System.out.println(city.getLength());
                for (int j = 0; j <city.getLength() ; j++) {
                    Element countyElement = (Element)city.item(j);
                    NodeList county = countyElement.getElementsByTagName("county");
                    for (int k = 0; k <county.getLength() ; k++) {
                        countyMap.put(county.item(k).getAttributes().getNamedItem("name").getNodeValue(),county.item(k).getAttributes().getNamedItem("weatherCode").getNodeValue());
                    }
                    HashMap<Object, Object> map = new HashMap<>();
                    map.putAll(countyMap);
                    cityMap.put(city.item(j).getAttributes().getNamedItem("name").getNodeValue(),map);
                    countyMap.clear();
                }
                HashMap<Object, Object> map = new HashMap<>();
                map.putAll(cityMap);
                provinceMap.put(province.item(i).getAttributes().getNamedItem("name").getNodeValue(),map);
                cityMap.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceMap;
    }


    public void weatherParsing(String filepath){
        Weather weather = new Weather();

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(filepath);
            Element root = document.getDocumentElement();

            NodeList city = root.getElementsByTagName("city");
            //System.out.println(city.item(0).getFirstChild().getNodeValue());
            weather.setCity(city.item(0).getFirstChild().getNodeValue());


            NodeList updatetime = root.getElementsByTagName("updatetime");
            weather.setUpadtetime(updatetime.item(0).getFirstChild().getNodeValue());
            //System.out.println(updatetime.item(0).getFirstChild().getNodeValue());

            NodeList wendu = root.getElementsByTagName("wendu");
            weather.setWendu(Integer.valueOf(wendu.item(0).getFirstChild().getNodeValue()));

//            NodeList fengli = root.getElementsByTagName("fengli");
//            for (int i = 0; i <fengli.getLength() ; i++) {
//                System.out.println(fengli.item(i).getFirstChild().getNodeValue());
//            }
            NodeList shidu = root.getElementsByTagName("shidu");
            System.out.println(shidu.item(0).getFirstChild().getNodeValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
