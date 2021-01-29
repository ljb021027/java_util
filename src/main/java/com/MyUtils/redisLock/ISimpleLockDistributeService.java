package com.MyUtils.redisLock;

/**
 * Created by ljb on 2017/12/26.
 */
public interface ISimpleLockDistributeService {

    /**
     * 在等待时间内只判断锁是否存在 不去获取锁
     * @param key
     * @param waitSeconds
     * @return
     */
    boolean tryLockNoAdd(String key, int waitSeconds);

    /**
     * 在等待时间内尝试获取锁
     * @param key
     * @param requestId 标识唯一线程 可用UUID.randomUUID().toString()
     * @param waitSeconds
     * @param expireSeconds
     * @return
     */
    boolean tryLock(String key, String requestId, int waitSeconds, int expireSeconds);

    /**
     * 解锁
     * @param key
     * @param requestId 标识唯一线程
     */
    void unLock(String key, String requestId);

}
