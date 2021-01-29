package com.MyUtils.atomic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ljb on 2017/7/19.
 */
public class AtomicReferenceTest {

    private static AtomicReference<BigDecimal> ar = new AtomicReference<BigDecimal>(new BigDecimal(0));

    public static void dfasd111() throws InterruptedException {
        int t = 102;
        final int c = 2;
        final CountDownLatch latch = new CountDownLatch(t);
        for (int i = 0; i < t; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < c; i++) {
                        int j = 0;
                        while (true) {
                            if(j++==1){
                                System.out.println("----"+j);
                            }

                            BigDecimal temp = ar.get();
                            if (ar.compareAndSet(temp, temp.add(new BigDecimal(1.1)))) {
                                break;
                            }
                        }
//                        BigDecimal temp = ar.get();
//                        ar.compareAndSet(temp, temp.add(new BigDecimal(1.1,MathContext.DECIMAL32)));
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        System.out.println(ar.get()); //10000000
    }

    public static void main(String[] args) throws InterruptedException {
        dfasd111();
        BigDecimal temp = new BigDecimal(1.1, MathContext.DECIMAL32);
        temp = temp.add(new BigDecimal(1.1,MathContext.DECIMAL32));
        System.out.println(temp);
    }
}
