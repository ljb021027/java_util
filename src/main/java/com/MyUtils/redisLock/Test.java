package com.MyUtils.redisLock;


import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ljb on 2017/10/9.
 */
public class Test {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";


    public static void main(String[] args) throws InterruptedException {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        Jedis jedis = new Jedis("127.0.0.1", 6379,99999);
        jedis.auth("cnws");
        jedis.select(0);
        jedis.set("abc", "1234");
        jedis.set("abc", "1234",SET_IF_NOT_EXIST);
        jedis.setnx("abc", "1234");

        String requestId = UUID.randomUUID().toString();
        String key = "ljb";
        String result = jedis.set(key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 30);

        System.out.println(result);

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result1 = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
        System.out.println(result1);
//        Thread.sleep(5000);
//        for (int i = 0; i < 5; i++) {
//
//            int finalI = i;
//            cachedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
////                    long value = System.nanoTime() + expireSeconds * 1000000000 + 1;
//
////                    System.out.println(jedis.setnx("abc", String.valueOf(2) ));
//
//                    boolean tryLock = lock.tryLock("abc",10000, 600);
//                    System.out.println("lock:" + tryLock);
//                    if (tryLock){
//                        System.out.println(finalI);
//                    }
//
//                }
//            });
//
//        }

    }

    private static void method(){
        System.out.println("ljb1");
        System.out.println("ljb2");
        System.out.println("ljb3");
    }
}
