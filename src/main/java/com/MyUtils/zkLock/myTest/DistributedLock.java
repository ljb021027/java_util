package com.MyUtils.zkLock.myTest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author : ljb
 * @Description :
 * @Date : 2018/2/2.
 */
public class DistributedLock {

    private ZkClient zkClient = new ZkClient();

    private String host = "localhost:2181";

    private final String LOCK_ROOT_NODE = "/lock";

    private final String LOCK_NAME = "/testName";

    public DistributedLock(){
        zkClient.connectZookeeper(host);
    }


    public Boolean lock() throws Exception {
        //创建临时顺序节点
        zkClient.createEPSEQPath(LOCK_ROOT_NODE + LOCK_NAME , null);
        //获取所有子节点 并排序
        List<String> children = getSortedChildren();
        return null;
    }

    private List<String> getSortedChildren() throws Exception {
//        try {
            List<String> children = zkClient.getChildren(LOCK_ROOT_NODE);
            Collections.sort(
                    children,
                    new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            return getLockNodeNumber(lhs, LOCK_NAME).compareTo(getLockNodeNumber(rhs, LOCK_NAME));
                        }
                    }
            );
            return children;

//        } catch (ZkNoNodeException e) {
//            zkClient.createPersistent(basePath, true);
//            return getSortedChildren();
//        }
    }

    private String getLockNodeNumber(String str, String lockName) {
        int index = str.lastIndexOf(lockName);
        if (index >= 0) {
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

}
