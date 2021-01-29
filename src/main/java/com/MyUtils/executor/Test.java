package com.MyUtils.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujiabei
 * @since 2019/6/11
 */
public class Test {

    public static ExecutorService reportWorkers = new ThreadPoolExecutor(20, 40, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(64),new MyDefaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        System.out.println("start:" + start);
        String exec = exec();
        System.out.println(exec);
        System.out.println("end:" + (System.currentTimeMillis() - start));

        return;
    }

    public static String exec() throws InterruptedException, ExecutionException, TimeoutException {
        reportWorkers.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        return "hahaha";
    }

    static class MyDefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyDefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}



