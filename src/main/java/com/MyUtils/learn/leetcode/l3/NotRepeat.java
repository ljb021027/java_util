package com.MyUtils.learn.leetcode.l3;

import org.omg.CORBA.INTERNAL;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author liujiabei
 * @date 2020/5/21 19:49
 */
public class NotRepeat {

    public static void main(String[] args) {
//        int maxLen = maxLen2("abcabcbb");
        int maxLen = lengthOfLongestSubstring("abcabcbbabcd");
        System.out.println(maxLen);
    }

    public static int lengthOfLongestSubstring(String str) {
        if (str == null || str.length() == 0) return 0;

        HashMap<Character, Integer> temp = new HashMap<>();
        char[] chars = str.toCharArray();

        int res = 0, start = 0;
        for (int i = 0; i < chars.length; i++) {
            if (temp.containsKey(chars[i])) {
                start = Math.max(temp.put(chars[i], i) + 1, start);
            }

            temp.put(chars[i], i);
            res = Math.max(res, i - start + 1);

        }

        return res;
    }

    public static int maxLen2(String s) {
        int i = 0;
        int j = 0;
        int length = s.length();
        int maxCount = 1;
        while (i < s.length() - 1 && j < s.length() - 1) {
            String substring = s.substring(i, j + 1);
            if (substring.contains(String.valueOf(s.charAt(j + 1)))) {
                i++;
            } else {
                j++;
                maxCount = Math.max(maxCount, j - i + 1);
            }
        }
        return maxCount;
    }

    public static int maxLen(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int i = 0, j = 0;
        int maxCount = 1;
        while (i < s.length() - 1 && j < s.length() - 1) {
            String subStr = s.substring(i, j + 1);
            if (!subStr.contains(String.valueOf(s.charAt(j + 1)))) {
                j++;
                int size = j - i + 1;
                maxCount = maxCount > size ? maxCount : size;
            } else {
                i++;
            }
        }
        return maxCount;
    }

//    public static int maxLen(String str) {
//        if (str.isEmpty()) {
//            return 0;
//        }
//        List<Integer> result = new ArrayList<Integer>();
//        char next = str.charAt(0);
//        List<Character> set = new ArrayList<>();
//        set.add(next);
//        result.add(1);
//        for (int j = 1; j < str.length(); j++) {
//            char c = str.charAt(j);
//            int indexOf = set.indexOf(c);
//            if (indexOf >= 0) {
//                j -= (set.size() - indexOf + 1);
//                result.add(set.size());
//                set.clear();
//            } else {
//                set.add(c);
//            }
//            next = c;
//        }
//        result.add(result.size());
//        int i = 0;
//        for (Integer integer : result) {
//            if (integer > i) {
//                i = integer;
//            }
//
//        }
//        return i;
//    }

//    public static int maxLen(String s) {
//        // 记录字符上一次出现的位置
//        int[] last = new int[128];
//        for (int i = 0; i < 128; i++) {
//            last[i] = -1;
//        }
//        int n = s.length();
//
//        int res = 0;
//        int start = 0; // 窗口开始位置
//        for (int i = 0; i < n; i++) {
//            int index = s.charAt(i);
//            start = Math.max(start, last[index] + 1);
//            res = Math.max(res, i - start + 1);
//            last[index] = i;
//        }
//
//        return res;
//    }


}