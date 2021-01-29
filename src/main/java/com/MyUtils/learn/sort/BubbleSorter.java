package com.MyUtils.learn.sort;

/**
 * @author ljb
 * @since 2018/11/14
 */
public class BubbleSorter {

    public static void main(String[] args) {
        int[] arr = {0, 2, 5, 12, 5, 1, 3};
        boolean swp = true;
        for (int i = 0; i < arr.length && swp; i++) {
            swp = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                    swp = true;
                }
            }

        }

        for (int i : arr) {
            System.out.println(i);
        }
    }
}
