package com.MyUtils.zkLock;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/***
 * 分布式锁
 * 
 * 解决分布式调度时同一任务多个节点重复执行的情况发生
* @ClassName: DistributedLock 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Chen_JIAN
* @date 2016年5月31日 下午5:14:32 
*
 */
public class DistributedLock  implements Watcher{
	
	  private final Logger log=Logger.getLogger(DistributedLock.class);
	
	  private final static String root = "/locks";//锁的客户端节点列表
	  
	  private final static String clientsPath="/locks/clients";
	  private final static String leaderPath="/locks/leader";//选举出的领导节点
	  
	
	  
	  private String myLockName;
	  private static int sessionTimeout = 50000;
	  
	  private String clientPort;//客户端端口
	  private CountDownLatch latch = new CountDownLatch(1); 
	  private ZooKeeper zookeeper; 
	  
	  public DistributedLock(String connectString, int sessionTimeout) throws Exception {  
		    
			  if(connectString.contains("zookeeper://")){
				  connectString=connectString.replaceAll("zookeeper://", "");
			  }
			  
	        zookeeper = new ZooKeeper(connectString, sessionTimeout, this);  
	        log.info("connecting zk server");  
	        if (latch.await(10l, TimeUnit.SECONDS)) {  
	        	 log.info("connect zk server success");  
	        } else {  
	        	 log.info("connect zk server error.");  
	            throw new Exception("connect zk server error.");  
	        }  
	    }  
	  
	  public DistributedLock(String connectString) throws Exception { 
		    
		  if(connectString.contains("zookeeper://")){
			  connectString=connectString.replaceAll("zookeeper://", "");
		  }
		  
	        zookeeper = new ZooKeeper(connectString, sessionTimeout, this);  
	        log.info("connecting zk server");  
	        if (latch.await(10l, TimeUnit.SECONDS)) {  
	        	 log.info("connect zk server success");  
	        } else {  
	        	 log.info("connect zk server error.");  
	            throw new Exception("connect zk server error.");  
	        }  
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
	  public void createEPSEQPath(String path,byte[]data) throws Exception {  
		  if (!this.exists(path)) {  
	            zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	            
	        } 
	    }  
	  
	  public void createEPPath(String path,byte[]data) throws Exception {  
		  if (!this.exists(path)) {  
	            zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);  
	        } 
	    }  
	  
	  
	  /**
	   * 
	   * initLock(这里用一句话描述这个方法的作用)
	   * 初始化锁
	   * @throws Exception void
	   * @exception 
	   * @version  1.0.0
	   * @date 2016年6月2日 
	   *
	   */
	  public void initLock() throws Exception{
		  
//		  this.myLockName=InetTool.getHostAddr()+clientPort;
		  //生成lock根节点
		  this.createRoot(root, true);
		  //生成锁的客户端上级节点
		  this.createRoot(clientsPath, true);
		  this.createEPSEQPath(clientsPath+"/"+myLockName+"_lock_", null);
		  
		
		  
	  }
	  
	  
	  /**
	   * 
	   * createRoot(这里用一句话描述这个方法的作用)
	   * 创建根节点
	   * @param path
	   * @param isPersistent
	   * @throws Exception void
	   * @exception 
	   * @version  1.0.0
	   * @date 2016年6月1日 
	   *
	   */
	  
