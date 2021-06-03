package com.example.thread.synchronizedDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
private String TAG = "llm_Test";
    private Bank bank;
    private Person person1;
    private Person person2;
    private Lock lock;

    public void test(){
        for (int i = 0; i <10 ; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("线程"+ finalI);
                    while (bank.withdrawMoney(10)){
                       //Log.i(TAG, Thread.currentThread().getName()+"取钱，此时"+bank.getBankName()+"还剩："+bank.getMoney()+"元");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public void init(){
        bank = new Bank(1000, "中国银行");
        person1 = new Person("张三", 50);
        person2 = new Person("李四", 50);
        lock = new ReentrantLock();
    }


    //类锁
    public void classLock(){
            new Thread(() -> {
                while (person1.saveMoney()){
                }
            }).start();

        new Thread(() -> {
            while (person2.saveMoney()){
            }
        }).start();
    }
    //对象锁
    public void instanceLock(){
        new Thread(() -> {
            while (person1.saveMoney2()){
            }
        }).start();

        new Thread(() -> {
            while (person2.saveMoney2()){
            }
        }).start();
    }

}
