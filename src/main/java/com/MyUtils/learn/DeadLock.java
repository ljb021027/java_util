package com.MyUtils.learn;

import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;

/**
 * @author liujiabei
 * @date 2020/5/25 14:26
 */
public class DeadLock {

    public static final Object ob1 = new Object();
    public static final Object ob2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                synchronized (ob1) {
                    System.out.println("1-2, 1");
                    synchronized (ob2) {
                        System.out.println("1-2, 2");

                    }
                }
            }


        }).start();

        new Thread(() -> {
            while (true) {
                synchronized (ob2) {
                    System.out.println("2-1, 1");
                    synchronized (ob1) {
                        System.out.println("2-1,2");
                    }
                }
            }


        }).start();


    }

}
