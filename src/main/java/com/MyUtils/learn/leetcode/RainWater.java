package com.MyUtils.learn.leetcode;


import java.util.Arrays;
import java.util.Stack;

public class RainWater {
    public static void main(String[] args) {
        int[] a = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int solution = solution(a);
        System.out.println(solution);
    }

    public static int solution(int[] height) {
        return find3(height);
    }

    public static int find1(int[] height) {
        int result = 0;
        int[] left = new int[height.length];
        int[] right = new int[height.length];

        int max = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > max) {
                max = height[i];
            }
            left[i] = max;
        }
        max = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            if (height[i] > max) {
                max = height[i];
            }
            right[i] = max;
        }
        System.out.println(Arrays.toString(left));
        System.out.println(Arrays.toString(right));

        for (int i = 1; i < height.length - 1; i++) {
            int water = Math.min(left[i], right[i]) - height[i];
            result += water;
        }
        return result;

    }


    public static int find2(int[] height) {
        Stack<Integer> stack = new Stack();
        int result = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.empty() && height[i] > height[stack.peek()]) {
                Integer pop = stack.pop();
                if (stack.empty()) { // 栈空就出去
                    break;
                }
                int distance = i - stack.peek() - 1; //两堵墙之前的距离。
                int min = Math.min(height[stack.peek()], height[i]);
                result = result + distance * (min - height[pop]);
            }
            stack.push(i);
        }
        return result;
    }

    public static int find3(int[] height) {
        int result = 0;
        int left = 0;
        int right = height.length - 1;
        int max_left = 0;
        int max_right = 0;
//        for (int i = 1; i < height.length - 1; i++) {
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= max_left) {
                    max_left = height[left];
                } else {
                    result += max_left - height[left];
                }
                left++;
            } else {
                if (height[right] >= max_right) {
                    max_right = height[right];
                } else {
                    result += max_right - height[right];
                }
                right--;
            }
        }

        return result;
    }
}
