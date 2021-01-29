package com.MyUtils.reentrantLock;

import org.apache.poi.ss.formula.functions.T;
import sun.jvm.hotspot.debugger.ThreadAccess;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujiabei
 * @date 2020/7/1 10:00
 */
public class CyclicBarrierTest implements Runnable {

    public static BlockingQueue blockingQueue = new LinkedBlockingQueue();

    public static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    public static Integer index = 1;

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierTest(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                if (index++==1){
//                    System.out.println("haha");
//                }
                if (atomicBoolean.compareAndSet(false, true)) {
                    System.out.println("haha");
                }
            }).start();
        }

    }

    @Override
    public void run() {
//        if (atomicInteger.compareAndSet(1, 2)) {
//            System.out.println("haha");
//        }
//        if (i == 1) {
//            i++;
//            System.out.println("haha");
//        }
    }

}
