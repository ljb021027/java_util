package com.MyUtils.learn.leetcode.l4;

import java.util.Arrays;

/**
 * @author liujiabei
 * @date 2020/5/25 14:23
 */
public class findMedianSortedArrays {

    public static void main(String[] args) {

//        int[] a = new int[]{4};
//        int[] b = new int[]{1, 2, 3, 5, 6};

//        int[] a = new int[]{1, 3};
//        int[] b = new int[]{2};

        int[] a = new int[]{1};
        int[] b = new int[]{2, 3, 4, 5, 6};

        double medianSortedArrays = findMedianSortedArrays(a, b);

        int len = a.length + b.length;
        int k = len / 2;

        double v = 0;
        if (len % 2 == 1) {
            v = getKth(a, b, 0, 0, k + 1);
        } else {
            v = (getKth(a, b, 0, 0, k) + getKth(a, b, 0, 0, k + 1)) / 2;
        }
        System.out.println(v);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1 > n2)
            return findMedianSortedArrays(nums2, nums1);
        int k = (n1 + n2 + 1) / 2;
        int left = 0;
        int right = n1;
        while (left < right) {
            int m1 = left + (right - left) / 2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2 - 1])
                left = m1 + 1;
            else
                right = m1;
        }
        int m1 = left;
        int m2 = k - left;
        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);
        if ((n1 + n2) % 2 == 1)
            return c1;
        int c2 = Math.min(m1 >= n1 ? Integer.MAX_VALUE : nums1[m1],
                m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
        return (c1 + c2) * 0.5;

    }

    public static double getKth(int[] a, int[] b, int aStart, int bStart, int k) {
        if (aStart == a.length) {
            return b[bStart + k - 1];
        } else if (bStart == b.length) {
            return a[aStart + k - 1];
        }

        //退出
        if (k == 1) {
            return Math.min(a[aStart], b[bStart]);
        }


        //二分，向下取整
        int kk = k / 2;
        //a 拿来比较的index
        int aIndex = aStart + kk - 1;
        //b 拿来比较的index
        int bIndex = bStart + kk - 1;
        if (kk > a.length) {
            aIndex = a.length - 1;
        } else if (kk > b.length) {
            bIndex = b.length - 1;
        }

        //排除的个数
        int out = 0;
        if (a[aIndex] > b[bIndex]) {
            out = bIndex + 1 - bStart;
            bStart = bIndex + 1;
        } else {
            out = aIndex + 1 - aStart;
            aStart = aIndex + 1;
        }
        return getKth(a, b, aStart, bStart, k - out);
    }

    public static double find(int[] a, int[] b) {
        int i = 0, j = 0;

        int cLength = a.length + b.length;
        int mid = cLength / 2 - 1;
        int[] c = new int[cLength];
        while (i < mid && j < mid) {
            if (a[i] > b[j]) {
//                c[i + j] = b[j++];
                j++;
            } else {
//                c[i + j] = a[i++];
                i++;
            }
        }
        while (i < mid) {
            i++;
//            c[i + j] = a[i++];
        }
        while (j < mid) {
            j++;
//            c[i + j] = b[j++];
        }


        int q = cLength % 2;

        if (q == 1) {
            return c[mid + 1];
        } else {
            return (c[mid] + c[mid + 1]) / 2d;
        }
    }

}
