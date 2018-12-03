package com.lichking.chapter05.booleanLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/30 0030
 * Description: No Description
 */
public class BooleanLock implements Lock{
    private Thread currentThread;
    private boolean locked = false;
    private final List<Thread> blockedList = new ArrayList<>();

    @Override
    public void lock() throws InterruptedException {
        synchronized (this){
            while (locked){//如果当前锁已被某个线程获取
                blockedList.add(currentThread());
                this.wait();
            }
            blockedList.remove(currentThread());
            locked = true;
            currentThread = currentThread();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized(this){
            if(mills<=0){
                this.lock();
            }else{
                long remainMills = mills;//剩余时间
                long endMills = currentTimeMillis()+remainMills;
                while (locked){//当前锁已被其他线程获取
                    while (endMills<=0){
                        throw new TimeoutException("在"+mills+"秒内不能获取到锁");
                    }
                    if(!blockedList.contains(currentThread())){
                        blockedList.add(currentThread());
                    }
                    this.wait(remainMills);
                    remainMills = endMills - currentTimeMillis();//重新计算剩余时间
                }
                blockedList.remove(currentThread());
                this.locked = true;
                this.currentThread = currentThread();
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (this){
            if(currentThread == currentThread()){
                this.locked = false;
                Optional.of(currentThread().getName()+"释放锁").ifPresent(System.out::println);
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockThreads() {
        return Collections.unmodifiableList(blockedList);
    }
}
