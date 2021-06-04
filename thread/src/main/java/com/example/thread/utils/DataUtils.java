package com.example.thread.utils;

public class DataUtils {

    /**
     * 中国银行
     * */
    private final String BANK_ZGYH = "ZGYH";
    private int ZGYHBankId = 0;

    /**
     * 建设银行
     * */
    private final String BANK_JSYH = "JSYH";
    private int JSYHBankId = 0;


    private final String USER = "USER";
    private int personId = 0;

    private static final DataUtils instance = new DataUtils();
    private DataUtils(){}

    public static DataUtils getInstance(){
        return instance;
    }


    public   String addZGYHBank(){
        synchronized ((Object) ZGYHBankId){
            ZGYHBankId++;
        }
        return BANK_ZGYH+ZGYHBankId;
    }

    public  String addJSYHBank(){
        synchronized ((Object) JSYHBankId){
            JSYHBankId++;
        }
        return BANK_JSYH+JSYHBankId;
    }

    public  String addUser(){
        synchronized ((Object) personId){
            personId++;
        }
        return USER+personId;
    }



}
