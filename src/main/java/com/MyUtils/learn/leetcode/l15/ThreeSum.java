package com.MyUtils.learn.leetcode.l15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liujiabei
 * @date 2020/6/7 11:36
 */
public class ThreeSum {

    public static void main(String[] args) {
        ThreeSum ts = new ThreeSum();
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};

        List<List<Integer>> lists = ts.threeSum(nums);
        System.out.println(lists);

        int i = ts.majorityElement(new int[]{6, 5, 5});
        System.out.println(i);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ls = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && (nums[i] == nums[i - 1])) {
                continue;
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum < 0) {
                    while (j < k && nums[j] == nums[++j]) {
                    }
                } else if (sum == 0) {
                    ls.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while (j < k && nums[j] == nums[++j]) {
                    }

                    while (j < k && nums[k] == nums[--k]) {
                    }
                } else if (sum > 0) {
                    while (j < k && nums[k] == nums[--k]) {
                    }
                }
            }


        }
        return ls;
    }

    public int majorityElement(int[] nums) {
        int a = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == a) {
                count++;

            } else {
                if (--count == 0) {
                    a = nums[i + 1];
                }
            }
        }
        return a;
    }
}
