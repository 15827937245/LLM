package com.example.thread.synchronizedDemo;

import android.util.Log;

public class Person {
    private String TAG = "llm_Person";
    private String name;
    private int money;

    public Person(String name, int money) {
        this.name = name;
        this.money = money;
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

    public boolean saveMoney(){
        if (this.money>=10){
            synchronized (this){
                this.money-=10;
            Log.i(TAG, this.getName()+"存入10元，此时还剩"+this.money+"元");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        return true;
        }else
            return false;
    }

    public boolean saveMoney2(){
        if (this.money>=10){
            synchronized (Person.class){
                this.money-=10;
            Log.i(TAG, this.getName()+"存入10元，此时还剩"+this.money+"元");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }else
            return false;
    }
}
