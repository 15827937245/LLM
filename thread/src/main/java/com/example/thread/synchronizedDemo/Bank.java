package com.example.thread.synchronizedDemo;


import com.example.thread.utils.DataUtils;

public class Bank {
    private String TAG = "llm_Test";
    private String id;
    private int money;
    private String bankName;

    public Bank(int BANK,int money, String bankName) {
        switch (BANK){
            case 0:this.id = DataUtils.getInstance().addZGYHBank();
            case 1:this.id = DataUtils.getInstance().addJSYHBank();
            default:this.id = DataUtils.getInstance().addZGYHBank();
        }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
