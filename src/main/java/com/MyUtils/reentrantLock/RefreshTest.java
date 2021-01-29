package com.MyUtils.reentrantLock;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujiabei
 * @date 2020/7/1 10:15
 */
public class RefreshTest {
    private Date expirationTime;
    private static final long validTime = 1000 * 5;
    private boolean flag;

    public RefreshTest() {
        expirationTime = new Date(System.currentTimeMillis() + validTime);
        System.out.println(expirationTime.getTime());
    }

    private void refresh() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date().toString() + " refresh ok");
        expirationTime = new Date(System.currentTimeMillis() + validTime);
        flag = false;
    }

    private boolean needRefrsh(Date date) {
        return expirationTime.getTime() < (date.getTime() + validTime / 5);
    }

    private boolean expiration(Date date) {
        return date.after(expirationTime);
    }

    private void request() throws InterruptedException {
        Date now = new Date();
        //是否已经过期
        if (expiration(now)) {
            System.out.println(now.getTime() + " expiration");
            synchronized (this) {
                if (expiration(now)) {
                    refresh();
                }
            }

        }
        //是否已经启动refresh线程 && 可以提前刷新
        if (!flag && needRefrsh(now)) {
            System.out.println(now.getTime() + " needRefrsh");
            synchronized (this) {
                if (!flag) {
                    flag = true;
                    new Thread(() -> refresh()).start();
                }
            }

        }
        if (expiration(new Date())) {
            System.out.println("error");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        RefreshTest refreshTest = new RefreshTest();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    refreshTest.request();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            if (i % 300 == 0) {
                //模拟过期
                Thread.sleep(validTime + 500);
            } else {
                Thread.sleep(50);

            }
        }

    }
}
