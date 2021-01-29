package com.MyUtils.reentrantLock;

/**
 * Created by ljb on 2017/7/17.
 */
public class Consumer {

    private Depot depot;

    public Consumer(Depot depot){
        this.depot = depot;
    }

    public void consume(int val){
        new Thread(){
            public void run(){
                depot.consume(val);
            }
        }.start();
    }
}
