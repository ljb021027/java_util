package com.MyUtils.learn.limit;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujiabei
 * @since 2019/2/22
 */
public class CountLimit implements RateLimit {

    private AtomicInteger count = new AtomicInteger(0);
    private int limit;
    private long interval;
    private long initTimeStamp;

    public CountLimit(int limit, Long interval) {
        this.limit = limit;
        this.interval = interval;
    }

    public boolean checkPass() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - initTimeStamp < interval) {
            return count.incrementAndGet() <= limit;
        } else {
            initTimeStamp = nowTime;
            count.set(0);
            return true;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CountLimit countLimit = new CountLimit(1, 500L);

        for (int i = 0; i < 20; i++) {
            Thread.sleep(400);
            int finalI = i;
            new Thread(() -> {
                if (countLimit.checkPass()) {
                    System.out.println(finalI);
                } else {
                    System.out.println("block");
                }
            }).start();
        }


    }

}
