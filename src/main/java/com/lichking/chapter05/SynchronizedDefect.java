package com.lichking.chapter05;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/30 0030
 * Description: No Description
 */
public class SynchronizedDefect {
    public synchronized void syncMethod(){
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect synchronizedDefect = new SynchronizedDefect();
        Thread t1 = new Thread(synchronizedDefect::syncMethod,"T1");
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(synchronizedDefect::syncMethod,"T2");
        t2.start();


        TimeUnit.MILLISECONDS.sleep(10);
        t2.interrupt();
        System.out.println(t2.isInterrupted());
        System.out.println(t2.getState());//BLOCKED  证明synchronized同步线程无法被打断
    }
}