	  public void createRoot(String path, boolean isPersistent) throws Exception{
		  CreateMode createMode = isPersistent ? CreateMode.PERSISTENT : CreateMode.EPHEMERAL;  
	        path = ZKUtil.normalize(path);  
	        if (!this.exists(path)) {  
	            zookeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);  
	        }  
	  }
	  
	  
	  
	  /**
	   * 
	   * getChildren(这里用一句话描述这个方法的作用)
	   * 根据路径获取子节点列表
	   * @param path
	   * @return
	   * @throws Exception List<String>
	   * @exception 
	   * @version  1.0.0
	   * @date 2016年6月1日 
	   *
	   */
	  public List<String> getChildren(String path) throws Exception{
		  
		 return zookeeper.getChildren(path, false);
		  
	  }
	  
	  
	  public Boolean Lock(){	
		  try {
			  //首先判断主锁节点是否存在
			if(!exists(leaderPath)){
				  //不存在则选举一个
				  chooseLeader();
			}
			String lockName=getData(leaderPath);
			
			
			 log.info("lockName:"+lockName);
			 log.info("myLockName:"+myLockName);
		   if(myLockName.equals(lockName)){
			   
			   log.error("取锁成功！"+lockName);
//			   zookeeper.close();
			   return true;
		   }
			
		} catch (Exception e) {
			 log.info("取锁异常:"+e.getMessage());
		}
		  
		  
		  log.error("取锁失败！");
		  return false;
	  }
	  
	  
	  /**
	   * 
	   * chooseLeader(这里用一句话描述这个方法的作用)
	   * 根据锁客户端列表随机选举主节点
	   * @throws Exception void
	   * @exception 
	   * @version  1.0.0
	   * @date 2016年6月1日 
	   *
	   */
	  
	  public void chooseLeader() throws Exception{
		  //获取锁客户端目录列表
		 List<String> childaren=getChildren(clientsPath);
		 String leader=null;
		  if(childaren!=null){
			  //根据随机数推举一个领导
			   Random random = new Random();
		    int index= random.nextInt(childaren.size());
		    log.info("下标："+index);
		      leader=childaren.get(index).split("_lock_")[0];
		  }else{
			  leader=myLockName;
		  }
		  

		  //生成临时的领导节点
		 this.createEPPath(leaderPath, leader.getBytes());
		 
		 log.info("选举的领导为："+leader);
		 
		  
	  }
	  
	  /**
	   * 
	   * unlock(这里用一句话描述这个方法的作用)
	   *  void
	   *  释放锁
	   * @exception 
	   * @version  1.0.0
	   * @date 2016年6月1日 
	   *
	   */
	  
	  public void unlock(){
		  
			try {
				zookeeper.delete(leaderPath, -1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	  }
	  
	  public boolean exists(String path) throws Exception {  
	        path = ZKUtil.normalize(path);  
	        Stat stat = zookeeper.exists(path, null);  
	        return stat != null;  
	    }  
	  
	    public String getData(String path) throws Exception {  
	        path = ZKUtil.normalize(path);  
	        try {  
	            byte[] data = zookeeper.getData(path, null, null);  
	            return new String(data);  
	        } catch (KeeperException e) {  
	            if (e instanceof KeeperException.NoNodeException) {  
	                throw new Exception("Node does not exist,path is [" + e.getPath() + "].", e);  
	            } else {  
	                throw new Exception(e);  
	            }  
	        } catch (InterruptedException e) {  
	            Thread.currentThread().interrupt();  
	            throw new Exception(e);  
	        }  
	    }  
	    
	    public static void main(String[] args) throws Exception {
//	    	DistributedLock lock=new DistributedLock("127.0.0.1:2181",sessionTimeout);
//	    	
//	
//	    	lock.initLock();
//	        lock.chooseLeader();
//	    	
////	    	System.out.println("root"+lock.getData(root));
//	    	System.out.println("clients"+lock.getData(leaderPath));
//	       System.out.println(lock.getLock());	
//	    	Thread.sleep(100000L);
	   
	    	
		}

	    @Override  
	    public void process(WatchedEvent event) {  
	        if (event == null) return;  
	  
	        // 连接状态  
	        Watcher.Event.KeeperState keeperState = event.getState();  
	        // 事件类型  
	        Watcher.Event.EventType eventType = event.getType();  
	        

	        if (Watcher.Event.KeeperState.SyncConnected == keeperState) {  
	            // 成功连接上ZK服务器  
	            if (Watcher.Event.EventType.None == eventType) {  
	            	 log.info("zookeeper connect success");  
	                latch.countDown();  
	            }  
	            
	            
	        }  
	        //下面可以做一些重连的工作.  
	        else if (Watcher.Event.KeeperState.Disconnected == keeperState) {  
	        	 log.info("zookeeper Disconnected");  
	        } else if (Watcher.Event.KeeperState.AuthFailed == keeperState) {  
	        	 log.info("zookeeper AuthFailed");  
	        } else if (Watcher.Event.KeeperState.Expired == keeperState) {  
	        	 log.info("zookeeper Expired");  
	        }
	    }

		public String getClientPort() {
			return clientPort;
		}

		public void setClientPort(String clientPort) {
			this.clientPort = clientPort;
		}  
	    
	    
	    
}
