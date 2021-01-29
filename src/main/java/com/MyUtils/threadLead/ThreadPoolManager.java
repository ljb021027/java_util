package com.MyUtils.threadLead;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ljb
 * @since 2018/11/13
 */
public class ThreadPoolManager {


    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    private WorkThread[] workThreads;

    public ThreadPoolManager(int worknum) {
        workThreads = new WorkThread[worknum];

        for (int i = 0; i < worknum; i++) {
            WorkThread workThread = new WorkThread();
            Thread thread = new Thread(workThread);
            thread.start();
            workThreads[i] = workThread;
        }
    }

    public void exec(Runnable task) {
        try {
            System.out.println("put" + task);
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class WorkThread implements Runnable {

        @Override
        public void run() {

//            while(taskQueue.isEmpty()){
//                try {
//                    synchronized (taskQueue){
//                        taskQueue.wait();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            while (true) {
                Runnable runnable = null;
                try {
                    System.out.println("take1");
                    runnable = taskQueue.take();
                    System.out.println("take2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runnable.run();
            }
        }
    }
}
