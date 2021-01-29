package com.MyUtils.redisLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Collections;

/**
 * 简单redis分布式实现 解决宕机死锁和解锁了别人的锁的问题
 * 有redis主从复制延迟 导致并发的问题
 * Created by ljb on 2017/12/26.
 */
@Service("simpleLockDistributeService")
public class SimpleLockDistributeService{

    private static final Logger logger = LoggerFactory.getLogger(SimpleLockDistributeService.class);

    private static final String LOCK_KEY_PREFIX = "lock.key:";//锁key的前缀

    private static final int EXPIRE_TIME_SECONDS_MIN = 3;//秒，设置单个锁对象的存活时间

    private static final int EXPIRE_TIME_SECONDS_MAX = 900;//秒，设置单个锁对象的存活时间

    private static final int WAIT_TRY_MAX_TIME_SECOND = 100;//秒，等待锁的最大时间

    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public boolean tryLock(String key, String requestId, int waitSeconds, int expireSeconds) {
        if (StringUtils.isBlank(key)) {
            return false;
        }

        RedisConnection jedisConnection = null;
        try {
            key = LOCK_KEY_PREFIX + key;

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
                logger.debug("try lock key: " + key);
                logger.debug("jedis.key:=== " + jedis.get(key));

                String result = jedis.set(key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireSeconds);

                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }

                if (waitTime == 0) {
                    break;
                }
                Thread.sleep(200);
            } while ((System.currentTimeMillis() - beginTime) < waitTime);

            return Boolean.FALSE;
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
            //returnBrokenResource(jedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnConnection(jedisConnection);
        }
        return Boolean.FALSE;

    }

    public void unLock(String key, String requestId) {
        key = LOCK_KEY_PREFIX + key;
        RedisConnection jedisConnection = null;
        try {
            jedisConnection = jedisConnectionFactory.getConnection();
            Jedis jedis = (Jedis) jedisConnection.getNativeConnection();

            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
//            if (RELEASE_SUCCESS.equals(result)) {
//                return true;
//            }
//            return false;

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
}
