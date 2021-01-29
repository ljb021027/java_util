package com.MyUtils.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujiabei
 * @since 2018/11/5
 */
public class ThreadUtil {

    private Map<Integer,Runnable> map;


    public static ThreadUtil NewThreadUtil(){
        ThreadUtil t = new ThreadUtil();
        t.map = new ConcurrentHashMap<>();
        return t;
    }

}
