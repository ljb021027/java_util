package com.MyUtils.zkLock.myTest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by ljb on 2017/11/15.
 */
public class ZkClient {

    public Logger logger = LoggerFactory.getLogger(getClass());
    public ZooKeeper zookeeper;
    private static int SESSION_TIME_OUT = 2000;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private SessionWatcher sessionWatcher = new SessionWatcher();

    public ZkClient(){

    }


    /**
     * 连接zookeeper
     * @param host
     * @throws IOException
     * @throws InterruptedException
     */
    public void connectZookeeper(String host){
        logger.info("连接ZooKeeper开始,host={}", host);
        try {
            zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, sessionWatcher);
            //连接超时4S
            countDownLatch.await(4000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("连接ZooKeeper异常,host={}", host, e);
        }
        logger.info("连接ZooKeeper完成,host={}", host);
    }

    class SessionWatcher implements Watcher {

        @Override
        public void process(WatchedEvent watchedEvent) {
            logger.info("zookeeper事件,nodePath={},state={},type={}", watchedEvent.getPath()
                    , watchedEvent.getState(), watchedEvent.getType());
            if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                countDownLatch.countDown();
            }

        }

    }

    /**
     * 根据路径创建节点，并且设置节点数据
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createNode(String path,byte[] data){
        logger.info("创建ZooKeeper节点,nodePath={},data={}" ,path, data);
        String s = null;
        try {
            s = this.zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
            logger.error("创建ZooKeeper节点异常,nodePath={},data={}", path, data, e);
        }
        return s;
    }

    /**
     *
     * createEPSEQPath(这里用一句话描述这个方法的作用)
     * 创建有序的临时节点
     * @param path
     * @param data
     * @throws Exception void
     * @exception
     * @version  1.0.0
     * @date 2016年6月1日
     *
     */
    public String createEPSEQPath(String path,byte[]data) throws Exception {

        logger.info("创建ZooKeeper节点,nodePath={},data={}" ,path, data);
        String s = null;
        try {
            s = zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            logger.error("创建ZooKeeper节点异常,nodePath={},data={}", path, data, e);
        }
        return s;
    }

    /**
     * 根据路径获取所有孩子节点
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException{
        return this.zookeeper.getChildren(path, false);
    }

    public Stat setData(String path, byte[] data, int version) throws KeeperException, InterruptedException{
        return this.zookeeper.setData(path, data, version);
    }

    /**
     * 根据路径获取节点数据
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] getData(String path) throws KeeperException, InterruptedException{
        return this.zookeeper.getData(path, false, null);
    }

    /**
     * 删除节点
     * @param path
     * @param version
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path,int version) throws InterruptedException, KeeperException{
        this.zookeeper.delete(path, version);
    }

    /**
     * 节点是否存在
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean exists(String path) {
        boolean bl = false;
        try {
            if (this.zookeeper.exists(path, true) != null) {
                bl = true;
            }
        } catch (Exception e) {
            logger.error("判断zookeeper节点是否存在异常", e);

        }
        return bl;
    }

    /**
     * 为节点设置监听
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void existsWatch(String path) {
        try {
            this.zookeeper.exists(path, sessionWatcher);
        } catch (Exception e) {
            logger.error("设置ZooKeeper节点监听异常,nodePath={}", path , e);
        }
    }

    /**
     * 关闭zookeeper连接
     * @throws InterruptedException
     */
    public void closeConnect() {
        if(null != zookeeper){
            try {
                zookeeper.close();
            } catch (InterruptedException e) {
                logger.error("关闭zookeeper连接异常", e);
            }
        }
    }

}
