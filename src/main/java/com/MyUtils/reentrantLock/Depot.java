package com.MyUtils.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljb on 2017/7/17.
 */
public class Depot {

    private volatile int size;

    private Lock lock;

    private Condition emptyCondtion;

    private Condition maxCondtion;

    private static final int maxSize = 200;

    public Depot(){
        lock = new ReentrantLock();
        emptyCondtion = lock.newCondition();
        maxCondtion = lock.newCondition();
    }



    public void produce(int val){
        lock.lock();
        try{
            while(size + val > maxSize){
                maxCondtion.await();
            }
            size += val;
            System.out.printf("%s produce(%d) --> size=%d\n",
                    Thread.currentThread().getName(), val, size);
            emptyCondtion.signalAll();
        }catch(Exception e){

        }finally {
            lock.unlock();
        }
    }

    public void consume(int val){
        lock.lock();
        try{
            while(size < val){
                emptyCondtion.await();
            }
            size -= val;
            if(size < maxSize){
                maxCondtion.signalAll();
            }
            System.out.printf("%s consume(%d) <-- size=%d\n",
                    Thread.currentThread().getName(), val, size);

        }catch(Exception e){

        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Depot depot = new Depot();
        Producer p = new Producer(depot);
        Consumer c = new Consumer(depot);
        c.consume(80);
        p.produce(10);
        p.produce(10);
        c.consume(20);
        c.consume(10);
        p.produce(500);
    }
}
