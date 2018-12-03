package com.lichking.chapter04;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/28 0028
 * Description: No Description
 */
public class TicketWindowRunnable implements Runnable{

    private int index = 1;
    private final static int MAX = 500;
    private final static Object object = new Object();

    @Override
    public void run() {
        synchronized(object){
            while(index<=MAX){
                System.out.println(Thread.currentThread()+" 的号码是："+(index++));
            }
        }
    }


    public static void main(String[] args) {
        TicketWindowRunnable task = new TicketWindowRunnable();
        Thread t1 = new Thread(task,"一号窗口");
        Thread t2 = new Thread(task,"二号窗口");
        Thread t3 = new Thread(task,"三号窗口");
        Thread t4 = new Thread(task,"四号窗口");
        t1.start();
        t2.start();
        t3.start();
        t4.start();



    }
}
