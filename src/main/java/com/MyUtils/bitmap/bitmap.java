package com.MyUtils.bitmap;

import sun.jvm.hotspot.utilities.BitMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiabei
 * @date 2020/7/16 17:26
 */
public class bitmap {

    public int firstMissingPositive1(int[] nums) {
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
                // 满足在指定范围内、并且没有放在正确的位置上，才交换
                // 例如：数值 3 应该放在索引 2 的位置上
                swap(nums, nums[i] - 1, i);
            }
        }

        // [1, -1, 3, 4]
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 都正确则返回数组长度 + 1
        return len + 1;
    }


    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] >= 1 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i]-1);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (i+1 != nums[i]) {
                return i+1;
            }
        }
        return nums.length + 1;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }


    public static void main(String[] args) {
        bitmap b = new bitmap();
//        int[] nums = new int[]{1, 2, 0};
        int[] nums = new int[]{-1,4,2,1,9,10};

        int i = b.firstMissingPositive(nums);
        System.out.println(i);
    }
}
