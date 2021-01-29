package com.MyUtils.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liujiabei
 * @since 2018/11/5
 */
public class ThreadPoolManager implements ThreadPool {


    //线程池中默认线程的个数为5
    private static int workerNum = 5;

    //工作线程数组
    WorkThead[] workTheads;

    //执行任务数量
    private static volatile int executeTaskNumber = 0;

    //任务队列 作为一个缓冲 List线程不安全
    private List<Runnable> taskQueue = new LinkedList<Runnable>();

    //private BlockingQueue<Runnable> taskQueue = new
    //ArrayBlockingQueue<Runnable>(1024);

    private static ThreadPoolManager threadPool;

    private AtomicLong threadNum = new AtomicLong();

    private ThreadPoolManager() {
        this(workerNum);
    }

    private ThreadPoolManager(int workerNum) {

        if (workerNum > 0) {
            ThreadPoolManager.workerNum = workerNum;
        }

        //工作线程初始化
        workTheads = new WorkThead[workerNum];

        for (int i = 0; i < ThreadPoolManager.workerNum; i++) {
            workTheads[i] = new WorkThead();
            Thread thread = new Thread(workTheads[i], "ThreadPool-worker" + threadNum.incrementAndGet());
            System.out.println("初始化线程总数" + (i + 1) + "------当前线程名称是：" + thread.getName());
            thread.start();
        }


    }

    //获取线程池
    public static ThreadPool getThreadPool() {

        return getThreadPool(ThreadPoolManager.workerNum);
    }

    public static ThreadPool getThreadPool(int workerNum) {

        if (workerNum <= 0) {
            workerNum = ThreadPoolManager.workerNum;
        }

        if (threadPool == null) {
            threadPool = new ThreadPoolManager(workerNum);
        }

        return threadPool;
    }


    @Override
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            //通知 启动的线程
            taskQueue.notifyAll();
        }
    }


    @Override
    public void execute(Runnable[] tasks) {

        synchronized (taskQueue) {
            for (Runnable task : tasks) {
                taskQueue.add(task);
            }
            taskQueue.notifyAll();
        }

    }

    @Override
    public void execute(List<Runnable> tasks) {

        synchronized (taskQueue) {
            for (Runnable task : tasks) {
                taskQueue.add(task);
            }
//            taskQueue.notify();
            taskQueue.notifyAll();
        }
    }

    @Override
    public int getExecuteTaskNumber() {

        return executeTaskNumber;
    }

    @Override
    public int getWaitTaskNumber() {
        return taskQueue.size();
    }

    @Override
    public int getWorkThreadNumber() {
        return workerNum;
    }

    /**
     * 线程池销毁
     */
    @Override
    public void destory() {

        while (!taskQueue.isEmpty()) {

            try {
                //不断检测任务队列是否全部执行
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < workerNum; i++) {
            workTheads[i].stopWorker();
            workTheads[i] = null;
        }

        threadPool = null;
        taskQueue.clear();
    }


    @Override
    public String toString() {
        return "当前工作线程数：" + workerNum +
                ", 已完成任务数：" + executeTaskNumber +
                ", 等待任务数量：" + getWaitTaskNumber();
    }

    private class WorkThead extends Thread {

        //该工作线程是否有效 用于结束该工作线程
        private boolean isRunning = true;


        @Override
        public void run() {
            super.run();

            //队列同步机制synchronized
            //接收队列当中的任务对象 Runnable 类型
            Runnable r = null;

//线程无效 结束run方法 线程没用了
            while (isRunning) {
                synchronized (taskQueue) {
//                    try {
//                        taskQueue.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    //队列为空
                    while (isRunning && taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!taskQueue.isEmpty()) {
                        r = taskQueue.remove(0);//取出任务
                    }

                    if (r != null) {
                        r.run();//执行任务
                    }

                    executeTaskNumber++;
                    r = null;

                }

            }


        }

        //停止工作 让该线程自然执行完run方法 自然结束
        public void stopWorker() {
            isRunning = false;
        }
    }

}
