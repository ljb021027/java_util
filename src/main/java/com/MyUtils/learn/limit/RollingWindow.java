package com.MyUtils.learn.limit;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 滑动窗口
 *
 * @author ljb
 * @since 2019/2/24
 */
public class RollingWindow implements RateLimit {

    private AtomicReferenceArray<StepWindow> slotCountArray;

    private long limit;

    private long step;

    private long startTime;

    private final ReentrantLock updateLock = new ReentrantLock();

    //step-100ms
    public RollingWindow(int limit, long step, int stepCount) {
        this.limit = limit;
        this.step = step;
        this.slotCountArray = new AtomicReferenceArray(stepCount);
        this.startTime = System.currentTimeMillis();
        for (int i = 0; i < stepCount; i++) {
            slotCountArray.set(i, new StepWindow(this.startTime + (i + 1) * step));
        }
    }

    private int sum(long nowTime, long cycle) {
        int totol = 0;
        for (int i = 0; i < slotCountArray.length(); i++) {
            StepWindow stepWindow = slotCountArray.get(i);
            long startTime = stepWindow.getStartTime();
            long interval = nowTime - startTime;
            long totol1 = stepWindow.getValue().sum();
            System.out.println(i + ":" + interval + ";" + totol1);
            if (interval <= cycle) {
                totol += totol1;
            }
        }
        return totol;
    }


    @Override
    public boolean checkPass() {
        long nowTime = System.currentTimeMillis();
        long slot = (nowTime - startTime - step) / step;
        int length = slotCountArray.length();
        int index = (int) (slot % length);
        System.out.println("slot:" + slot);
        long cycle = length * step;
        slotCountArray.get(index).add(nowTime, cycle);
        int sum = this.sum(nowTime, cycle);
        return sum <= this.limit;
    }

    class StepWindow {

        public StepWindow(long startTime) {
            this.startTime = startTime;
            this.value = new LongAdder();
        }

        private void add(long nowTime, long cycle) {
            boolean b = nowTime - startTime <= cycle;
            if (b) {
                //in cycle
                this.value.increment();
            } else {
                this.startTime = nowTime;
                this.value = new LongAdder();
            }
        }

        private long startTime;

        private LongAdder value;

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public LongAdder getValue() {
            return value;
        }

        public void setValue(LongAdder value) {
            this.value = value;
        }
    }
}
