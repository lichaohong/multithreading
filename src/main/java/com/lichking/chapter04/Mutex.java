package com.lichking.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/28 0028
 * Description: No Description
 */
public class Mutex {
    private final static Object MUTEX = new Object();
    public void accessResource(){
        synchronized (MUTEX){
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        final Mutex mutex = new Mutex();
        for (int i = 0; i < 5; i++) {
//            new Thread(mutex::accessResource).start();
            new Thread(()->mutex.accessResource()).start();
        }
    }
}
