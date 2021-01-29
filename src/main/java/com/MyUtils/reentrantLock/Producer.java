package com.MyUtils.reentrantLock;

/**
 * Created by ljb on 2017/7/17.
 */
public class Producer {

    private Depot depot;

    public Producer(Depot depot){
        this.depot = depot;
    }

    public void produce(int val){
        new Thread(){
            public void run(){
                depot.produce(val);
            }
        }.start();
    }

}
