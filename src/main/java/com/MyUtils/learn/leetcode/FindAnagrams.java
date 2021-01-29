package com.MyUtils.learn.leetcode;


import java.util.BitSet;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/
 *
 * @author ljb
 * @since 2019/6/7
 */
public class FindAnagrams {

    public List<Integer> findAnagrams(String s, String p) {

        return null;
    }

    public static void main(String[] args){
        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(1, true);
        bitSet.set(5, true);
        int cardinality = bitSet.cardinality();
        System.out.println(cardinality);
    }
}
