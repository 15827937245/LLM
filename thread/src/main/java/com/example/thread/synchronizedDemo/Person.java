package com.example.thread.synchronizedDemo;

import android.util.Log;

import com.example.thread.utils.DataUtils;

import java.util.ArrayList;

public class Person {
    private String TAG = "llm_Person";
    private String id;
    private String name;
    private int money;
    private ArrayList<Bank> bankList;

    public Person(String name, int money) {
        this.id = DataUtils.getInstance().addUser();
        this.name = name;
        this.money = money;
        bankList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public synchronized boolean addBank(int Bank,int saveMoney,String name){
        if (saveMoney<this.money){
            return false;
        }else {
            this.money-=saveMoney;
            Bank bank = new Bank(Bank,saveMoney,name);
            bankList.add(bank);
            return true;
        }
    }

    public ArrayList<Bank> getBankList() {
        return bankList;
    }

    public void setBankList(ArrayList<Bank> bankList) {
        this.bankList = bankList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
