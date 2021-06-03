package com.example.thread.synchronizedDemo;

import android.util.Log;

public class Bank {
    private String TAG = "llm_Test";
    private int money;
    private String bankName;

    public Bank(int money, String bankName) {
        this.money = money;
        this.bankName = bankName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean withdrawMoney(int count){
        if (this.money>0) {
            if (money>=count){
                synchronized ((Object) this.money){
                    this.money-= count;
                    Log.i(TAG, Thread.currentThread().getName()+"取钱，此时"+getBankName()+"还剩："+getMoney()+"元");
                }
        return true;}else {
                return false;
            }
        }else {
            return false;
        }
    }
}
