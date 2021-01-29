package com.MyUtils.redisLock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LockDistributeService implements ILockDistributeService {

    private static final Logger logger = LoggerFactory.getLogger(LockDistributeService.class);

    private static final String LOCK_KEY_PREFIX = "lock:key:";//锁key的前缀

    private static final int EXPIRE_TIME_SECONDS_MIN = 3;//秒，设置单个锁对象的存活时间

    private static final int EXPIRE_TIME_SECONDS_MAX = 900;//秒，设置单个锁对象的存活时间

    private static final int DEFAULT_BATCH_EXPIRE_TIME = 6;//秒，设置批量锁对象的存活时间

    private static final int WAIT_TRY_MAX_TIME_SECOND = 10;//秒，等待锁的最大时间

    private JedisConnectionFactory jedisConnectionFactory;

    private Jedis jedis;

    /**
     * 获取锁 如果锁可用 立即返回true， 否则返回false
     *
     * @param key
     * @return
     */
    public boolean tryLock(String key) {
        return tryLock(key, 0, 0);
    }

    /**
     * 获取锁,并设置存活时间。 如果锁可用 立即返回true， 否则返回false
     *
     * @param key
     * @param expireSeconds
     * @return
     */
    public boolean tryLock(String key, int expireSeconds) {
        return tryLock(key, 0, expireSeconds);
    }

    /**
     * 在设定的等待时间内循环尝试获取 锁，如果没等到时间，则只尝试执行一次
     * 获取锁成功 返回true， 否则返回false
     *
     * @param key
     * @param waitSeconds
     * @param expireSeconds 锁的存活时间
     * @return
     */
    public boolean tryLock(String key, int waitSeconds, int expireSeconds) {
        if (StringUtils.isBlank(key)) {
            return false;
        }

        RedisConnection jedisConnection = null;
        try {
//            key = LOCK_KEY_PREFIX + key;

            long beginTime = System.currentTimeMillis();//开始时间（毫秒）
            long waitTime = 0L;//等待时间（毫秒）
            if (waitSeconds > 0) {
                if (waitSeconds > WAIT_TRY_MAX_TIME_SECOND) {
                    waitSeconds = WAIT_TRY_MAX_TIME_SECOND;
                }
                waitTime = waitSeconds * 1000;
            }

            if (expireSeconds < EXPIRE_TIME_SECONDS_MIN) {
                expireSeconds = EXPIRE_TIME_SECONDS_MIN;
            } else if (expireSeconds > EXPIRE_TIME_SECONDS_MAX) {
                expireSeconds = EXPIRE_TIME_SECONDS_MAX;
            }

//            jedisConnection = jedisConnectionFactory.getConnection();
//            Jedis jedis = (Jedis) jedisConnection.getNativeConnection();

            do {
                System.out.println("==");
//                logger.debug("try lock key: " + key);
//                logger.debug("jedis.key:=== " + jedis.get(key));
                long value = System.nanoTime() + expireSeconds * 1000000000 + 1;//设置key的值，值为过期时间的纳秒值

                Long i = jedis.setnx(key, String.valueOf(value) );
                if (i == 1) {
                    //jedis.setex(key, DEFAULT_SINGLE_EXPIRE_TIME, ""+value);
                    jedis.expire(key, expireSeconds);
                    logger.debug("get lock, key: " + key + " , expire in " + expireSeconds + " seconds.");

                    return Boolean.TRUE;
                } else { // 存在锁
                    if (logger.isDebugEnabled()) {
                        String desc = jedis.get(key);
                        logger.debug("key: " + key + " locked by another business：" + desc);
                    }
                }
                if (waitTime == 0) {
                    break;
                }
                Thread.sleep(500);
            } while ((System.currentTimeMillis() - beginTime) < waitTime);

            return Boolean.FALSE;
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
            //returnBrokenResource(jedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            returnConnection(jedisConnection);
        }
        return Boolean.FALSE;
    }

    /**
     * 批量获取锁 如果全部获取 立即返回true, 部分获取失败 返回false
     *
     * @param keyList
     * @return
     */
    public boolean tryLockAll(List<String> keyList) {
        return tryLockAll(keyList, 0, 0);
    }

    /**
     * 批量获取锁 如果全部获取 立即返回true, 部分获取失败 返回false
     *
     * @param keyList
     * @param expireSeconds
     * @return
     */
    public boolean tryLockAll(List<String> keyList, int expireSeconds) {
        return tryLockAll(keyList, 0, expireSeconds);
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param keyList       注意有可能包含重复的key
     * @param waitSeconds
     * @param expireSeconds
     * @return
     */
    public boolean tryLockAll(List<String> keyList, int waitSeconds, int expireSeconds) {
        if (CollectionUtils.isEmpty(keyList)) {
            return false;
        }

        RedisConnection jedisConnection = null;
        try {
            List<String> needLockList = new CopyOnWriteArrayList<String>();
            List<String> lockedList = new CopyOnWriteArrayList<String>();
            for (String key : keyList) {
                if (StringUtils.isNotBlank(key)) {
                    needLockList.add(LOCK_KEY_PREFIX + key);
                }
            }

            if (CollectionUtils.isEmpty(needLockList)) {
                return false;
            }

            long value = System.nanoTime() + expireSeconds * 1000000000 + 1;//设置key的值，值为过期时间的纳秒值

            long beginTime = System.currentTimeMillis();//开始时间（毫秒）
            long waitTime = 0L;//等待时间（毫秒）
            if (waitSeconds > 0) {
                if (waitSeconds > WAIT_TRY_MAX_TIME_SECOND) {
                    waitSeconds = WAIT_TRY_MAX_TIME_SECOND;
                }
                waitTime = waitSeconds * 1000;
            }

            if (expireSeconds < EXPIRE_TIME_SECONDS_MIN) {
                expireSeconds = EXPIRE_TIME_SECONDS_MIN;
            } else if (expireSeconds > EXPIRE_TIME_SECONDS_MAX) {
                expireSeconds = EXPIRE_TIME_SECONDS_MAX;
            }

            jedisConnection = jedisConnectionFactory.getConnection();
            Jedis jedis = (Jedis) jedisConnection.getNativeConnection();

            do {
                // 构建pipeline，批量提交
                //pipeline就可以充当“批处理”的工具；而且在一定程度上，可以较大的提升性能,性能提升的原因主要是TCP链接中较少了“交互往返”的时间。
                //不过在编码时注意，pipeline期间将“独占”链接，此期间将不能进行非“管道”类型的其他操作，直到pipeline关闭
                Pipeline pipeline = jedis.pipelined();
                for (String key : needLockList) {
                    pipeline.setnx(key, value + "");//NX是Not eXists的缩写，如SETNX命令就应该理解为：SET if Not eXists。
                    //pipeline.incrBy(key, 1L);
                }
                logger.debug("try lock keys: " + needLockList);
                // 提交redis执行计数
                List<Object> resultList = pipeline.syncAndReturnAll();//提交锁定，并返回结果的keylist【1,0,0,1,1,1.......】
                logger.debug("pipeline.syncAndReturnAll: " + resultList);
                for (int i = 0; i < resultList.size(); ++i) {
                    Long result = (Long) resultList.get(i);
                    String key = needLockList.get(i);
                    if (result == 1) { // setnx成功，获得锁
                        //jedis.expire(key, DEFAULT_BATCH_EXPIRE_TIME);
                        lockedList.add(key);
                    }
                }
                needLockList.removeAll(lockedList); // 已锁定资源去除，重复的key也会被删除

                if (CollectionUtils.isEmpty(needLockList)) {//全部都锁定

                    if (!CollectionUtils.isEmpty(lockedList)) {
                        //批量设置存活时间
                        logger.debug("--pipeline.expire keys: " + lockedList);

                        for (String key : lockedList) {
                            pipeline.expire(key, expireSeconds);
                        }

                        List<Object> expireList = pipeline.syncAndReturnAll();
                        logger.debug("--pipeline.expire results: " + expireList);
                    }

                    return true;
                } else {
                    // 部分资源未能锁住
                    logger.debug("-----some keys: " + needLockList + " locked by another business, waitting...");
                }

                if (waitSeconds == 0) {
                    break;
                }
                Thread.sleep(500);
            } while ((System.currentTimeMillis() - beginTime) < waitTime);

            // 得不到锁，释放锁定的部分对象，并返回失败
            if (!CollectionUtils.isEmpty(lockedList)) {
                logger.debug("--jedis.del keys: " + lockedList);
                jedis.del(lockedList.toArray(new String[0]));
            }
            return false;
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
            //returnBrokenResource(jedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnConnection(jedisConnection);
        }
        return true;
    }


    /**
     * 释放锁
     *
     * @param key
     */
    public void unLock(String key) {
        List<String> list = new ArrayList<String>();
        list.add(key);
        unLock(list);
    }

    /**
     * 批量释放锁
     *
     * @param keyList
     */
    public void unLock(List<String> keyList) {
        List<String> keys = new CopyOnWriteArrayList<String>();
        for (String key : keyList) {
            if (StringUtils.isNotBlank(key)) {
                keys.add(LOCK_KEY_PREFIX + key);
            }
        }

        RedisConnection jedisConnection = null;
        try {
            jedisConnection = jedisConnectionFactory.getConnection();
            Jedis jedis = (Jedis) jedisConnection.getNativeConnection();

            jedis.del(keys.toArray(new String[0]));
            logger.debug("release lock, keys :" + keys);
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
            //returnBrokenResource(jedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnConnection(jedisConnection);
        }
    }


    private void closeJedis(Jedis jedis) {//jedis.close();

        try {
            if (jedis != null) {
                jedis.close();//此方法会判断Jedis的dataSource成员不为空，不为空则将连接放回连接池
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * spring-data-redis pool回收链接
     *
     * @param jedisConnection
     */
    private void returnConnection(RedisConnection jedisConnection) {//jedis.close();

        try {
            if (jedisConnection != null) {
                jedisConnection.close();//这个close不是真正的关闭，而是将连接放回连接池
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public JedisConnectionFactory getJedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
