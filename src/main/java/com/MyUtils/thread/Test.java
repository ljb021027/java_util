package com.MyUtils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author liujiabei
 * @since 2018/11/5
 */
public class Test {

    public static void main(String[] args) {
//        ThreadPool t = ThreadPoolManager.getThreadPool(6);

        ExecutorService t = Executors.newCachedThreadPool();

        List<Runnable> taskList = new ArrayList<Runnable>();

        for (int i = 0; i < 100; i++) {
//            taskList.add(new Task());
            t.execute(new Task());
//            System.out.println(t);
        }
//        t.execute(taskList);
//        System.out.println(t);
//        t.destory();//所有线程执行完才destory
        System.out.println(t);


    }

    //任务类
    static class Task implements Runnable {

//        private static volatile int i = 1;

        private static AtomicInteger i =  new AtomicInteger();


        @Override
        public void run() {
            System.out.println("当前处理的线程是：" + Thread.currentThread().getName() + " 执行任务" + (i.incrementAndGet()) + " 完成");
        }
    }
}
