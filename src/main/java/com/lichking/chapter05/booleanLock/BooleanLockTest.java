package com.lichking.chapter05.booleanLock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/30 0030
 * Description: No Description
 */
public class BooleanLockTest {
    private final Lock lock = new BooleanLock();
    public  synchronized void syncMethod()  {
        try {
            lock.lock();
            int randomInt = ThreadLocalRandom.current().nextInt(5);
            System.out.println(currentThread().getName()+"获取锁");
            TimeUnit.SECONDS.sleep(randomInt);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        BooleanLockTest test = new BooleanLockTest();
//        IntStream.range(0,5).mapToObj(i->new Thread(test::syncMethod)).forEach(Thread::start);
        new Thread(test::syncMethod,"T1").start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(test::syncMethod,"T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        t2.interrupt();

    }





}
