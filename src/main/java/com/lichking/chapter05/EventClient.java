package com.lichking.chapter05;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/30 0030
 * Description: 当多个线程执行 take 或 offer 方法时 会出现数据不一致情况
 */
public class EventClient {
    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();
        new Thread(()->{
            for (;;){
                eventQueue.offer(new EventQueue.Event());
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(0,10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"producer").start();
        new Thread(()->{
            for (;;){
                eventQueue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(0,10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer").start();
    }
}
