package com.example.mycamrea;

import org.junit.Test;

public class Test2 {
    public static int tickets =  100000;

    @Test
    public void  test(){
        long l = System.currentTimeMillis();
        TicketsSale test = new TicketsSale();
        for (int i = 0; i <3 ; i++) {
            new Thread(()->{
                //System.out.println(Thread.currentThread().getName()+"卖出去了一张票");
                while (test.sale()){
                    //System.out.println(Thread.currentThread().getName()+"卖出去了一张票");
                }
                System.out.println("执行时间为:"+(System.currentTimeMillis()-l));
            },"count"+i).start();
        }

       // System.out.println("执行时间为:"+(System.currentTimeMillis()-l));
    }
}
