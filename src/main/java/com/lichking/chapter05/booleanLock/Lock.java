package com.lichking.chapter05.booleanLock;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/30 0030
 * Description: No Description
 */
public interface Lock {
    /**
     * 方法永远阻塞 除非获取到锁 可以被中断
     * @throws InterruptedException
     */
    void lock() throws InterruptedException;

    /**
     * 可以被中断 增加了超时功能
     * @param mills
     * @throws InterruptedException
     * @throws TimeoutException
     */
    void lock(long mills) throws InterruptedException,TimeoutException;


    /**
     * 锁释放
     */
    void unlock();

    /**
     * 获取当前被阻塞的线程
     * @return
     */
    List<Thread> getBlockThreads();
}
