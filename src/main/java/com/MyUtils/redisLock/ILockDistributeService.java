package com.MyUtils.redisLock;

import java.util.List;

public interface ILockDistributeService {
	
	/**
	 * 获取锁 如果锁可用 立即返回true， 否则返回false
	 * @param key
	 * @return
	 */
	public boolean tryLock(String key);
	
	/**
	 * 获取锁,并设置存活时间。 如果锁可用 立即返回true， 否则返回false
	 * @param key
	 * @param expireSeconds
	 * @return
	 */
	public boolean tryLock(String key, int expireSeconds);
	
	/**
	 * 在设定的等待时间内循环尝试获取 锁，如果没等到时间，则只尝试执行一次
	 * 获取锁成功 返回true， 否则返回false
	 * @param key
	 * @param waitSeconds
	 * @param expireSeconds 锁的存活时间
	 * @return
	 */
	public boolean tryLock(String key, int waitSeconds, int expireSeconds);
	
	/**
	 * 批量获取锁 如果全部获取 立即返回true, 部分获取失败 返回false
	 * @param keyList
	 * @return
	 */
	public boolean tryLockAll(List<String> keyList);
	
	/**
	 * 批量获取锁 如果全部获取 立即返回true, 部分获取失败 返回false
	 * @param keyList
	 * @param expireSeconds
	 * @return
	 */
	public boolean tryLockAll(List<String> keyList, int expireSeconds);
	
	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 * @param keyList
	 * @param waitSeconds
	 * @param expireSeconds
	 * @return
	 */
	public boolean tryLockAll(List<String> keyList, int waitSeconds, int expireSeconds);
	
	
	/**
	 * 释放锁
	 * @param key
	 */
	public void unLock(String key);
	
	/**
	 * 批量释放锁
	 * @param keyList
	 */
	public void unLock(List<String> keyList);

}
