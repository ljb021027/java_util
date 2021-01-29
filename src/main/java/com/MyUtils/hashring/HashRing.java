package com.MyUtils.hashring;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author : ljb
 * @Description :
 * @Date : 2018/3/5.
 */
public class HashRing {

    private static String[] services = new String[]{"1", "2" ,"3"};

    private static SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();

    static{
        for (String s : services){
            sortedMap.put(getHash(s), s);
        }
    }

    public static String getService(String key){
        int hash = getHash(key);
        SortedMap<Integer, String> tailMap = sortedMap.tailMap(hash);
        if (tailMap.isEmpty()){
            return sortedMap.get(sortedMap.firstKey());
        }
        Integer integer = tailMap.firstKey();
        return sortedMap.get(integer);
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
        for (int i = 0; i < nodes.length; i++){
            System.out.println("[" + nodes[i] + "]的hash值为" +
            getHash(nodes[i]) + ", 被路由到结点[" + getService(nodes[i]) + "]");
        }

        System.out.println(1);
    }
}
