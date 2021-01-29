package com.MyUtils.hashring;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author : ljb
 * @Description :
 * @Date : 2018/3/5.
 */
public class HashRingVirtualNode {

    private static String[] services = new String[]{"1", "2", "3"};

    private static SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();

    /**
     * 虚拟节点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟节点
     */
    private static final int VIRTUAL_NODES = 5000;

    static {
        for (String s : services) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = s + "&&VN" + String.valueOf(i);
                sortedMap.put(getHash(virtualNodeName), virtualNodeName);
            }
        }
    }

    public static String getService(String key) {
        int hash = getHash(key);
        SortedMap<Integer, String> tailMap = sortedMap.tailMap(hash);
        if (tailMap.isEmpty()) {
            return sortedMap.get(sortedMap.firstKey());
        }
        Integer integer = tailMap.firstKey();
        String virtualNode = sortedMap.get(integer);
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }

    //使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    public static void main(String[] args) {
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    getHash(nodes[i]) + ", 被路由到结点[" + getService(nodes[i]) + "]");
        }

        System.out.println(1);
    }
}
