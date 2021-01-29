package com.MyUtils.learn.sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 快速排序
 *
 * @author ljb
 * @since 2018/12/18
 */
public class QuickSorter {

    public static void main(String[] args) {
        int[] a = new int[]{4, 1, 61, 81, 123, 100, 2, 77, 3, 88, 6};
        sort(a, 0, a.length - 1);
        for (int i : a) {
            System.out.print(i + ",");
        }

    }

    public static void sort(int[] a, int start, int end) {

        if (start >= end) {
            return;
        }
        int partition = partition(a, start, end);
        sort(a, start, partition - 1);//递归
        sort(a, partition + 1, end);

    }

    public static int partition(int[] a, int start, int end) {
        int partition = a[end];
        int i = start;
        //遍历，找到所有比partition小的，交换到a[0:i-1]里面去
        //,则a[0:i-1]全部小于partition。
        //,a[i:end-1] > partition
        //最后交换 a[i] 和 a[end]
        for (int j = start; j < end; j++) {
            if (a[j] < partition) {
                //比partition小
                //加入到a[0:i-1]的尾部 也就是a[i]
                //swap a[i] a[j]
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
                i++;
            }
        }
        //swap a[i] 和 a[end]
        a[end] = a[i];
        a[i] = partition;
        return i;
    }

}
