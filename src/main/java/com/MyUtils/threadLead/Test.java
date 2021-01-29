package com.MyUtils.threadLead;

import com.MyUtils.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiabei
 * @since 2018/11/5
 */
public class Test {
    
    public static void main(String[] args){
        ThreadPoolManager t = new ThreadPoolManager(6);

        List<Runnable> taskList = new ArrayList<Runnable>();

        for (int i = 0; i < 100; i++) {
            t.exec(new Task());

        }

        System.out.println(t);
    }

    //任务类
    static class Task implements Runnable {

        private static volatile int i = 1;

        @Override
        public void run() {
            System.out.println("当前处理的线程是：" + Thread.currentThread().getName() + " 执行任务" + (i++) + " 完成");
        }
    }
}
