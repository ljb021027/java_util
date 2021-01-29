package com.MyUtils.learn.sort;

/**
 * 归并排序
 *
 * @author ljb
 * @since 2018/12/15
 */
public class MergeSorter {

    public static void main(String[] args) {
        int[] a = new int[]{2, 5, 1, 66, 231, 51213, 22,6,2312,22,7,8,9};
        mergeSort(a);
        for (int i : a) {
            System.out.print(i + ",");

        }
    }

    public static void mergeSort(int[] a) {
        sort(a, 0, a.length-1);
    }

    public static void sort(int[] a, int s, int e) {
        if (s >= e) {
            return;
        }
        int m = (s + e) / 2;
        sort(a, s, m);
        sort(a, m + 1, e);
        int[] merge = merge(split(a, s, m), split(a, m + 1, e));
        System.arraycopy(merge, 0, a, s, merge.length);
    }

    public static int[] split(int[] a, int s, int e) {
        int i = e - s + 1;
        int[] temp = new int[i];
        System.arraycopy(a, s, temp, 0, i);
        return temp;
    }

    public static int[] merge(int[] a, int[] b) {
        int[] temp = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] > b[j]) {
                temp[k++] = b[j++];
            } else {
                temp[k++] = a[i++];
            }

        }
        if (i == a.length) {
            System.arraycopy(b, j, temp, k, temp.length - k);
        } else {
            System.arraycopy(a, i, temp, k, temp.length - k);
        }
        return temp;
    }

}
