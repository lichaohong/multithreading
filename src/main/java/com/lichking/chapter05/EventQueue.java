package com.lichking.chapter05;

import java.util.LinkedList;

import static java.lang.Thread.currentThread;

/**
 * Created with IntelliJ IDEA.
 * User: LichKing
 * Date: 2018/11/30 0030
 * Description: 当多个线程执行 take 或 offer 方法时 会出现数据不一致情况
 *              修改的话  只需要把 if改为while notify改为notifyAll 即可
 */
public class EventQueue {
    private final int max;

    public EventQueue(int max) {
        this.max = max;
    }

    static class Event{}

    private final LinkedList<Event> eventQueue = new LinkedList<>();
    private final static int DEFAULT_MAX_EVENT = 10;

    public EventQueue(){
        this(DEFAULT_MAX_EVENT);
    }

    //装载
    public void offer(Event event){
        synchronized (eventQueue){
            if(eventQueue.size() >= max){
                try {
                    console("列表已满");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            console("列表未满，可以提交，共 "+eventQueue.size()+" 个");
            eventQueue.addLast(event);
            eventQueue.notify();
        }
    }

    //取数
    public Event take(){
        synchronized (eventQueue){
            if(eventQueue.isEmpty()){
                try {
                    console("列表为空");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Event event = eventQueue.removeFirst();
            eventQueue.notify();
            console("事件已取到");
            return event;
        }
    }

    private void console(String message){
        System.out.printf("%s:%s\n",currentThread().getName(),message);
    }
}
