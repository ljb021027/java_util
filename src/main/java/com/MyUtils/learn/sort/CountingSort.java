package com.MyUtils.learn.sort;

import java.util.Collections;

/**
 * 计数排序 (桶排序的一种)
 * 适合数据范围远小于数据个数
 * 只能非负整数，其他类型要转换成非负整数
 *
 * @author ljb
 * @since 2018/12/30
 */
public class CountingSort {

    public static void main(String[] args) {
        int[] a = new int[]{0, 10, 2, 3, 6, 6, 8, 7, 9, 9, 9, 7, 7, 6, 6, 4, 5, 3, 1};
        sort(a);
        for (int i : a) {
            System.out.println(i);
        }

    }


    public static void sort(int[] a) {
        int length = a.length;

        int min = a[0], max = a[0];
        for (int i = 1; i < length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
            if (a[i] > max) {
                max = a[i];
            }
        }
        //create bucket
        int[] bucket = new int[max - min + 1];

        for (int i : a) {
            bucket[i]++;
        }
        //正序遍历会不稳定
        for (int i = 0; i < bucket.length; i++) {
            if (i == 0) {
                continue;
            }
            bucket[i] = bucket[i] + bucket[i - 1];
        }
        //临时数组
        int[] temp = new int[length];
        //正序遍历会不稳定
//        for (int i : a) {
        for (int i = a.length - 1; i >= 0; i--) {
            int num = a[i];
            //小于等于i的个数
            int count = bucket[num];
            //个数-1 就是i所处的下标位置
            temp[count - 1] = num;
            bucket[num] = count - 1;
        }
        //赋值进去
        for (int i = 0; i < a.length; i++) {
            a[i] = temp[i];
        }


    }
}
