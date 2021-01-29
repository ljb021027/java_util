package com.MyUtils.learn.sort;

/**
 * 插入排序
 * @author ljb
 * @since 2018/12/15
 */
public class InsertSorter {

    public static void insertSort(int[] a) {
        int length = a.length;

        for (int i = 1; i < length; i++) {
            int value = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = value;
        }
    }


    // 插入排序，a 表示数组，n 表示数组大小
    public static void insertionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j];  // 数据移动
                } else {
                    break;
                }
            }
            a[j + 1] = value; // 插入数据
        }
    }


    public static void main(String[] args) {
        int[] a = new int[]{2, 1, 7, 2, 3};
        insertSort(a);

        for (int i : a) {
            System.out.print(i);

        }
    }
}
