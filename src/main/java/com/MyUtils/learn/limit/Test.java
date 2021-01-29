package com.MyUtils.learn.limit;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author ljb
 * @since 2019/2/24
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
//        RateLimit rateLimit =new CountLimit(1, 500L);

        RateLimit rateLimit =new RollingWindow(2, 500L,2);

        for (int i = 0; i < 20; i++) {
            Thread.sleep(200);
            int finalI = i;
            new Thread(() -> {
                if (rateLimit.checkPass()) {
                    System.out.println("=======pass"+finalI);
                } else {
                    System.out.println("=======block");
                }
            }).start();
        }
    }
}

class Test2{
    public static void main(String[] args){
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
    }
}
