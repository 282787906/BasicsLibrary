package com.liqg.library.log;


import java.util.LinkedList;

public class LogQueueUtils {

    private static int maxSize = 10;
    /**
     * 日志队列
     */
    static LinkedList<String> linkedList = new LinkedList();


    public static synchronized void set(String msg) {
        linkedList.add(msg);
        if (linkedList.size() > maxSize) {
            linkedList.poll();
        }
    }

    public static synchronized LinkedList<String> get() {
        return linkedList;
    }
}
